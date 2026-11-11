# Footprint Lens prototype test scenarios

**Purpose:** reproducible D2 interaction checks. These checks validate the
prototype as a design artefact; they do not establish participant usability.

| ID | Path | Expected result | Check |
|---|---|---|---|
| P0-01 | Serve prototype → Entry | Footprint Lens name, India-centric subtitle and illustrative boundary are visible | Pending browser walkthrough |
| P1-01 | Entry → Continue with blank quantity | Error is announced; no result is shown; other inputs remain | Pure validation test passes |
| P1-02 | Enter negative quantity | Specific error; no state is saved | Pure validation test passes |
| P1-03 | Load illustrative example → Preview | 168 kWh household record reaches P2 | Manual browser walkthrough |
| P1-04 | Quantity input → Enter key | Submits quantity and navigates to P2 identically to preview button | Automated / manual walkthrough |
| P2-01 | P2 → Inspect assumptions | P3 shows activity, quantity, scope, factor, formula, source and boundary | Manual browser walkthrough |
| P2-02 | P2 → shared total | P6 shows current scope and does not infer a person share | Manual browser walkthrough |
| P2-03 | P2 → Edit quantity | Returns to P1 with quantity preserved for editing | Automated / manual walkthrough |
| P2-04 | P2 → Record another activity | Clears state and returns to P0 for a fresh record | Automated / manual walkthrough |
| P3-01 | P3 → Back to result | P2 is restored without losing the quantity | Manual browser walkthrough |
| P4-01 | P2 → action → Choose/Defer/Not feasible | Feedback changes and preserves the non-reduction boundary | Pure action tests pass; browser walkthrough pending |
| P4-02 | Protected activity → P4 | Protected message is shown, action controls are disabled, and status feedback explains constraint | Manual browser walkthrough |
| P4-03 | P4 → Record another activity | Clears state and returns to P0 for a fresh record | Automated / manual walkthrough |
| P5-01 | Select packaging → quantity → result → unavailable | P2 remains a result state; P5 explains why no estimate exists and returns safely | Manual browser walkthrough |
| P6-01 | Entry → shared boundary | P6 is reachable before quantity and shows no personal attribution | Manual browser walkthrough |
| P6-02 | P6 → Back to result | P2 is restored safely when navigated from result without data destruction | Automated / manual walkthrough |
| JRN-01 | Journey menu → P1 (quantity) | Navigates directly to P1 even when quantity is null (allows user to describe activity) | Automated / manual walkthrough |
| JRN-02 | Journey menu → P6 (shared total) | Reachable directly before quantity entry | Automated / manual walkthrough |
| A11Y-01 | Keyboard through controls | Focus is visible; state headings receive focus; announcements are exposed | Team accessibility review |
| A11Y-02 | Narrow viewport | Journey remains usable; controls wrap; no colour-only status is required | Team responsive review |

## Automated checks

```sh
node --test prototype-logic.test.mjs
python prototype-structure-check.py
node --check app.js
node --check prototype-logic.mjs
```

The remaining browser walkthrough rows must be run by the team in a real browser
and recorded with the browser/version and date.

The pure logic suite currently covers seven checks: category metadata,
blank/invalid/negative/zero/large quantity handling, illustrative arithmetic,
unavailable categories, protected choices, unknown categories and invalid
calculation inputs.
