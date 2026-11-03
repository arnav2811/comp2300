# Requirements traceability

**Status:** draft-complete traceability register; clean Maven/UI/team evidence
remains pending.

## Official-to-project traceability

| ID | Requirement/evidence | Design response | Implementation | Verification | Status |
|---|---|---|---|---|---|
| O1 | Topic 1 must address India-relevant carbon-footprint awareness/reduction | India-relevant categories, language and contexts from D1 | `application/` | D2 review; later story tests | Draft complete / review pending |
| O2 | Java and JavaFX are mandatory | Desktop Java/JavaFX architecture | `application/pom.xml`, `application/src/main/java/` | Clean Maven build on a clean clone | Draft complete / build pending |
| O3 | UoS GitLab must store source | Repository workflow and traceable branches | Remote exists; source completeness unverified | Clone/build check | In progress |
| O4 | D2 requires three distinct concepts | D2 concept comparison record | `D2/` | Independent review against D2 rubric | Draft complete / review pending |
| O5 | D2 requires small stories, MoSCoW and acceptance criteria | E1–E5 split into story IDs | `D2/` | Story walkthrough and criterion audit | Draft complete / review pending |
| O6 | D2 prototype must be low-fidelity, feasible, accessible and non-speculative | Cards/sketches for the five Must journeys | `D2/prototype/` | Walkthrough with reviewer | Draft complete / review pending |
| O7 | Weekly minutes must record actions/owners | Official-format meeting records | `docs/meetings/` | Team accuracy check before submission | Process requirement |
| O8 | Weekly peer reviews contribute to PRISM | Review rotation and evidence retention | `docs/reviews/` plus Blackboard | Actual review submission/status | Process requirement |
| O9 | Reports need management, tools and product/process reflection | Deliverable report checklist | `D2/`–`D7/` reports | Pre-submission checklist | Draft complete / records pending |
| O10 | PDF >=12 pt, A4, >=1.5 cm margins; ACM SIGCHI citations | Formatting and citation gate | Report-specific source | Rendered PDF inspection | Planned |
| O11 | GenAI must be disclosed with tool/version/purpose | Per-deliverable disclosure register | `genai.txt` at submission location | Student completeness check | In progress |
| O12 | D3 evaluation must be user-centred, controlled and non-sensitive | Recruit another COMP2300 group; protocol | D3 artefact | Protocol review | Future dependency |
| O13 | D4–D6 must compile/run and show testing | CI/build/test evidence and story acceptance checks | `application/` | Clean checkout build and tests | Draft complete / build pending |
| O14 | D7 must use actual summative evaluation data | No synthetic findings; data provenance register | `docs/evidence/` | Participant/session audit | Future dependency |

## D1 baseline to D2 traceability

| Baseline ID | D1 evidence | D2 design implication | Acceptance evidence needed |
|---|---|---|---|
| S1 | Abstract kg CO2e is hard to interpret; users request local/familiar units | Compare kg with clearly labelled INR, water or travel references | User can explain at least one result without hidden assumptions |
| S2 | Convenience packaging, food waste, dairy/local food, repair and reuse appear in the analysis | Choose a justified category subset and state exclusions | Every represented category has a unit/boundary and an explicit limitation |
| S3 | Long forms, heavy typing and data use discourage continued logging | Provide a short path and a simple/detailed distinction | Walkthrough records steps, time target and recovery path |
| L1/L2 | Shared bills and household responsibility are difficult to attribute | Shared total must not imply personal responsibility | Prototype shows communal scope and consent/attribution boundary |
| L3 | Safety, shift work and heat constrain choices | Necessary choices are protected from moralising advice | Walkthrough shows a protected path and feasible alternative logic |
| RQ1 | Familiar comparisons versus raw carbon weight remain unsettled | Make comprehension an evaluation question, not a design fact | D3/D7 protocol measures comprehension accurately |
| RQ2 | Presets may reduce effort but could reduce completeness | Compare short entry with detail/coverage disclosure | Protocol records effort and missing information |
| RQ3 | Shared view should show communal totals without unsupported allocation | Resolve the D1 noted attribution tension in D2 | Explicit team decision and consent model |

## Must-epic coverage gate

| Epic | D1 meaning | D2 story family | Prototype route | Later test/evaluation |
|---|---|---|---|---|
| E1 | Understand impact in familiar terms | Enter activity and inspect contextual result | Must be mapped to a card/screen | Comprehension and correctness |
| E2 | See/edit assumptions behind estimates | Open factor/formula explanation | Must be mapped to a card/screen | Missing/invalid/factor provenance tests |
| E3 | Find actions that fit constraints | Mark necessary choice and receive feasible action | Must be mapped to a card/screen | Protected-choice acceptance test |
| E4 | Include Indian/circular categories | Select category and show included/excluded scope | Must be mapped to a card/screen | Category/unit/boundary tests |
| E5 | Log briefly/offline/in language | Short entry path with language/accessibility boundary | Must be mapped to a card/screen | Effort, completeness and accessibility checks |

Until each row has a real prototype route and acceptance criterion, no claim of
complete Must-epic coverage should appear in a report.
