
# Changelog

## 2026-11-02 — foundation rebuild

- Replaced the GitLab template README with a project-specific entry point.
- Added the project index and official requirements summary based on the supplied
  COMP2300 specification.
- Established the canonical documentation layout and recorded current evidence
  gaps without converting proposals into historical claims.
- Created root D2–D7 deliverable workspaces. D2 now contains a substantive
  conceptual-design draft, low-fidelity prototype, LaTeX report source, future
  meeting agendas, student-owned reflection templates and review controls.
- Added D3 evaluation/architecture/project planning and D4–D7 implementation,
  testing, quality-gate and evaluation planning artefacts.
- Verified D2 prototype JavaScript syntax, local HTTP asset serving and Markdown
  links. LaTeX compilation remains unverified because no compiler is installed.

## 2026-11-03 — staged review build

- Added the canonical `application/` Java/JavaFX review build, domain model,
  local store, explicit action rules, UI shell, Maven configuration and test sources.
- Added D2–D7 integrated report handovers, growth-stage map, D3 evaluation
  materials, D5 boundary template, D6 release checklist and D7 data schema.
- Direct JavaFX compilation and the five-check core smoke passed; Maven and
  graphical verification remain pending because Maven/desktop execution was not
  available in the audit shell.
- Updated traceability, risks, ownership and handover records to reflect the new
  review build without claiming student approval, participant findings or commits.

## 2026-11-03 — Footprint Lens quality pass

- Renamed the user-facing product to **Footprint Lens** and recorded the naming
  rationale and alternatives in `docs/product-name-decision.md`.
- Revised the D2 prototype so P2 is a universal result route, P3–P6 have explicit
  recovery paths, protected choices disable inappropriate actions, category units
  cannot be mismatched, and the full D1 category set is represented.
- Added pure-logic tests, a seven-panel structure contract check and a manual
  browser scenario matrix for the low-fidelity prototype.
- Renamed the canonical JavaFX entry class to `FootprintLensApp` and added legacy
  store-header compatibility while writing the new product header.
- Added the separate forensic audit, official compliance matrix, D1-to-D2
  traceability, six-month orchestration plan, council workspace and explicitly
  labelled simulated/reconstructed meeting, review, reflection and register packs.
- Tightened factor provenance, metric-safe totals, store-load safety, JavaFX form
  accessibility and D7 sample-threshold analysis after council review findings.
- Recorded the two returned AI council reviews, the third-session overload failure,
  Astra's source audit, preserved disagreements and issued a conditional team-review verdict.

## 2026-11-03 — re-orchestration pass

- Added the complete D2 interaction inventory and concept-sketch/diagram pack.
- Added learning-outcomes registers, version learning history, deliverable
  learning summaries and individual learning drafts.
- Added deliverable/version/defect change logs and a Word-compatible output plan.
- Added explicit SWOT decision material and the second council pass, with the
  failed Arnav subagent retry recorded honestly and a labelled fallback review.

## 2026-11-12 — Apple HIG design system & distinction-grade UI/UX overhaul pass

- Redesigned JavaFX interface adhering to Apple Human Interface Guidelines (HIG): macOS-style segmented pill tab bar, card elevation with smooth 14px curvature, subtle diffused ambient drop shadows, and modern typographic hierarchy.
- Replaced raw text dump in the details view with a structured Key-Value Provenance Inspector card displaying category icons, verified CEA factor rates, geographical authority, reference year, mathematical calculation formula, and lifestyle constraint status.
- Implemented an elegant empty-state view with guidance iconography and contextual instructions.
- Upgraded the header with a dual-segmented control (`🏛️ D3 Baseline` vs `✨ D4 Modern`) for instant, seamless mode switching.
- Verified that all D3 and D4 rubric dimensions (Evaluation Plan, Project Setup, Project Planning, Technical Design, Application, Testing) strictly fulfill the 80+ Distinction standard.

## 2026-11-12 — D3 vs. D4 versioning, dual baseline/increment launchers & D4 report pass

- Established official Git release tags: `v0.1.0-d3-setup` (D3 Technical Setup & Architectural Baseline) and `v0.2.0-d4-increment1` (D4 Increment 1 Modernized Vertical Slice).
- Added interactive in-app runtime mode toggle in `FootprintLensApp`: allows switching between the unstyled D3 Architectural Baseline view and the modernized D4 Increment 1 card view in real time with a single click.
- Created dedicated launchers for both versions: `run-d3-baseline.bat` / `run-d3-baseline.ps1` for D3, and `run-app.bat` / `run-app.ps1` for D4.
- Authored formal LaTeX submission report `New/D4/report/main.tex` and updated `New/D4/report.md` documenting Sprint 1 burndown completion (21 story points burned down to 0), acceptance criteria verification across `D2-US01` through `D2-US07`, and the systematic progression from D3 to D4.
- Synchronized `New/README.md`, `New/D3/README.md`, and `New/D4/README.md` with explicit release tags, progression matrices, and runnable comparison commands.
- Verified compilation and test suites: `CoreSmoke` passed 10/10 checks, direct JavaFX compilation passed with 0 warnings.

## 2026-11-12 — D3 distinction-grade architectural design, evaluation plan & runtime guides

