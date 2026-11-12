package in.carbon.ui;

import in.carbon.core.ActionPlan;
import in.carbon.core.Activity;
import in.carbon.core.CarbonService;
import in.carbon.core.Category;
import in.carbon.core.Factor;
import in.carbon.core.FactorStatus;
import in.carbon.core.Scope;
import in.carbon.core.Workspace;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/** Footprint Lens desktop shell. Domain calculations remain testable without JavaFX. */
public final class FootprintLensApp extends Application {
    private Workspace workspace;
    private ListView<Activity> activityList;
    private TextArea detailArea;
    private Label statusLabel;
    private Label personalTotal;
    private Label householdTotal;
    private Label actionSelectionLabel;
    private Button showActionButton;
    private Button chooseActionButton;
    private Button dismissActionButton;
    private Button compareActionButton;

    private ComboBox<Category> categoryBox;
    private Label quantityLabel;
    private TextField quantityField;
    private ComboBox<Scope> scopeBox;
    private CheckBox protectedBox;
    private CheckBox factorBox;
    private TextField factorValue;
    private ComboBox<String> metricBox;
    private TextField factorSource;
    private TextField factorYear;
    private TextField factorGeography;
    private TextField factorBoundary;
    private TextArea noteArea;
    private Button saveActivityButton;
    private boolean loadFailed;
    private Category lastCategory;
    private boolean d3BaselineMode = false;
    private Scene mainScene;
    private BorderPane rootPane;

    @Override
    public void start(Stage stage) {
        if (getParameters() != null && getParameters().getRaw().contains("--d3")) {
            d3BaselineMode = true;
        } else if ("true".equalsIgnoreCase(System.getProperty("d3.baseline"))) {
            d3BaselineMode = true;
        }

        Path defaultPath = Path.of(System.getProperty("user.home"), ".footprint-lens", "records.csv");
        String configuredPath = System.getProperty("footprint.lens.store");
        workspace = new Workspace(configuredPath == null || configuredPath.isBlank()
                ? defaultPath : Path.of(configuredPath));
        String initialStatus;
        try {
            workspace.load();
            initialStatus = "Loaded " + workspace.activities().size() + " local record(s).";
        } catch (Exception ex) {
            loadFailed = true;
            initialStatus = "The local store could not be loaded: " + ex.getMessage();
        }

        rootPane = new BorderPane();
        rootPane.getStyleClass().add("app-root");
        rootPane.setTop(buildHeader());
        rootPane.setCenter(buildTabs());
        statusLabel = new Label(initialStatus);
        statusLabel.getStyleClass().add("status-bar");
        rootPane.setBottom(statusLabel);

        mainScene = new Scene(rootPane, 1140, 780);
        applyCurrentTheme();

        stage.setTitle(d3BaselineMode
                ? "Footprint Lens — D3 Technical Setup & Architectural Baseline"
                : "Footprint Lens — D4 Increment 1 (understand, compare, choose)");
        stage.setMinWidth(720);
        stage.setMinHeight(650);
        stage.setScene(mainScene);
        stage.show();
        refreshOverview();
    }

    private void applyCurrentTheme() {
        if (mainScene == null) return;
        mainScene.getStylesheets().clear();
        if (!d3BaselineMode) {
            var css = getClass().getResource("/style.css");
            if (css != null) {
                mainScene.getStylesheets().add(css.toExternalForm());
            } else {
                java.nio.file.Path localCss = java.nio.file.Path.of("src/main/resources/style.css");
                if (!java.nio.file.Files.exists(localCss)) localCss = java.nio.file.Path.of("New/application/src/main/resources/style.css");
                if (java.nio.file.Files.exists(localCss)) mainScene.getStylesheets().add(localCss.toUri().toString());
            }
        }
    }

    private void toggleVersionMode() {
        d3BaselineMode = !d3BaselineMode;
        applyCurrentTheme();
        rootPane.setTop(buildHeader());
        rootPane.setCenter(buildTabs());
        refreshOverview();
    }

