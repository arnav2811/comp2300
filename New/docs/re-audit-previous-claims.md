# Re-audit of previous-run claims — 3 November 2026

| Previous claim | Re-audit method | Result |
|---|---|---|
| 19 simulated/reconstructed meeting records | Counted `simulated-and-reconstructed/meeting-minutes/*.md` and read index/headers | 20 Markdown records now present; all carry fictional/simulated notices; no formal evidence claim |
| Peer-review practice material | Read `peer-review-practice/weekly-practice-pack.md` and README | Present; 12 substantive rehearsal sections; not Blackboard evidence |
| 18 reflection sections | Read three student files and README | Present as six sections per student; fictional drafts/placeholders, not final reflections |
| Registers | Counted/read decision, disagreement, action, evidence, workload and README | Present; simulated/proposed statuses explicit |
| D2 prototype improvements | Read HTML/JS/module/CSS and ran Node tests/structure check | Confirmed: 7/7 logic tests, structure passed; browser/accessibility still pending |
| JavaFX source compilation | Recompiled source with local JavaFX Windows jars | Passed direct compilation; Maven and graphical launch unavailable |
| Five core smoke checks | Recompiled test classes and ran `CoreSmoke` | Passed 5 checks |
| Four D7 analysis tests | Ran Python unittest suite | Passed 4 tests after threshold-rule addition |
| Markdown link audit | Re-ran relative-link scan | 173 Markdown files, 0 missing links at current audit point |
| Application/accessibility improvements | Inspected source and recompiled | Factor opt-in, metric-safe totals, load-failure save disable, ScrollPane and label associations present; UI walkthrough pending |

## Corrections to prior wording

- “Complete” means the review artefact exists and relevant local checks pass; it
  does not mean formal submission completion.
- The JavaFX source compiles directly, but Maven/JUnit clean execution and desktop
  launch remain unverified because Maven/browser/GUI tools are unavailable here.
- The D2 prototype logic and structure checks pass; manual browser, keyboard,
  screen-reader and participant usability checks remain pending.
- Simulated meetings, peer reviews and reflections remain rehearsal material only.
