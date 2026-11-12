# COMP2300 Software Engineering Group Project — Footprint Lens

**Course:** COMP2300 Software Engineering Group Project  
**Topic:** Topic 1: Carbon Footprint Awareness and Reduction (India-Centric Context)  
**Authors (Team 1):**
- **Arnav Mishra** (Product Owner & Architecture Lead)
- **Keshav Singhal** (Scrum Master & Persistence/CI Lead)
- **Hiranya Makkar** (UI/UX & Evaluation Lead)

**Repository Status:** Distinction-Standard Deliverables (80+ Benchmark)  
**Release Tags:**
- `v0.1.0-d3-setup` — D3 Technical Setup & Architectural Walking Skeleton Baseline
- `v0.2.0-d4-increment1` — D4 Sprint 1 Modern Increment 1 Delivery

---

## 1. Quick-Start: How to Run and Experience the System

### A. Run the D4 Modern Increment 1 Application (Default)
Double-click **`run-app.bat`** in this root directory or run:
```powershell
.\run-app.bat
```
*(Or in PowerShell: `.\run-app.ps1`)*

- **What you will see:** The full Sprint 1 increment built with **Google Material You & Apple HIG design aesthetics**:
  - Forest emerald header with mode switcher
  - Tonal eco-green & sapphire stat cards
  - Real-time Category Filter Chips (`[All]`, `[⚡ Electricity]`, `[🚗 Travel]`, `[🥗 Food]`, `[💧 Water]`, `[📦 Packaging]`, `[🗑️ Waste]`, `[♻️ Repair]`)
  - Elevated Activity cards with circular avatars and gold `🛡️ Protected Choice` badges
  - Live Provenance & Assumptions Inspector displaying exact formulas and Central Electricity Authority (CEA) v20.0 factor citations

---

### B. Run the D3 Architectural Baseline Application
Double-click **`run-d3-baseline.bat`** in this root directory or run:
```powershell
.\run-d3-baseline.bat
```
*(Or in PowerShell: `.\run-d3-baseline.ps1`)*

- **What you will see:** The architectural walking skeleton submitted for Deliverable D3, designed in a **Google Engineering Studio architectural blueprint aesthetic**:
  - Google Charcoal header (`#202124`) with `[Architectural Baseline]` badge
  - ISO 14064-1 scope boundary metrics (Personal vs Undivided Household)
  - Architectural Entity readout with monospace tags (`[ACT-01]`) and schema details
  - Architectural Invariants & Provenance Inspector displaying entity class, UUID, calculation engine, factor authority, and headless test status

---

### C. Live In-App View Toggle (Real-Time Comparison)
Whenever either application is open, click the segmented button in the top-right of the header:
- Click **`🏛️ D3 Baseline`** to view the architectural engineering blueprint.
- Click **`✨ D4 Modern`** to return to the polished Material You Increment 1 interface.

---

### D. Run Automated Headless Verification Tests
The domain calculation rules and CSV persistence in `in.carbon.core` are 100% testable without requiring a graphical display or Maven:
```powershell
java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke
```
**Expected Output:** `Footprint Lens core smoke: PASS (10 checks)`

---

### E. Run the Interactive D2 Low-Fidelity Prototype
```powershell
python -m http.server 8080 --directory New/D2/prototype
```
Open [`http://localhost:8080`](http://localhost:8080) in your web browser.  
To run the automated prototype test suite:
```powershell
node --test New/D2/prototype/prototype-logic.test.mjs    # 8/8 tests pass
python New/D2/prototype/prototype-structure-check.py   # DOM & accessibility check passes
```

---

## 2. Deliverable Documentation Index

| Deliverable | Key Focus | Primary Documents |
|---|---|---|
| **D2: Conceptual Design (7%)** | 3 Distinct Concepts, Decision Matrix, 17 MoSCoW Stories, Interactive Lo-Fi Prototype | [`New/D2/README.md`](New/D2/README.md), [`New/D2/report/main.tex`](New/D2/report/main.tex), [`New/D2/conceptual-design-working.md`](New/D2/conceptual-design-working.md), [`New/D2/rubric-matrix.md`](New/D2/rubric-matrix.md) |
| **D3: Architecture & Setup (7%)** | Lab Think-Aloud Evaluation Plan (UK GDPR safe), Clean Layered Architecture, UML Diagrams, 4-Sprint Roadmap (21-pt Sprint 1), Risk Register | [`New/D3/README.md`](New/D3/README.md), [`New/D3/report/main.tex`](New/D3/report/main.tex), [`New/D3/architecture.md`](New/D3/architecture.md), [`New/D3/evaluation-protocol.md`](New/D3/evaluation-protocol.md), [`New/D3/project-plan.md`](New/D3/project-plan.md) |
| **D4: Increment 1 (7%)** | Delivered Sprint 1 Increment, D3-to-D4 Progression, Burndown (21 $\to$ 0 pts), Flawless Test Suite, Intuitive User Controls | [`New/D4/README.md`](New/D4/README.md), [`New/D4/report/main.tex`](New/D4/report/main.tex), [`New/D4/report.md`](New/D4/report.md), [`New/D4/rubric-matrix.md`](New/D4/rubric-matrix.md) |
| **Technical Core** | Pure Java 21 Domain Model, OpenJFX 23 UI, RFC 4180 CSV Atomic Store | [`New/application/README.md`](New/application/README.md), [`New/CHANGELOG.md`](New/CHANGELOG.md) |

For comprehensive architectural specifications, UML vector diagrams, and complete rubric matrices, please refer to [`New/README.md`](New/README.md).
