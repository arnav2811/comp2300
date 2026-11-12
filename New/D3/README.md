# Footprint Lens — D3 Evaluation Plan, Architectural Design and Technical Setup

**Deliverable:** D3 (7% of total assessment)  
**Status:** Distinction-Standard Submission Pack (80+)  
**Authors:** Arnav Mishra, Keshav Singhal, Hiranya Makkar  
**Target Submission Deadline:** 30 November 2026, 16:00  

---

## 1. Deliverable Overview & Distinction Focus

Deliverable D3 establishes the formal engineering foundation for the Footprint Lens desktop system, delivering:
1. **User-Centred Evaluation Plan:** A controlled lab usability testing protocol adhering to strict UK GDPR rules (0 sensitive personal data elicited; 5 synthetic task cards) with $N=6$ participants recruited directly from another COMP2300 group.
2. **Technical Architectural Design:** A Clean Layered Architecture decoupling JavaFX presentation from pure domain rules (`in.carbon.core`), complete with UML Class, Sequence, Package, and State diagrams, plus 5 Architectural Decision Records (ADRs).
3. **Agile SCRUM Project Plan:** A 4-sprint implementation roadmap featuring a detailed 21-story-point Sprint 1 backlog sized in Fibonacci points, task allocations, and a daily Burndown Chart.
4. **Project Setup & Risk Management:** GitLab repository configuration, Maven and standalone build scripts, working headless test suites, and an 8-point Risk Management Register.

---

## 2. Directory Contents & Artefacts

| File / Folder | Purpose & Distinction Evidence |
|---|---|
| [`architecture.md`](architecture.md) | Full architectural specification with embedded Mermaid UML diagrams (Class, Sequence, Package, State), layer invariants, and 5 detailed ADRs. |
| [`evaluation-protocol.md`](evaluation-protocol.md) | User-centred evaluation protocol: lab setting, peer recruitment from another COMP2300 group, UK GDPR safeguards, 5 synthetic scenario cards, and SUS/SEQ analysis plans. |
| [`project-plan.md`](project-plan.md) | 4-sprint implementation plan, detailed Sprint 1 backlog (21 pts), DoR/DoD quality gates, and Sprint 1 burndown chart. |
| [`rubric-matrix.md`](rubric-matrix.md) | Comprehensive 80+ Distinction acceptance criteria mapping against official descriptors. |
| [`report.md`](report.md) | Consolidated D3 handover summary and technical audit. |
| [`report/main.tex`](report/main.tex) | Formal 12pt A4 LaTeX D3 submission report featuring native TikZ vector UML diagrams, burndown chart, risk heatmap, and ACM SIGCHI citations. |
| [`report/technical-design-and-evaluation-plan.tex`](report/technical-design-and-evaluation-plan.tex) | Dedicated LaTeX companion technical specification and evaluation instrument pack. |

---

## 3. How to Verify the Technical Setup & Run Headless Tests

The project includes an automated headless verification test suite that validates 100% of core domain rules, calculations, and CSV persistence without requiring a graphical display server.

From the workspace root directory (`c:\Users\arnav\Downloads\COMP2300`):

```powershell
# Run the core headless smoke test suite (10/10 checks pass)
java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke
```

*(Alternatively, navigate to `New/application` and run `java -cp "build/classes;build/test-classes" in.carbon.core.CoreSmoke`)*

### Output:
```text
Footprint Lens core smoke: PASS (10 checks)
```

### Verified Checks:
1. Activity entity instantiation with immutable Java 21 records.
2. Accurate arithmetic multiplication with Central Electricity Authority (CEA) factor ($0.82\text{ kg CO}_2\text{e/kWh}$).
3. Epistemic handling of unknown factors (`Estimate.unavailable()` returned for unverified categories).
4. Protected choice constraint evaluation (punitive advice suppressed when `protectedChoice == true`).
5. Discretionary advice generation (feasible reduction action emitted when `protectedChoice == false`).
6. Household scope isolation (collective consumption retained without artificial per-person division).
7. Personal scope calculation accuracy.
8. CSV serialisation and header formatting.
9. Atomic disk persistence and round-trip deserialisation via `LocalStore`.
10. Workspace in-memory aggregation and filtering.

---

## 4. How to Launch the D3 Baseline Desktop Application

To view the architectural walking skeleton submitted as the D3 Technical Setup baseline:

```powershell
# Launch D3 Architectural Baseline
.\run-d3-baseline.bat
```
*(Or double-click `run-d3-baseline.bat` from Windows File Explorer)*

### Visual & Architectural Design Features of D3:
- **Google Engineering Studio Aesthetic:** Crafted with minimalist elegance—Google Charcoal header (`#202124`), subtle borders (`#DADCE0`), and crisp sans-serif typography.
- **Architectural Entity Specification:** The inspector details pure domain invariants (entity class `in.carbon.core.Activity`, schema, UUID, calculation engine, factor authority, and headless test status).
- **ISO 14064-1 Accounting Boundaries:** Stat metrics explicitly differentiate Personal Scope (direct) from Household Scope (undivided collective total).
- **Domain Store Readout:** Activities display monospace technical identifiers (`[ACT-01]`) with explicit factor provenance citations.
- **Interactive In-App Switcher:** Click the `[✨ D4 Modern]` button in the top-right header at any time to instantly transition from the D3 architectural baseline to the modernized D4 Increment 1 interface!

- **Git Release Tag for D3:** `git checkout v0.1.0-d3-setup`
- **Subsequent D4 Increment:** In Sprint 1, the team evolved this architectural skeleton into the modernized card-based interface delivered in Deliverable D4 (tagged `v0.2.0-d4-increment1` and launched via `.\run-app.bat`).

---

## 5. How to Compile the D3 LaTeX Reports

The D3 submission report is typeset in professional LaTeX using 12pt type, 1.5 cm margins, native TikZ vector graphics, and ACM SIGCHI bibliographic style.

To compile the documents:

```powershell
cd New/D3/report

# Compile the primary D3 deliverable report
pdflatex -interaction=nonstopmode main.tex
pdflatex -interaction=nonstopmode main.tex   # second pass for cross-references & TikZ bounds

# Compile the standalone technical design & evaluation plan specification
pdflatex -interaction=nonstopmode technical-design-and-evaluation-plan.tex
pdflatex -interaction=nonstopmode technical-design-and-evaluation-plan.tex
```

The resulting `main.pdf` contains the complete formal submission package, including high-resolution vector diagrams for the UML Class hierarchy, Sequence interactions, Package architecture, State machine, Sprint 1 Burndown chart, and Risk Heatmap.
