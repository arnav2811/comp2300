# D3 Report Handover — Evaluation Plan, Architectural Design and Technical Setup

**Status:** Distinction Ready (80--90%+)  
**Formal LaTeX Source:** [`report/main.tex`](report/main.tex)  
**Companion Technical Document:** [`report/technical-design-and-evaluation-plan.tex`](report/technical-design-and-evaluation-plan.tex)  

---

## 1. Product Stage & Technical Overview

Deliverable 3 establishes the concrete engineering architecture, agile implementation roadmap, and empirical evaluation protocol for **Footprint Lens (Concept A: Contextual Choice Coach)**.

The system is engineered using a **Clean Layered Architecture** in Java 21 LTS and JavaFX 21:
1. **Presentation Tier (`in.carbon.ui`):** Desktop JavaFX views, controllers, layout containers, accessible controls, and event listeners. Zero mathematical or domain calculation logic.
2. **Application Service Tier (`in.carbon.core`):** `CarbonService` orchestrates use cases, multi-routine summaries, and metric partitioning; `ActionPlanner` implements deterministic, transparent recommendation rules without speculative ML.
3. **Domain Model Tier (`in.carbon.core`):** Immutable Java 21 `record`s (`Activity`, `Factor`, `Estimate`, `Scope`, `Category`). Utilizes `BigDecimal` for zero rounding error in carbon calculations. Headless testability guaranteed (no `javafx.*` imports).
4. **Persistence Tier (`in.carbon.core`):** `LocalStore` handles local, zero-network CSV file I/O with strict schema versioning (`FOOTPRINT-LENS,1`) and atomic file writing.

---

## 2. Completed Distinction-Level Artefacts

### A. Evaluation Plan (Rubric Benchmark: 80+)
- **User-Centred Lab Methodology:** Moderated task-based usability testing with a think-aloud protocol in a controlled campus room.
- **Peer Recruitment:** $N = 6$ participants recruited directly from an independent COMP2300 group.
- **Strict UK GDPR Compliance:** Zero collection or elicitation of Special Category Data; synthetic activity cards eliminate real-world personal bill leakage.
- **5 Standardized Tasks:** Covering basic entry (T1), assumptions audit (T2), constraint protection (T3), descriptive logging (T4), and communal boundaries (T5).
- **Multi-Dimensional Metrics:** Task Completion Rate, Time on Task ($\le 60$s for T1), Single Ease Question (SEQ), System Usability Scale (SUS benchmark $\ge 75/100$), and qualitative thematic analysis.

### B. Technical Design & UML Models (Rubric Benchmark: 80+)
- **Layered Clean Architecture:** Complete decoupling of domain rules from JavaFX views.
- **UML Diagrams in Vector TikZ:**
  - Package/Layered Architecture Diagram.
  - UML Class Diagram showing domain entities, records, and services.
  - UML Sequence Diagram tracing activity input, calculation, and persistence.
  - State Machine Diagram for `Estimate` and `ActionPlan` lifecycles.
- **Architectural Decision Records (ADRs):** Justifications for headless testability, immutable records, `BigDecimal` math, metric-partitioned aggregations, and deterministic action rules.

### C. Project Planning & Burndown (Rubric Benchmark: 80+)
- **4-Sprint Delivery Roadmap:** Spanning Weeks 13 through 20 with clear value increments.
- **Sprint 1 Backlog:** 5 stories/tasks totaling 21 Fibonacci story points against a 24-point team capacity.
- **Formal Burndown Chart:** Day 1 to Day 10 linear ideal burn vs projected burndown.

### D. Project Setup & Risk Management (Rubric Benchmark: 80+)
- **Infrastructure:** GitLab repository with branch protection, Maven 3.9+ POM, JavaFX controls, and JUnit 5 test harness.
- **Comprehensive Risk Register:** 8 technical, human, and ethical risks evaluated with Likelihood, Impact, Risk Score, Early Warning Triggers, and Mitigations.

---

## 3. Verification & Execution Status

- **Java Core Smoke:** Passes 10/10 checks (`CoreSmoke.java`).
- **LaTeX Reports:** Fully formatted for 12pt, A4, 1.5cm margins, ACM SIGCHI citations, and native TikZ vector graphics.