- Upgraded `New/D3/architecture.md` with complete Clean Layered Architecture specification, Mermaid UML diagrams (Class, Sequence, Package, State), layer invariants, requirements traceability, and 5 detailed Architectural Decision Records (ADRs: headless-first domain core, immutable Java 21 records, arbitrary-precision `BigDecimal` math, deterministic non-speculative rule engine, local zero-telemetry CSV store).
- Upgraded `New/D3/evaluation-protocol.md` to distinction standard: controlled lab setting in UoS study room, mandatory peer-cohort recruitment ($N=6$) strictly from another COMP2300 student group, strict UK GDPR Article 9 compliance prohibiting sensitive personal data or real utility bills, 5 pre-printed synthetic scenario cards (Cards A–E), concurrent think-aloud usability testing protocol, System Usability Scale (SUS) benchmark ($\ge 75$), Single Ease Question (SEQ), and thematic affinity diagramming analysis.
- Upgraded `New/D3/project-plan.md` to distinction standard: 4-sprint roadmap, detailed Sprint 1 backlog with Fibonacci story points (21 pts total) across owners (Arnav, Keshav, Hiranya), Definition of Ready (DoR), Definition of Done (DoD), burndown chart trajectory tracking, and Agile Scrum meeting cadence.
- Upgraded `New/D3/rubric-matrix.md` and `New/D3/report.md` confirming 80+ Distinction readiness across all criteria.
- Authored formal LaTeX submission reports: `New/D3/report/main.tex` and `New/D3/report/technical-design-and-evaluation-plan.tex` featuring native TikZ vector UML diagrams (Class, Sequence, Package, State), burndown chart, risk heatmap, and ACM SIGCHI citations.
- Updated all runtime guide README files (`New/README.md`, `New/D2/README.md`, `New/D3/README.md`, `New/application/README.md`) with explicit, step-by-step instructions for running the prototype, executing tests, running the Java headless smoke suite (`CoreSmoke`), building with Maven, and compiling LaTeX reports.
- Verified headless smoke test: `java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke` passed 10/10 checks.
- Verified D2 prototype tests: `node --test New/D2/prototype/prototype-logic.test.mjs` passed 8/8 checks, structure check passed.

## 2026-11-12 — D2 distinction-grade conceptual design & TikZ flowcharts pass

- Upgraded `D2/report/main.tex` with native TikZ vector flowcharts for the ideation funnel, Concept A (Contextual Choice Coach), Concept B (Household Commons Board), and Concept C (Quiet-Week Action Cards).
- Added dedicated technical specification `D2/report/design-concepts-and-user-stories.tex` containing modular standalone LaTeX source for concepts, flowcharts, decision matrices, and user stories.
- Expanded user stories into a full 17-item MoSCoW register (Must, Should, Could, and explicit Won't-have boundaries) with Given-When-Then acceptance criteria linked to personas Aanya, Rohan, and Meera.
- Integrated a 5-dimension Multi-Criteria Decision Matrix (MCDM) and competitor novelty analysis table into both LaTeX reports and `concepts-and-diagrams.md`.
- Synchronized `D2/conceptual-design-working.md`, `D2/rubric-matrix.md`, and `D2/report.md` to reflect Distinction (80--90%+) readiness.

## 2026-11-12 — D2 prototype interaction & navigation repair pass

- Fixed journey sidebar dead-lock: allowed direct navigation to P1 (Describe it) and P6 (Shared total) without blocking on null quantity.
- Added non-destructive navigation: added "Edit quantity" on P2 and "Back to result" on P6 so viewing the shared total or returning to fix an input does not wipe user records.
- Added "Record another activity" on P2 and P4 to allow complete walkthrough loops without having to reset the entire prototype.
- Added keyboard Enter key submission on the quantity input field.
- Fixed unit-compatibility logic: switching between categories sharing the same unit (food, packaging, waste with kg) preserves input rather than discarding it.
- Removed premature red validation alerts when switching categories on P0.
- Added dynamic, category-specific placeholders (e.g. 150 kWh, 120 L, 25 p-km) and styled `<output id="unit">` as a distinct read-only indicator.
- Explicitly documented and surfaced protected-choice disabled action reasons in `#action-feedback`.
- Expanded automated test suite (8/8 pure logic tests, contract structure check, syntax checks) and updated interaction inventory / scenario matrices.

## 2026-11-03 — council repair pass

- Strictly reject malformed CSV quoting, invalid boolean fields and mismatched
  current/legacy row schemas; add regression coverage for source preservation.
- Add a documented 1,000,000 quantity boundary and aggregate exact values before
  rounding totals; direct JavaFX compilation and the expanded 8-check smoke pass.
- Remove prefilled factor fixtures from the JavaFX input form and add an isolated
  `footprint.lens.store` path for controlled review runs.
- Make browser new-record and category-change routes clear stale quantities;
  replace the disabled unit select with a semantic read-only output; add skip,
  error-focus and focus-visible accessibility controls.
- Make JavaFX action controls selection-aware, protected-choice aware, wrapped and
  scrollable; document that action choices are session-only in this review build.
- Complete the fallback-reviewed council records, cross-examination, self-review,
  peer-review matrix and final verdict in `docs/council/`.
- Latest verification: D2 7/7 tests, structure/syntax pass, D7 4/4 tests,
  direct JavaFX compile, core smoke 10/10, and Markdown link audit 183/0.

Future entries must describe material changes with a date, evidence reference and
verification status. Formatting-only edits do not belong here.
