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

    @Override
    public void start(Stage stage) {
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

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-root");
        root.setTop(buildHeader());
        root.setCenter(buildTabs());
        statusLabel = new Label(initialStatus);
        statusLabel.getStyleClass().add("status-bar");
        root.setBottom(statusLabel);

        Scene scene = new Scene(root, 1120, 760);
        var css = getClass().getResource("/style.css");
        if (css != null) scene.getStylesheets().add(css.toExternalForm());
        stage.setTitle("Footprint Lens — understand, compare, choose");
        stage.setMinWidth(720);
        stage.setMinHeight(650);
        stage.setScene(scene);
        stage.show();
        refreshOverview();
    }

    private VBox buildHeader() {
        Label title = new Label("Footprint Lens");
        title.setFont(Font.font("System", FontWeight.BOLD, 28));
        title.getStyleClass().add("title");
        Label subtitle = new Label("India-centric footprint awareness — transparent, feasible and non-judgemental");
        subtitle.getStyleClass().add("subtitle");
        VBox header = new VBox(4, title, subtitle);
        header.setPadding(new Insets(22, 28, 18, 28));
        header.getStyleClass().add("header");
        return header;
    }

    private TabPane buildTabs() {
        TabPane tabs = new TabPane();
        Tab overview = new Tab("Overview & records", buildOverview());
        overview.setClosable(false);
        Tab add = new Tab("Add activity", buildEntryForm());
        add.setClosable(false);
        Tab actions = new Tab("Actions & comparisons", buildActions());
        actions.setClosable(false);
        Tab help = new Tab("About & boundaries", buildAbout());
        help.setClosable(false);
        tabs.getTabs().addAll(overview, add, actions, help);
        return tabs;
    }

    private BorderPane buildOverview() {
        personalTotal = new Label();
        householdTotal = new Label();
        personalTotal.setAccessibleText("Partial personal estimated total");
        householdTotal.setAccessibleText("Partial household estimated total");
        HBox summary = new HBox(18, statCard("Personal estimated total (partial)", personalTotal),
                statCard("Household estimated total (partial)", householdTotal));
        summary.setPadding(new Insets(20, 24, 12, 24));

        activityList = new ListView<>();
        activityList.setAccessibleText("Saved activity records");
        activityList.setPlaceholder(new Label("No activities yet. Start with one small record."));
        activityList.setCellFactory(view -> new ListCell<>() {
            @Override protected void updateItem(Activity item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setGraphic(null); }
                else {
                    setText(item.displayName() + "  •  " + item.estimate().display()
                            + (item.protectedChoice() ? "  •  protected choice" : ""));
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

        Button inspect = new Button("Inspect assumptions");
        inspect.setOnAction(event -> inspectSelected());
        Button delete = new Button("Delete selected");
        delete.setOnAction(event -> deleteSelected());
        Button refresh = new Button("Refresh");
        refresh.setOnAction(event -> refreshOverview());
        HBox controls = new HBox(10, inspect, delete, refresh);
        controls.setAlignment(Pos.CENTER_LEFT);

        VBox right = new VBox(12, new Label("Selected record"), detailArea, controls);
        right.setPadding(new Insets(10, 24, 24, 12));
        right.setPrefWidth(470);
        VBox left = new VBox(new Label("Your records"), activityList);
        left.setPadding(new Insets(10, 12, 24, 24));
        VBox.setVgrow(activityList, Priority.ALWAYS);
        HBox body = new HBox(left, right);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        return new BorderPane(body, summary, null, null, null);
    }

    private VBox statCard(String title, Label value) {
        Label heading = new Label(title);
        heading.getStyleClass().add("muted");
        value.getStyleClass().add("stat");
        VBox box = new VBox(4, heading, value);
        box.getStyleClass().add("stat-card");
        box.setPadding(new Insets(12));
        box.setMinWidth(250);
        return box;
    }

    private ScrollPane buildEntryForm() {
        categoryBox = new ComboBox<>(FXCollections.observableArrayList(Category.values()));
        categoryBox.getSelectionModel().select(Category.ELECTRICITY);
        quantityField = new TextField();
        quantityField.setPromptText("e.g. 168");
        scopeBox = new ComboBox<>(FXCollections.observableArrayList(Scope.values()));
        scopeBox.getSelectionModel().select(Scope.PERSONAL);
        protectedBox = new CheckBox("This activity is protected / not changeable");
        factorBox = new CheckBox("Use my own unverified electricity factor");
        factorBox.setSelected(false);

        factorValue = new TextField();
        factorValue.setPromptText("e.g. 0.716");
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
        noteArea.setPromptText("Optional context; do not enter names or sensitive personal information.");
        noteArea.setPrefRowCount(3);

        GridPane grid = new GridPane();
        grid.setHgap(14); grid.setVgap(10); grid.setPadding(new Insets(24));
        int row = 0;
        Label categoryLabel = new Label("Activity category"); categoryLabel.setLabelFor(categoryBox);
        grid.add(categoryLabel, 0, row); grid.add(categoryBox, 1, row++);
        quantityLabel = new Label();
        quantityLabel.setLabelFor(quantityField);
        grid.add(quantityLabel, 0, row); grid.add(quantityField, 1, row++);
        Label scopeLabel = new Label("Scope"); scopeLabel.setLabelFor(scopeBox);
        grid.add(scopeLabel, 0, row); grid.add(scopeBox, 1, row++);
        grid.add(protectedBox, 1, row++);
        grid.add(factorBox, 1, row++);
        Label factorValueLabel = new Label("Factor value (kg per kWh)"); factorValueLabel.setLabelFor(factorValue);
        grid.add(factorValueLabel, 0, row); grid.add(factorValue, 1, row++);
        Label metricLabel = new Label("Metric"); metricLabel.setLabelFor(metricBox);
        grid.add(metricLabel, 0, row); grid.add(metricBox, 1, row++);
        Label sourceLabel = new Label("Source"); sourceLabel.setLabelFor(factorSource);
        grid.add(sourceLabel, 0, row); grid.add(factorSource, 1, row++);
        Label yearLabel = new Label("Reference year"); yearLabel.setLabelFor(factorYear);
        grid.add(yearLabel, 0, row); grid.add(factorYear, 1, row++);
        Label geographyLabel = new Label("Geography"); geographyLabel.setLabelFor(factorGeography);
        grid.add(geographyLabel, 0, row); grid.add(factorGeography, 1, row++);
        Label boundaryLabel = new Label("Boundary / method"); boundaryLabel.setLabelFor(factorBoundary);
        grid.add(boundaryLabel, 0, row); grid.add(factorBoundary, 1, row++);
        Label noteLabel = new Label("Note"); noteLabel.setLabelFor(noteArea);
        grid.add(noteLabel, 0, row); grid.add(noteArea, 1, row++);
        GridPane.setHgrow(quantityField, Priority.ALWAYS);
        GridPane.setHgrow(factorSource, Priority.ALWAYS);
        GridPane.setHgrow(factorGeography, Priority.ALWAYS);
        GridPane.setHgrow(factorBoundary, Priority.ALWAYS);
        GridPane.setHgrow(noteArea, Priority.ALWAYS);

        Label explanation = new Label("Unknown is different from zero. Only electricity has a numeric path in this review build; other categories are retained descriptively. A factor is never prefilled: enter and verify its provenance before opting in.");
        explanation.setWrapText(true);
        explanation.getStyleClass().add("callout");
        saveActivityButton = new Button("Save activity");
        saveActivityButton.getStyleClass().add("primary-button");
        saveActivityButton.setDisable(loadFailed);
        saveActivityButton.setOnAction(event -> saveActivity());
        Button clear = new Button("Clear form");
        clear.setOnAction(event -> clearForm());
        Label storeWarning = new Label(loadFailed
                ? "Editing is disabled because the local store could not be loaded. Preserve the original file and repair or choose a new workspace before continuing."
                : "Changes are stored locally; failed validation does not replace the saved file.");
        storeWarning.setWrapText(true);
        storeWarning.getStyleClass().add("callout");
        HBox buttons = new HBox(10, saveActivityButton, clear);
        buttons.setPadding(new Insets(0, 24, 24, 24));
        VBox root = new VBox(explanation, storeWarning, grid, buttons);
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
        Label intro = new Label("Select a record on the Overview tab. Suggestions use explicit rules and never claim completed savings. Action choices are session-only in this review build.");
        intro.setWrapText(true);
        actionSelectionLabel = new Label("No record selected.");
        actionSelectionLabel.getStyleClass().add("muted");
        TextArea actionText = new TextArea();
        actionText.setEditable(false); actionText.setWrapText(true); actionText.setPrefRowCount(8);
        actionText.setPromptText("Select a record, then inspect its available action or comparison.");
        actionText.setAccessibleText("Action explanation and result");
        showActionButton = new Button("Show eligible action");
        chooseActionButton = new Button("Choose action");
        dismissActionButton = new Button("Not feasible");
        compareActionButton = new Button("Compare selected electricity");
        showActionButton.setOnAction(event -> showAction(actionText));
        chooseActionButton.setOnAction(event -> changeAction(actionText, true));
        dismissActionButton.setOnAction(event -> changeAction(actionText, false));
        compareActionButton.setOnAction(event -> compareSelected());
        FlowPane buttons = new FlowPane(10, 10, showActionButton, chooseActionButton,
                dismissActionButton, compareActionButton);
        VBox root = new VBox(16, intro, actionSelectionLabel, actionText, buttons);
        root.setPadding(new Insets(28));
        VBox.setVgrow(actionText, Priority.ALWAYS);
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
        Label heading = new Label("A transparent working product");
        heading.setFont(Font.font("System", FontWeight.BOLD, 20));
        Label text = new Label("Footprint Lens keeps a clear line between a quantity, a factor and an estimate. A missing factor is shown as unavailable, protected choices are not penalised, and household totals are not assigned to people. Illustrative values are visibly labelled.\n\nThis desktop build is offline and stores data locally. It is a design and engineering prototype, not a verified carbon-accounting service. Hindi translation, screen-reader validation, participant evaluation and evidence-backed national factors remain explicit review gates.");
        text.setWrapText(true);
        Button assumptions = new Button("Open project boundaries");
        assumptions.setOnAction(event -> showInfo("Project boundaries", text.getText()));
        VBox root = new VBox(16, heading, text, assumptions);
        root.setPadding(new Insets(30));
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
