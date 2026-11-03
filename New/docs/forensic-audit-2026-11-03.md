# Forensic audit — 3 November 2026

## Purpose and scope

This audit establishes the state of the Footprint Lens workspace against the
official COMP2300 specification, the supplied D1 report, supplied Week 6/7
minutes, supplied peer-review record, repository history and observable build/test
artefacts. It is an engineering and assessment-control document, not a claim
that simulated records are acceptable formal evidence.

## Authority and evidence status

| Evidence class | Material | Status and use |
|---|---|---|
| Official | `docs/sources/official/COMP2300_PG_Updated_12_10_26.pdf` | Verified controlling requirements/rubrics; live deadline interface still requires checking |
| D1 | `docs/deliverables/D1/D1-group-report-supplied.pdf` and `D1-All-Three-Submission-Pack/` | Verified submitted baseline; source transcripts are not independently available |
| Genuine project records | `docs/meetings/2026-10-15-week06-minutes.pdf`, `2026-10-22-week07-minutes.pdf`, supplied peer-review PDF | Supplied records; Blackboard submission/attendance metadata not independently verified |
| Observable implementation | `application/`, `D2/prototype/` | Current AI-assisted review artefacts; execution checks recorded separately |
| Historical snapshots | `POC/` | Preserved engineering snapshots; not automatically canonical current source |
| Simulated/reconstructed | `simulated-and-reconstructed/` | Practice/planning material only; never formal evidence |

## D1 findings carried forward

The D1 report establishes semantic themes S1–S3, latent themes L1–L3, personas
P1–P6, scenarios SC1–SC4, RQ1–RQ3, the 62-word JTBD and E1–E14 MoSCoW
prioritisation. The central design constraints are:

1. familiar context must not conceal carbon-factor uncertainty (S1/RQ1);
2. circular and convenience activities must not be reduced to unsupported credits
   (S2);
3. logging must stay short, offline and language-conscious (S3/RQ2);
4. shared totals must not be treated as a person's emissions (L1/L2/RQ3); and
5. safety, shift work, heat and housing constraints must not be moralised (L3).

The transcripts are illustrative coursework material and are not a representative
population. Numerical values in D1 are treated as fixtures or assumptions unless
independently sourced.

## Repository and product findings

| Area | Finding | Confidence |
|---|---|---|
| Repository | Current branch is `main`; working tree contains substantial uncommitted documentation and implementation additions | High |
| D2 | Three concepts, stories, report source and prototype exist; authentic ideation evidence, final concept approval, novelty comparison and compiled PDF remain open | High |
| Prototype | Seven-panel low-fidelity route, pure logic tests and structure checks exist; browser walkthrough remains a human gate | High |
| Java/JavaFX | `application/` source exists and direct JavaFX compilation/core smoke passed; Maven was unavailable in the audit shell | High |
| D3 | Evaluation protocol/materials, architecture, project plan and report exist; recruitment and team-approved protocol are pending | High |
| D4–D6 | Review implementation and reports exist; clean Maven run, graphical acceptance, actual sprint evidence, hours and reviews are pending | High |
| D7 | Analysis utility, blank schema and report framework exist; no participant data or findings are present | High |
| Process | Week 8 onward minutes, Blackboard peer-review submissions, lecturer feedback, merge requests and actual contribution records are not available | High |
| Naming | Footprint Lens is a provisional working name; team approval is pending | High |

## Checks run on 3 November 2026

- D2 prototype logic: Node test runner, 7/7 passed.
- D2 structure contract: seven panels and required controls/handlers passed.
- D2 JavaScript/module syntax: passed.
- D2 local HTTP smoke: HTML, CSS, application and module assets served.
- JavaFX source compilation with local JavaFX 23.0.1 Windows jars: passed.
- Application core smoke: 10/10 checks passed after the repair pass; the earlier
  five-check result remains a historical baseline.
- D7 analysis tests: 4/4 passed; empty template returns `not_conducted`.
- Markdown link audit: 183 files, zero missing relative links at final audit.
- `git diff --check`: passed with normal line-ending warnings.

## Principal weaknesses before formal submission

1. Human evidence is the dominant gap: actual meetings, peer reviews, student
   reflections, hours, GitLab review history and participant evaluation are not
   replaceable by AI council material.
2. The official PDF schedule contains weekday/date presentation that must be
   checked against the live interface; the project calendar records this warning.
3. D2 novelty is not yet evidenced by a verified competitor comparison.
4. The formal D2 LaTeX source has not been compiled in this environment.
5. Maven/desktop execution has not been run in this environment.
6. Hindi/local-language support is a declared boundary, not completed localisation.

## Audit verdict

**Conditionally ready for team review and continued development.** The workspace
contains a coherent, traceable and testable review package. It is not formally
submission-ready until the human and environment-dependent gates above are closed.
