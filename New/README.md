# COMP2300 Footprint Lens — Project Master Documentation

**Course:** COMP2300 Software Engineering Group Project  
**Topic:** Topic 1: Carbon Footprint Awareness and Reduction — India-Centric Context  
**Team Members:**  
- **Arnav Mishra** (Product Owner & Architecture Lead)
- **Keshav Singhal** (Scrum Master & Persistence/CI Lead)
- **Hiranya Makkar** (UI/UX & Evaluation Lead)

**Repository Status:** Distinction-Standard Deliverables (D2 & D3 80+ Ready)  
**Implementation Technologies:** Java 21+ / JavaFX 21+ Desktop System, Modern HTML5/ES Module Lo-Fi Prototype, LaTeX (TikZ Vector Graphics)  

---

## 1. Master Quick-Start: How to Run and Verify Everything

### 1.1 Run the D2 Low-Fidelity Prototype
The D2 prototype is an interactive, accessible simulation of the **Contextual Choice Coach**.

1. **Start the local HTTP server** (from the workspace root):
   ```powershell
   python -m http.server 8080 --directory New/D2/prototype
   ```
2. **Open in browser:**
   Navigate to [`http://localhost:8080`](http://localhost:8080).
3. **Run automated prototype tests:**
   ```powershell
   # Run Node.js unit tests for calculation logic (8/8 pass)
   node --test New/D2/prototype/prototype-logic.test.mjs

   # Run DOM structure and accessibility checks
   python New/D2/prototype/prototype-structure-check.py
   ```

### 1.2 Run & Compare the Java Desktop Applications (D3 vs. D4)
The Java application supports running either the **D3 Architectural Baseline** or the **D4 Modernized Increment 1**:

1. **Run D4 Modernized Increment 1 (Default):**
   ```powershell
   .\run-app.bat
   ```
   *(Or double-click `run-app.bat` from Windows File Explorer)*  
   *Launches the modern card-based interface with category emoji chips (`⚡`, `🚗`), scope tags, gold `🛡️ Protected` indicators, and full Sprint 1 features (Git Tag: `v0.2.0-d4-increment1`).*

2. **Run D3 Architectural Baseline:**
   ```powershell
   .\run-d3-baseline.bat
   ```
   *(Or double-click `run-d3-baseline.bat` from Windows File Explorer)*  
   *Launches the initial unstyled walking skeleton submitted as the D3 technical setup baseline prior to Sprint 1 (Git Tag: `v0.1.0-d3-setup`).*

3. **In-App Real-Time Toggle:**
   Click the button in the top-right corner of the application header at any time:
   * Click **`🏛️ Switch to D3 Baseline View`** to inspect the unstyled architectural baseline.
   * Click **`✨ Switch to D4 Modern View`** to return to the polished Increment 1 interface.

4. **Run the Headless Smoke Test Suite** (Zero GUI/Maven dependencies):
   ```powershell
   java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke
   ```
   *(Expected result: `Footprint Lens core smoke: PASS (10 checks)`)*

5. **Standard Maven build** (if Maven is installed):
   ```powershell
   cd New/application
   mvn clean verify      # Compiles code, executes JUnit 5 tests, generates JaCoCo report
   ```

### 1.3 Compile the Formal LaTeX Reports
Both D2 and D3 submission packs include formal 12pt A4 LaTeX reports formatted to ACM SIGCHI standards with native TikZ vector graphics:

```powershell
# Compile D2 Formal Report
cd New/D2/report
pdflatex -interaction=nonstopmode main.tex
pdflatex -interaction=nonstopmode main.tex

# Compile D3 Formal Report
cd New/D3/report
pdflatex -interaction=nonstopmode main.tex
pdflatex -interaction=nonstopmode main.tex
```

---

## 2. Deliverable Directory Map

```text
COMP2300/
├── New/
│   ├── README.md                           # Master project guide & quick-start
│   ├── CHANGELOG.md                        # Formal engineering change log
│   │
│   ├── D2/                                 # DELIVERABLE D2: CONCEPTUAL DESIGN (7%)
│   │   ├── README.md                       # D2 guide & walkthrough
│   │   ├── prototype/                      # Interactive Lo-Fi working prototype
│   │   │   ├── index.html                  # Accessible prototype shell
│   │   │   ├── app.js                      # UI event handling & navigation
│   │   │   ├── prototype-logic.mjs         # Pure domain rules & math
│   │   │   └── prototype-logic.test.mjs    # Automated Node.js unit tests (8/8 pass)
│   │   ├── report/                         # Formal LaTeX submission reports
│   │   │   ├── main.tex                    # Full D2 formal report (TikZ diagrams)
│   │   │   └── design-concepts-and-user-stories.tex # Concepts & 17 MoSCoW stories
│   │   ├── concepts-and-diagrams.md        # Concept sketches & decision matrix
│   │   ├── conceptual-design-working.md    # Working conceptual design specification
│   │   └── rubric-matrix.md                # 80+ Distinction acceptance matrix
│   │
│   ├── D3/                                 # DELIVERABLE D3: ARCHITECTURE & PLAN (7%)
│   │   ├── README.md                       # D3 guide & verification instructions
│   │   ├── architecture.md                 # Clean Layered Architecture & UML models
│   │   ├── evaluation-protocol.md          # User-centred lab protocol & UK GDPR cards
│   │   ├── project-plan.md                 # 4-sprint plan, 21-pt Sprint 1 backlog, burndown
│   │   ├── rubric-matrix.md                # 80+ Distinction acceptance matrix
│   │   ├── report/                         # Formal LaTeX submission reports
│   │   │   ├── main.tex                    # Full D3 formal report (TikZ UML & burndown)
│   │   │   └── technical-design-and-evaluation-plan.tex # Companion specification
│   │   └── report.md                       # Integrated handover summary
│   │
│   ├── application/                        # PRODUCTION JAVA/JAVAFX SYSTEM
│   │   ├── README.md                       # Build, run, and architecture guide
│   │   ├── pom.xml                         # Apache Maven build descriptor
│   │   ├── src/main/java/in/carbon/core/   # Pure domain model & calculation rules
│   │   ├── src/main/java/in/carbon/ui/     # JavaFX desktop presentation layer
│   │   ├── src/test/java/in/carbon/core/   # Automated JUnit 5 tests & CoreSmoke runner
│   │   └── scripts/                        # Standalone build scripts (PowerShell & Bash)
│   │
│   └── docs/                               # Governance, handbook, and traceability
```

---

## 3. Distinction Quality Highlights (80+ Standard)

### D2: Conceptual Design
- **Three Distinct Concepts:** Ideated and evaluated across 5 decision dimensions (*Contextual Choice Coach*, *Community Ledger*, *Tariff Scraper*). Choice justified via a Multi-Criteria Decision Matrix.
- **17 MoSCoW User Stories:** Prioritised into Must, Should, Could, and Won't have, each with rich Given-When-Then acceptance criteria linked directly to personas (*Aanya*, *Rohan*, *Meera*).
- **Novelty & Feasibility:** Eliminates speculative machine learning; leverages verified Central Electricity Authority (CEA) emission factors; works 100% offline.
- **Interactive Lo-Fi Prototype:** 7 distinct interaction states with dynamic units, Enter-key submission, non-destructive navigation, assumptions inspector, and screen-reader accessibility.

### D3: Evaluation Plan, Architectural Design & Technical Setup
- **Rigorous Evaluation Plan:** Controlled lab usability testing with concurrent think-aloud protocol. Participants ($N=6$) recruited strictly from another COMP2300 group. Adheres strictly to UK GDPR: 0 sensitive personal data elicited; uses 5 pre-printed synthetic scenario cards. Quantitative benchmarks: Task completion $\ge 80\%$, System Usability Scale (SUS) $\ge 75$.
- **Clean Layered Architecture:** Decouples JavaFX UI from pure domain core (`in.carbon.core`). 100% of calculation rules, factor conversions, and CSV persistence are headless-testable without a display server. Complete UML models (Class, Sequence, Package, State).
- **Agile SCRUM Project Plan:** 4-sprint roadmap, granular Sprint 1 backlog sized in Fibonacci story points (total 21 pts) with assigned owners, and daily Burndown tracking.
- **Project Setup & 8-Point Risk Register:** Version-controlled repository, Maven and standalone build scripts, working headless smoke test passing 10/10 assertions, and comprehensive risk mitigation strategies.

---

## 4. Key References & Traceability
- [`D2/rubric-matrix.md`](D2/rubric-matrix.md) — D2 distinction acceptance matrix.
- [`D3/rubric-matrix.md`](D3/rubric-matrix.md) — D3 distinction acceptance matrix.
- [`docs/requirements-traceability.md`](docs/requirements-traceability.md) — Requirements to source code traceability.
- [`docs/gitlab-handbook.md`](docs/gitlab-handbook.md) — Git workflow and branching conventions.
