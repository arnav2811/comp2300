# D4 Report — Increment 1 (Sprint 1 Delivery)

**Status:** Distinction-Standard Submission Pack (80+)  
**Authors:** Arnav Mishra, Keshav Singhal, Hiranya Makkar  
**Deliverable:** COMP2300 Deliverable 4 — Increment 1 (7%)  
**Product Stage:** First Runnable Vertical Slice & Modern Card Interface  
**Git Release Tag:** `v0.2.0-d4-increment1` (built upon `v0.1.0-d3-setup`)  
**Formal Report Source:** [`report/main.tex`](report/main.tex)  

---

## 1. Executive Summary & Progression from D3

Deliverable D4 marks the successful completion of **Agile Sprint 1**, transforming the initial unstyled architectural walking skeleton submitted for D3 (`v0.1.0-d3-setup`) into a fully realized, testable, and modernized desktop application (`v0.2.0-d4-increment1`).

### D3 Baseline vs. D4 Increment 1 Comparison

| Metric / Dimension | D3 Technical Setup Baseline (`v0.1.0`) | D4 Increment 1 Delivery (`v0.2.0`) |
|---|---|---|
| **Objective** | Architecture design, UML models, evaluation plan, and build environment. | Runnable vertical slice delivering Sprint 1 user stories with full acceptance testing. |
| **User Interface** | Raw unstyled system controls (plain text cells, basic grey buttons). | Modern card-based design system: forest emerald banner, category emoji chips, stat elevation, gold constraint badges. |
| **Story Backlog** | 21 Story Points committed in Sprint 1 backlog. | **100% Done:** `D2-US01`, `D2-US02`, `D2-US03`, `D2-US04`, `D2-US07` accepted. |
| **Persistence** | Initial CSV header validation. | Atomic CSV storage engine (`LocalStore`) with session synchronization. |
| **Launch Commands** | `.\run-d3-baseline.bat` | `.\run-app.bat` (with live in-app toggle between views). |

---

## 2. Sprint 1 User Story Acceptance

All 21 committed Fibonacci Story Points have been delivered and accepted against strict Given-When-Then criteria:

1. **D2-US01 (Activity Logging, 5 pts):** Dynamic unit adaptation (`kWh`, `km`, `litres`, `kg`, `items`) and personal vs. household scope isolation.
2. **D2-US02 (Epistemic Factor Calculation, 5 pts):** Central Electricity Authority (CEA) verified factor applied ($0.82\text{ kg CO}_2\text{e/kWh}$); unverified categories return explicit `Estimate.unavailable()`.
3. **D2-US03 (Provenance Inspection, 3 pts):** `🔍 Inspect Assumptions` drawer reveals calculation formulas, geographic source, and reference year.
4. **D2-US04 (Validation & Error Recovery, 5 pts):** Gracefully rejects negative quantities, non-numeric input, and excessive numbers ($> 1,000,000$) without losing user input or corrupting the CSV store.
5. **D2-US07 (Descriptive Stewardship, 3 pts):** Allows recording packaging and appliance repairs descriptively without fabricating unsupported carbon numbers.

---

## 3. How to Launch and Verify

```powershell
# 1. Run headless smoke test suite (10/10 checks pass)
java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke

# 2. Launch D4 modern increment application
.\run-app.bat

# 3. Launch D3 architectural baseline application (for before/after comparison)
.\run-d3-baseline.bat
```

---

## 4. Formal LaTeX Compilation

To compile the formal D4 submission report:
```powershell
cd New/D4/report
pdflatex -interaction=nonstopmode main.tex
pdflatex -interaction=nonstopmode main.tex
```