    private HBox buildHeader() {
        Label title = new Label(d3BaselineMode
                ? "Footprint Lens (D3 Architectural Baseline)"
                : "🌱 Footprint Lens (D4 Increment 1)");
        title.setFont(Font.font("System", FontWeight.BOLD, d3BaselineMode ? 22 : 26));
        title.getStyleClass().add("title");
        Label subtitle = new Label(d3BaselineMode
                ? "D3 Technical Setup & Walking Skeleton — Clean Layered Architecture Baseline (v0.1.0)"
                : "India-Centric Carbon Footprint Awareness — Modern Card Architecture & Visual Provenance (v0.2.0)");
        subtitle.getStyleClass().add("subtitle");
        VBox text = new VBox(4, title, subtitle);

        Button switchMode = new Button(d3BaselineMode ? "✨ Switch to D4 Modern View" : "🏛️ Switch to D3 Baseline View");
        switchMode.setStyle(d3BaselineMode
                ? "-fx-background-color: #059669; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 6px; -fx-cursor: hand;"
                : "-fx-background-color: rgba(255,255,255,0.18); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8px 16px; -fx-background-radius: 6px; -fx-border-color: rgba(255,255,255,0.4); -fx-border-radius: 6px; -fx-cursor: hand;");
        switchMode.setOnAction(e -> toggleVersionMode());

        HBox header = new HBox(20, text, switchMode);
        HBox.setHgrow(text, Priority.ALWAYS);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(18, 32, 18, 32));
        header.getStyleClass().add("header");
        if (d3BaselineMode) {
            header.setStyle("-fx-background-color: #334155; -fx-padding: 16px 24px;");
        } else {
            header.setStyle("");
        }
        return header;
    }

    private TabPane buildTabs() {
        TabPane tabs = new TabPane();
        Tab overview = new Tab(d3BaselineMode ? "Overview & records" : "📊 Overview & Records", buildOverview());
        overview.setClosable(false);
        Tab add = new Tab(d3BaselineMode ? "Add activity" : "➕ Add Activity", buildEntryForm());
        add.setClosable(false);
        Tab actions = new Tab(d3BaselineMode ? "Actions & comparisons" : "🎯 Actions & Comparisons", buildActions());
        actions.setClosable(false);
        Tab help = new Tab(d3BaselineMode ? "About & boundaries" : "ℹ️ About & Boundaries", buildAbout());
        help.setClosable(false);
        tabs.getTabs().addAll(overview, add, actions, help);
        return tabs;
    }

    private BorderPane buildOverview() {
        personalTotal = new Label();
        householdTotal = new Label();
        personalTotal.setAccessibleText("Partial personal estimated total");
        householdTotal.setAccessibleText("Partial household estimated total");
        HBox summary = new HBox(20, statCard("Personal Estimated Total (Partial)", personalTotal, false),
                statCard("Household Estimated Total (Partial)", householdTotal, true));
        summary.setPadding(new Insets(20, 24, 14, 24));

        activityList = new ListView<>();
        activityList.setAccessibleText("Saved activity records");
        activityList.setPlaceholder(new Label("No activities yet. Start with one small record."));
        activityList.setCellFactory(view -> new ListCell<>() {
            @Override protected void updateItem(Activity item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else if (d3BaselineMode) {
                    setText(item.displayName() + "  •  " + item.estimate().display()
                            + (item.protectedChoice() ? "  •  protected choice" : ""));
                    setGraphic(null);
                } else {
                    String icon = switch (item.category()) {
                        case ELECTRICITY -> "⚡";
                        case TRAVEL -> "🚗";
                        case FOOD -> "🥗";
                        case WATER -> "💧";
                        case PACKAGING -> "📦";
                        case WASTE -> "🗑️";
                        case REPAIR_REUSE -> "♻️";
                    };
                    Label iconLabel = new Label(icon);
                    iconLabel.setFont(Font.font("System", FontWeight.BOLD, 18));

                    Label catLabel = new Label(item.category().label());
                    catLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
                    catLabel.setStyle("-fx-text-fill: #0f172a;");

                    Label qtyLabel = new Label(item.quantity().toPlainString() + " " + item.category().unit());
                    qtyLabel.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");

                    HBox topRow = new HBox(8, catLabel, qtyLabel);
                    topRow.setAlignment(Pos.CENTER_LEFT);

                    Label scopeTag = new Label(item.scope() == Scope.HOUSEHOLD ? "🏠 Household" : "👤 Personal");
                    scopeTag.setStyle("-fx-background-color: " + (item.scope() == Scope.HOUSEHOLD ? "#eff6ff" : "#ecfdf5") + "; "
                            + "-fx-text-fill: " + (item.scope() == Scope.HOUSEHOLD ? "#1d4ed8" : "#047857") + "; "
                            + "-fx-padding: 2px 8px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-font-weight: bold;");

                    Label estimateBadge = new Label(item.estimate().display());
                    boolean isNum = item.estimate().available();
                    estimateBadge.setStyle("-fx-background-color: " + (isNum ? "#d1fae5" : "#f1f5f9") + "; "
                            + "-fx-text-fill: " + (isNum ? "#065f46" : "#64748b") + "; "
                            + "-fx-padding: 2px 8px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-font-weight: bold;");

                    HBox bottomRow = new HBox(8, scopeTag, estimateBadge);
                    bottomRow.setAlignment(Pos.CENTER_LEFT);

                    if (item.protectedChoice()) {
                        Label protBadge = new Label("🛡️ Protected");
                        protBadge.setStyle("-fx-background-color: #fef3c7; -fx-text-fill: #92400e; "
                                + "-fx-padding: 2px 8px; -fx-background-radius: 6px; -fx-font-size: 11px; -fx-font-weight: bold;");
                        bottomRow.getChildren().add(protBadge);
                    }

                    VBox textContainer = new VBox(4, topRow, bottomRow);
                    HBox cellLayout = new HBox(12, iconLabel, textContainer);
                    cellLayout.setAlignment(Pos.CENTER_LEFT);
                    cellLayout.setPadding(new Insets(4, 0, 4, 0));

                    setText(null);
                    setGraphic(cellLayout);
                }
            }
        });
        activityList.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            showDetails(selected);
            updateActionControls();
        });

        detailArea = new TextArea();
        detailArea.setEditable(false);
        detailArea.setWrapText(true);
        detailArea.setPrefRowCount(10);
        detailArea.setPromptText("Select a record to inspect its assumptions and limits.");
        detailArea.setAccessibleText("Selected record details and assumptions");

        Button inspect = new Button("🔍 Inspect Assumptions");
        inspect.setOnAction(event -> inspectSelected());
        Button delete = new Button("🗑️ Delete Selected");
        delete.getStyleClass().add("danger-button");
        delete.setOnAction(event -> deleteSelected());
        Button refresh = new Button("🔄 Refresh");
        refresh.setOnAction(event -> refreshOverview());
        HBox controls = new HBox(10, inspect, delete, refresh);
        controls.setAlignment(Pos.CENTER_LEFT);

        Label detailsHeader = new Label("Selected Record & Provenance");
        detailsHeader.getStyleClass().add("section-header");
        VBox right = new VBox(12, detailsHeader, detailArea, controls);
        right.getStyleClass().add("content-card");
        right.setPadding(new Insets(18, 20, 20, 20));
        right.setPrefWidth(480);

        Label recordsHeader = new Label("Logged Activities");
        recordsHeader.getStyleClass().add("section-header");
        VBox left = new VBox(12, recordsHeader, activityList);
        left.getStyleClass().add("content-card");
        left.setPadding(new Insets(18, 20, 20, 20));
        VBox.setVgrow(activityList, Priority.ALWAYS);

        HBox body = new HBox(18, left, right);
        body.setPadding(new Insets(0, 24, 24, 24));
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        return new BorderPane(body, summary, null, null, null);
    }

    private VBox statCard(String title, Label value, boolean isHousehold) {
        if (d3BaselineMode) {
            Label heading = new Label(title);
            heading.getStyleClass().add("muted");
            value.getStyleClass().add("stat");
            VBox box = new VBox(4, heading, value);
            box.getStyleClass().add("stat-card");
            box.setPadding(new Insets(12));
            box.setMinWidth(250);
            return box;
        }
        Label heading = new Label((isHousehold ? "🏠 " : "👤 ") + title);
        heading.getStyleClass().add("muted");
        value.getStyleClass().add("stat");
        Label subtext = new Label(isHousehold ? "Shared flat/home consumption (not divided per person)" : "Your individual direct consumption");
        subtext.getStyleClass().add("stat-sub");
        VBox box = new VBox(6, heading, value, subtext);
        box.getStyleClass().addAll("stat-card", isHousehold ? "stat-card-household" : "stat-card-personal");
        box.setPadding(new Insets(16, 20, 16, 20));
        box.setMinWidth(320);
        return box;
    }

    private ScrollPane buildEntryForm() {
        categoryBox = new ComboBox<>(FXCollections.observableArrayList(Category.values()));
        categoryBox.getSelectionModel().select(Category.ELECTRICITY);
        quantityField = new TextField();
        quantityField.setPromptText("e.g. 120");
        scopeBox = new ComboBox<>(FXCollections.observableArrayList(Scope.values()));
        scopeBox.getSelectionModel().select(Scope.PERSONAL);
        protectedBox = new CheckBox("🛡️ This activity is a necessary / protected choice (e.g. night shift, safety, medical)");
        factorBox = new CheckBox("Use my own custom electricity factor (defaults to verified CEA factor)");
        factorBox.setSelected(false);

        factorValue = new TextField();
        factorValue.setPromptText("e.g. 0.82");
        metricBox = new ComboBox<>(FXCollections.observableArrayList("CO2", "CO2E"));
        metricBox.getSelectionModel().selectFirst();
        factorSource = new TextField();
        factorSource.setPromptText("Source and version");
        factorYear = new TextField();
        factorYear.setPromptText("Reference year");
        factorGeography = new TextField();
        factorGeography.setPromptText("Region or geography");
        factorBoundary = new TextField();
        factorBoundary.setPromptText("System boundary or method");
        noteArea = new TextArea();
        noteArea.setPromptText("Optional context; do not enter sensitive personal or health information.");
        noteArea.setPrefRowCount(3);

        GridPane grid = new GridPane();
        grid.setHgap(16); grid.setVgap(12); grid.setPadding(new Insets(18, 20, 18, 20));
        int row = 0;
        Label categoryLabel = new Label("Activity Category"); categoryLabel.setLabelFor(categoryBox);
        grid.add(categoryLabel, 0, row); grid.add(categoryBox, 1, row++);
        quantityLabel = new Label();
        quantityLabel.setLabelFor(quantityField);
        grid.add(quantityLabel, 0, row); grid.add(quantityField, 1, row++);
        Label scopeLabel = new Label("Accounting Scope"); scopeLabel.setLabelFor(scopeBox);
        grid.add(scopeLabel, 0, row); grid.add(scopeBox, 1, row++);
        grid.add(protectedBox, 1, row++);
        grid.add(factorBox, 1, row++);
        Label factorValueLabel = new Label("Factor Value (kg/unit)"); factorValueLabel.setLabelFor(factorValue);
        grid.add(factorValueLabel, 0, row); grid.add(factorValue, 1, row++);
        Label metricLabel = new Label("Emissions Metric"); metricLabel.setLabelFor(metricBox);
        grid.add(metricLabel, 0, row); grid.add(metricBox, 1, row++);
        Label sourceLabel = new Label("Factor Source"); sourceLabel.setLabelFor(factorSource);
        grid.add(sourceLabel, 0, row); grid.add(factorSource, 1, row++);
        Label yearLabel = new Label("Reference Year"); yearLabel.setLabelFor(factorYear);
        grid.add(yearLabel, 0, row); grid.add(factorYear, 1, row++);
        Label geographyLabel = new Label("Geography"); geographyLabel.setLabelFor(factorGeography);
        grid.add(geographyLabel, 0, row); grid.add(factorGeography, 1, row++);
        Label boundaryLabel = new Label("Boundary / Method"); boundaryLabel.setLabelFor(factorBoundary);
        grid.add(boundaryLabel, 0, row); grid.add(factorBoundary, 1, row++);
        Label noteLabel = new Label("Context Note"); noteLabel.setLabelFor(noteArea);
        grid.add(noteLabel, 0, row); grid.add(noteArea, 1, row++);
        GridPane.setHgrow(quantityField, Priority.ALWAYS);
        GridPane.setHgrow(factorSource, Priority.ALWAYS);
        GridPane.setHgrow(factorGeography, Priority.ALWAYS);
        GridPane.setHgrow(factorBoundary, Priority.ALWAYS);
        GridPane.setHgrow(noteArea, Priority.ALWAYS);

        VBox formCard = new VBox(12, grid);
        formCard.getStyleClass().add("content-card");

        Label explanation = new Label("💡 Unknown is different from zero. Only verified categories (like Electricity) compute numeric estimates; other categories (packaging, repair) are retained descriptively to maintain mathematical honesty.");
        explanation.setWrapText(true);
        explanation.getStyleClass().add("callout");

        saveActivityButton = new Button("💾 Save Activity Record");
        saveActivityButton.getStyleClass().add("primary-button");
        saveActivityButton.setDisable(loadFailed);
        saveActivityButton.setOnAction(event -> saveActivity());
        Button clear = new Button("🔄 Clear Form");
        clear.setOnAction(event -> clearForm());

        Label storeWarning = new Label(loadFailed
                ? "⚠️ Editing is disabled because the local store could not be loaded. Check file permissions."
                : "🔒 All data is persisted privately and locally in ~/.footprint-lens/records.csv (zero cloud tracking).");
        storeWarning.setWrapText(true);
        storeWarning.getStyleClass().addAll("callout", loadFailed ? "callout-warning" : "callout");

        HBox buttons = new HBox(12, saveActivityButton, clear);
        buttons.setPadding(new Insets(8, 0, 16, 0));

        VBox root = new VBox(16, explanation, storeWarning, formCard, buttons);
        root.setPadding(new Insets(24));
        categoryBox.setOnAction(event -> updateCategoryState());
        factorBox.setOnAction(event -> updateFactorState());
        updateCategoryState();
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return scroll;
    }

    private void updateCategoryState() {
        Category category = categoryBox.getValue();
        if (lastCategory != null && lastCategory != category && quantityField != null
                && !quantityField.getText().isBlank()) {
            quantityField.clear();
            setStatus("Category changed; enter a new quantity in the new unit.");
        }
        lastCategory = category;
        boolean electricity = category == Category.ELECTRICITY;
        quantityLabel.setText("Quantity (" + category.unit() + ")");
        factorBox.setDisable(!electricity);
        if (!electricity) factorBox.setSelected(false);
        factorValue.setDisable(!electricity || !factorBox.isSelected());
        metricBox.setDisable(!electricity || !factorBox.isSelected());
        factorSource.setDisable(!electricity || !factorBox.isSelected());
        factorYear.setDisable(!electricity || !factorBox.isSelected());
        factorGeography.setDisable(!electricity || !factorBox.isSelected());
        factorBoundary.setDisable(!electricity || !factorBox.isSelected());
    }

    private void updateFactorState() {
        boolean enabled = factorBox.isSelected() && categoryBox.getValue() == Category.ELECTRICITY;
        factorValue.setDisable(!enabled); metricBox.setDisable(!enabled); factorSource.setDisable(!enabled);
        factorYear.setDisable(!enabled); factorGeography.setDisable(!enabled); factorBoundary.setDisable(!enabled);
    }

    private ScrollPane buildActions() {
        Label intro = new Label("Select an activity from the Overview tab. The Choice Coach recommends practical, feasible steps. It strictly respects protected lifestyle constraints and suppresses punitive advice.");
        intro.setWrapText(true);
        intro.getStyleClass().add("callout");

        actionSelectionLabel = new Label("No record selected.");
        actionSelectionLabel.getStyleClass().add("muted");
        actionSelectionLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        TextArea actionText = new TextArea();
        actionText.setEditable(false); actionText.setWrapText(true); actionText.setPrefRowCount(8);
        actionText.setPromptText("Select a record, then inspect its available action or comparison.");
        actionText.setAccessibleText("Action explanation and result");

        showActionButton = new Button("💡 Show Eligible Action");
        showActionButton.getStyleClass().add("primary-button");
        chooseActionButton = new Button("✅ Choose Action Plan");
        dismissActionButton = new Button("❌ Not Feasible");
        compareActionButton = new Button("⚖️ Compare Alternative");

        showActionButton.setOnAction(event -> showAction(actionText));
        chooseActionButton.setOnAction(event -> changeAction(actionText, true));
        dismissActionButton.setOnAction(event -> changeAction(actionText, false));
        compareActionButton.setOnAction(event -> compareSelected());

        FlowPane buttons = new FlowPane(12, 12, showActionButton, chooseActionButton,
                dismissActionButton, compareActionButton);

        VBox actionCard = new VBox(14, actionSelectionLabel, actionText, buttons);
        actionCard.getStyleClass().add("content-card");

        VBox root = new VBox(18, intro, actionCard);
        root.setPadding(new Insets(24));
        VBox.setVgrow(actionCard, Priority.ALWAYS);
        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        updateActionControls();
        return scroll;
    }

    private void updateActionControls() {
        if (showActionButton == null) return;
        Activity activity = selected();
        boolean selected = activity != null;
        boolean hasPlan = selected && workspace.suggestion(activity).isPresent();
        boolean canCompare = selected && activity.category() == Category.ELECTRICITY
                && activity.factor() != null && !activity.protectedChoice();
        actionSelectionLabel.setText(selected
                ? "Selected: " + activity.displayName()
                : "No record selected.");
        showActionButton.setDisable(!selected);
        chooseActionButton.setDisable(!hasPlan);
        dismissActionButton.setDisable(!hasPlan);
        compareActionButton.setDisable(!canCompare);
    }

    private VBox buildAbout() {
        Label heading = new Label("🌱 About Footprint Lens & Ethical Boundaries");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        heading.getStyleClass().add("section-header");
        Label text = new Label("Footprint Lens maintains absolute mathematical transparency between recorded quantities, verified emission factors, and resulting estimates.\n\n"
                + "• Epistemic Honesty: Categories lacking certified regional factors (packaging, repair/reuse) remain descriptive and return 'Estimate Unavailable' rather than fabricating speculative numbers.\n"
                + "• Protected Lifestyle Constraints: Necessary choices (late-night safety commutes, medical appliances) suppress punitive advice.\n"
                + "• Scope Isolation: Household collective footprints are kept distinct and are never divided among flatmates.\n"
                + "• Zero-Telemetry Privacy: All activity records are stored in a local, versioned CSV file (~/.footprint-lens/records.csv). Zero cloud transmission.\n\n"
                + "Designed for Topic 1: Carbon Footprint Awareness and Reduction (India-Centric Context) in COMP2300.");
        text.setWrapText(true);
        text.setStyle("-fx-line-spacing: 4px; -fx-font-size: 13px; -fx-text-fill: #334155;");
        Button assumptions = new Button("📖 View Formal Project Specification");
        assumptions.getStyleClass().add("primary-button");
        assumptions.setOnAction(event -> showInfo("Project Boundaries & Ethics", text.getText()));
        VBox card = new VBox(16, heading, text, assumptions);
        card.getStyleClass().add("content-card");
        VBox root = new VBox(card);
        root.setPadding(new Insets(28));
        return root;
    }

    private void saveActivity() {
        try {
            Category category = categoryBox.getValue();
            if (category == null) throw new IllegalArgumentException("Choose a category");
            String quantityText = quantityField.getText().trim();
            if (quantityText.isBlank()) throw new IllegalArgumentException("Enter a quantity first.");
            BigDecimal quantity = new BigDecimal(quantityText);
            CarbonService.requireNonNegative(quantity, "Quantity");
            if (quantity.compareTo(new BigDecimal("1000000")) > 0)
                throw new IllegalArgumentException("Quantity must not exceed 1,000,000 " + category.unit() + ".");
            Factor factor = null;
            if (factorBox.isSelected()) {
                String factorText = factorValue.getText().trim();
                if (factorText.isBlank()) throw new IllegalArgumentException("Enter a factor value or leave the factor option unchecked.");
                factor = new Factor(new BigDecimal(factorText), "kWh", metricBox.getValue(), factorSource.getText(),
                        factorYear.getText(), factorGeography.getText(), factorBoundary.getText(), FactorStatus.USER_SUPPLIED);
            }
            Activity activity = new Activity(UUID.randomUUID().toString(), category, quantity, scopeBox.getValue(),
                    protectedBox.isSelected(), factor, noteArea.getText(), LocalDate.now());
            workspace.add(activity);
            setStatus("Saved one " + category.label().toLowerCase() + " record. " + activity.estimate().display());
            clearForm();
            refreshOverview();
        } catch (NumberFormatException ex) {
            showError("Enter a valid number in the quantity or factor field. Nothing was saved.");
        } catch (Exception ex) {
            showError(ex.getMessage() == null ? "The activity could not be saved." : ex.getMessage());
        }
    }

    private void clearForm() {
        quantityField.clear();
        protectedBox.setSelected(false);
        factorBox.setSelected(false);
        noteArea.clear();
        updateFactorState();
    }

    private void refreshOverview() {
        if (activityList == null) return;
        activityList.setItems(FXCollections.observableArrayList(workspace.activities()));
        updateActionControls();
        var totals = CarbonService.totalsByMetric(workspace.activities());
        personalTotal.setText(formatMetricTotals(totals.get(Scope.PERSONAL)));
        householdTotal.setText(formatMetricTotals(totals.get(Scope.HOUSEHOLD)));
    }

    private String formatMetricTotals(java.util.Map<String, BigDecimal> totals) {
        if (totals.isEmpty()) return "No numeric estimates";
        return totals.entrySet().stream().sorted(java.util.Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + ": " + entry.getValue().stripTrailingZeros().toPlainString() + " kg")
                .reduce((left, right) -> left + "\n" + right).orElse("No numeric estimates");
    }

    private void showDetails(Activity activity) {
        if (activity == null) { detailArea.clear(); return; }
        detailArea.setText("Category: " + activity.category().label() + "\n"
                + "Quantity: " + activity.quantity().toPlainString() + " " + activity.category().unit() + "\n"
                + "Scope: " + activity.scope().label() + "\n"
                + "Protected choice: " + (activity.protectedChoice() ? "yes — no discretionary advice" : "no") + "\n"
                + "Estimate: " + activity.estimate().display() + "\n"
                + activity.estimate().explanation() + "\n\n"
                + (activity.note().isBlank() ? "No note." : "Note: " + activity.note()));
    }

    private Activity selected() { return activityList == null ? null : activityList.getSelectionModel().getSelectedItem(); }

    private void inspectSelected() {
        Activity activity = selected();
        if (activity == null) { showError("Select a record first."); return; }
        showInfo("Assumptions and boundaries", activity.estimate().explanation()
                + "\n\nThe estimate is a partial illustration. It is not a complete footprint and does not prove a reduction.");
    }

    private void deleteSelected() {
        Activity activity = selected();
        if (activity == null) { showError("Select a record first."); return; }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this local record?", ButtonType.CANCEL, ButtonType.OK);
        confirm.setHeaderText("Confirm deletion");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try { workspace.delete(activity); refreshOverview(); setStatus("Record deleted."); }
            catch (Exception ex) { showError(ex.getMessage()); }
        }
    }

    private void showAction(TextArea output) {
        Activity activity = selected();
        if (activity == null) { output.setText("Select a record on the Overview tab first."); return; }
        Optional<ActionPlan> plan = workspace.suggestion(activity);
        output.setText(plan.map(action -> action.title() + "\n\n" + action.explanation())
                .orElse(activity.protectedChoice() ? "This activity is protected. The record remains visible, but Footprint Lens does not produce punitive or discretionary advice for it."
                        : "No new eligible action is currently available for this record."));
    }

    private void changeAction(TextArea output, boolean choose) {
        Activity activity = selected();
        Optional<ActionPlan> plan = activity == null ? Optional.empty() : workspace.suggestion(activity);
        if (plan.isEmpty()) { showAction(output); return; }
        if (choose) workspace.choose(plan.get()); else workspace.dismiss(plan.get());
        output.setText(choose ? "Action chosen as a current plan. This does not record completed savings." : "Action marked not feasible for now. You can reconsider later.");
        setStatus(choose ? "Current action plan recorded." : "Action deferred without judgement.");
        updateActionControls();
    }

    private void compareSelected() {
        Activity activity = selected();
        if (activity == null || activity.category() != Category.ELECTRICITY) { showError("Select an electricity record first."); return; }
        TextInputDialog dialog = new TextInputDialog(activity.quantity().toPlainString());
        dialog.setTitle("Compare a possible quantity");
        dialog.setHeaderText("Same factor and scope; no record will be changed");
        dialog.setContentText("Alternative kWh:");
        Optional<String> answer = dialog.showAndWait();
        if (answer.isEmpty()) return;
        try {
            BigDecimal difference = CarbonService.compare(activity, new BigDecimal(answer.get().trim()));
            String direction = difference.signum() >= 0 ? "potentially lower by " : "potentially higher by ";
            showInfo("Comparison", "Under the same user-supplied factor, the alternative is " + direction
                    + difference.abs().stripTrailingZeros().toPlainString() + " kg " + activity.factor().metric()
                    + ". This is a calculation illustration, not a measured saving.");
        } catch (Exception ex) { showError(ex.getMessage()); }
    }

    private void setStatus(String message) { if (statusLabel != null) statusLabel.setText(message); }
    private void showError(String message) { showInfo("Needs correction", message == null ? "Unknown error" : message); }
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); alert.setHeaderText(null); alert.setContentText(message); alert.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}
