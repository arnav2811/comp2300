# Defect and fix register

| ID | Defect | Severity | Root cause | Fix/status | Verification |
|---|---|---|---|---|---|
| DF-01 | Unsupported category route did not use universal result | High | Conditional transition | P2 universal; P5 explicit | 7 logic tests; browser pending |
| DF-02 | Provenance lacked structured year/geography | High | Metadata in one source string | P3 rows and category metadata | Static/structure checks |
| DF-03 | Default factor could look user-supplied | Critical | UI preselection | Factor opt-in | Java source compile; UI pending |
| DF-04 | CO2/CO2E totals could combine | Critical | Scope-only totals map | `totalsByMetric` and UI partition | Core tests/smoke |
| DF-05 | Unreadable store could be overwritten after startup | Critical | Load failure left editable workspace | Save disabled after load failure | Code inspection; corrupt-store UI pending |
| DF-06 | JavaFX form could clip at small window | High | No scroll container | Fit-to-width ScrollPane | Source compile; desktop pending |
| DF-07 | Invalid prototype category fell back to electricity | High | Default lookup fallback | Fail-closed category lookup | Node test |
| DF-08 | D7 four-participant rule undefined | Medium | Open threshold | Proposed 3/4/5 rule | Python tests |
| DF-09 | Official PDF schedule weekday/date mismatch | High | Source calendar inconsistency | Recorded warning, no silent correction | Official PDF text audit |
| DF-10 | Third council session overloaded | Medium | Tool capability failure | Honest fallback/source audit | Recorded in council verdict |
