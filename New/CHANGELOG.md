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

Future entries must describe material changes with a date, evidence reference and
verification status. Formatting-only edits do not belong here.

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
- Added five pure-logic tests, a seven-panel structure contract check and a manual
  browser scenario matrix for the low-fidelity prototype.
- Renamed the canonical JavaFX entry class to `FootprintLensApp` and added legacy
  store-header compatibility while writing the new product header.
