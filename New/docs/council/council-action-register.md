# Council finding → change → verification register

| ID | Finding/source | Action/change | Artefact | Verification/status |
|---|---|---|---|---|
| CA-01 | D2-US03 provenance incomplete; UX review | Added metric/year/geography/source/boundary rows and category metadata | `D2/prototype/app.js`, `prototype-logic.mjs` | 7 logic tests/static check passed; browser pending |
| CA-02 | Unsupported category branch; UX/engineering review | Universal P2 and explicit P5 explanation; unknown categories fail closed | `D2/prototype/` | Node tests/structure passed |
| CA-03 | Default factor policy; engineering review | Factor checkbox opt-in and denominator/metric domain validation | `application/FootprintLensApp.java`, `Factor.java` | Direct JavaFX compile/core smoke passed |
| CA-04 | Mixed metric totals; engineering review | Partition totals by metric and display no numeric estimate for empty metric map | `CarbonService.java`, `FootprintLensApp.java` | Source/core tests passed |
| CA-05 | Unreadable-store overwrite; engineering review | Disable save after load failure and preserve temporary load state | `FootprintLensApp.java`, `Workspace.java` | Source compile; corrupt-store desktop test pending |
| CA-06 | Small-window form; UX review | Fit-to-width JavaFX ScrollPane and explicit label relationships | `FootprintLensApp.java` | Source compile; scaling walkthrough pending |
| CA-07 | D7 threshold ambiguity; audit | Proposed 3/4/5 threshold rule and analyser test | `D3/evaluation-protocol.md`, `D7/tools/` | 4 Python tests passed; team approval pending |
| CA-08 | Missing complete control inventory | Added 32-control D2 interaction inventory | `D2/prototype/INTERACTION-INVENTORY.md` | Static contract; manual rows pending |
| CA-09 | Missing learning/change registers | Added learning-outcomes and docs/change-log | `learning-outcomes/`, `docs/change-log/` | Markdown audit passed: 183 files, 0 missing links |
| CA-10 | Missing Word-compatible editable output | Added RTF review pack and status note | `reports/word/` | File created; Word/LibreOffice visual opening pending |
| CA-11 | Engineering review: action controls appeared active for protected/no-selection records | Added selection-aware, wrapped action controls and disabled inapplicable comparisons/actions | `application/src/main/java/in/carbon/ui/FootprintLensApp.java` | Direct source compilation passed; desktop walkthrough pending |
| CA-12 | Engineering/UX review: factor fixture could be mistaken for user input | Factor fields now start blank and use prompts; opt-in copy explains provenance requirement | `application/src/main/java/in/carbon/ui/FootprintLensApp.java`, `application/USER-GUIDE.md` | Direct source compilation and 10-check smoke passed; desktop walkthrough pending |
| CA-13 | UX review: browser unit control looked disabled/inert | Replaced disabled select with read-only semantic output | `D2/prototype/index.html`, `app.js`, `INTERACTION-INVENTORY.md` | Node 7/7 and structure checks passed; browser walkthrough pending |
| CA-14 | Engineering review: stale quantity when starting another browser record | New-record route clears quantity and validation state | `D2/prototype/app.js` | Node 7/7 and structure checks passed; browser walkthrough pending |
| CA-15 | Engineering/UX review: category change could reinterpret an existing quantity | Category change clears quantity and requires re-entry in the new unit | `D2/prototype/app.js`, `FootprintLensApp.java` | Node/structure checks and direct compile passed; walkthrough pending |
| CA-16 | UX review: unavailable panel implied browser persistence | Reworded P5 as a descriptive preview and renamed recovery control | `D2/prototype/index.html`, `app.js` | Node 7/7 and structure checks passed; browser walkthrough pending |
