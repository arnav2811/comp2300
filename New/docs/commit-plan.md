# Commit plan

This is a forward plan. “Planned” entries are not claims that the work or commit
exists. Actual SHAs must be added only after the contributor commits and the
repository metadata is inspected.

| Task/requirement | Owner | Proposed branch | Files/output | Proposed commit(s) | Verification | Reviewer | Status |
|---|---|---|---|---|---|---|---|
| Foundation documentation and source register | Arnav | `docs/project-foundation` | README, `logic.md`, `strategy.md`, `docs/` foundation | `docs(project): establish evidence-led project foundation` | Link/path and Markdown checks | Keshav | In progress in current working tree; SHA pending |
| D2 concept alternatives | Whole team/Hiranya lead | `feature/D2-concepts` | `D2/` concept record/assets | `docs(D2): record three differentiated concepts` | D2 concept rubric and novelty/feasibility review | Arnav/Keshav | Planned |
| D2 stories and traceability | Keshav | `docs/D2-story-map` | `D2/`, story register, acceptance criteria, traceability | `docs(D2): define testable story acceptance criteria` | Every E1–E5 story has route and later check | Hiranya | Planned |
| D2 prototype | Hiranya | `feature/D2-low-fi-prototype` | `D2/prototype/` editable source and rendered evidence | `feat(D2): add low-fidelity concept walkthrough` | Reviewer can follow all mapped paths | Arnav/Keshav | In progress; student review required |
| Complete Java/JavaFX source restoration | Keshav | `chore/restore-application-source` | `application/` source/build/tests | `chore(build): restore reproducible JavaFX checkout` | Direct JavaFX compile/core smoke passed; clean Maven run pending | Hiranya/Arnav | Draft source present; review/build gate pending |
| D3 evaluation/architecture | Arnav/Keshav | `docs/D3-evaluation-architecture` | protocol, UML, plan, risks | `docs(D3): add evaluation and technical design` | Official D3 criteria and clean-build check | Hiranya | Draft complete; team review pending |
| D4–D6 increments | Rotating | deliverable-specific branch | source/tests/report evidence | deliverable-scoped `feat`, `test`, `docs` commits | Acceptance, regression and package checks | Non-author | Review build/report present; evidence gates pending |
| D7 evaluation | Whole team | `docs/D7-evaluation-report` | actual materials/data/analysis/report | `docs(D7): report summative evaluation evidence` | Data provenance and rubric audit | Non-author | Report/materials drafted; actual data pending |

## Execution sequence

Requirement → design/logic update → implementation → tests → documentation →
diff review → commit → merge-request review → verification → project-index update.

If the sequence changes, record why in the relevant MR and update this table.
