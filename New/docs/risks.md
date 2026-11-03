# Risk register

Scores use **L**ikelihood and **I**mpact on a 1–5 scale. The score is a
planning judgement, not a measured probability.

| ID | Risk/cause | L | I | Mitigation/contingency | Owner | Trigger/status |
|---|---|---:|---:|---|---|---|
| R1 | Clean Maven/desktop verification of the new Java/JavaFX review build is pending | 5 | 4 | Run `mvn clean verify` and desktop walkthrough from `application/` | Keshav | Review/build gate |
| R2 | D2 concept/prototype work may be missing or unsupported | 4 | 5 | Rebuild from D1 and the official D2 brief; map every Must story to an actual card/screen | Whole team | Immediate blocker |
| R3 | Unsourced or geographically inappropriate emission factors create false precision | 4 | 5 | Maintain factor provenance; label assumptions; use estimate-unavailable state | Arnav/Keshav | Open from D1 placeholders |
| R4 | Shared-household data is mistaken for individual attribution | 4 | 5 | Shared-total default; explicit scope and consent decision; no forced shares | Whole team | D2 decision gate |
| R5 | Safety/shift/heat constraints are moralised by recommendations | 3 | 5 | Protected-choice rule and acceptance tests; review scenarios SC2/SC3 | Hiranya/Keshav | D2 design gate |
| R6 | Three concepts are superficial variants or not novel | 3 | 4 | Compare interaction model, coverage and constraints against cited alternatives | Whole team | D2 concept gate |
| R7 | Scope expands into ML, hardware or automatic data collection | 4 | 4 | MoSCoW gate; no speculative ML dependency; defer unsupported integration | Whole team | Week 6 ML idea remains investigation only |
| R8 | Parallel files drift in terminology, citations or IDs | 4 | 4 | One canonical source per deliverable; stable IDs; final cross-check | Arnav | Confirmed in Week 7 minutes |
| R9 | Missing or late team contribution | 3 | 4 | Shared ownership, small reviewable tasks, early escalation and contingency | Whole team | Review weekly |
| R10 | AI-assisted text/code is misunderstood or incompletely disclosed | 3 | 5 | Record tool/version/purpose; student verification; keep AI review separate from peer review | Arnav/Hiranya | Open until disclosure confirmed |
| R11 | Evaluation recruits unsuitable participants or collects sensitive data | 2 | 5 | D3 protocol gate; recruit another COMP2300 group; screen tasks/questions | Arnav | Future dependency |
| R12 | Submission formatting/citation failure | 3 | 4 | ACM SIGCHI audit; 12 pt/A4/1.5 cm render check; open final PDF | Arnav | Every deliverable gate |

## Escalation rule

Any risk rated 5 impact that blocks an official acceptance criterion must appear
in the next meeting agenda and the deliverable status must remain blocked until
evidence closes it. A mitigation plan is not a completed mitigation.
