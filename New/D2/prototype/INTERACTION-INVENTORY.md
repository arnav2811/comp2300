# Footprint Lens D2 interaction inventory

**Status:** static interaction contract and manual-walkthrough record. This is
not participant usability evidence. Browser rows are executed only when a team
member runs the prototype in a real browser and records browser/version/date.

| ID | Screen/control | Expected behaviour | Static check | Manual browser result |
|---|---|---|---|---|
| P0-C01 | Category select | Seven D1-relevant categories update unit/boundary and clear an existing quantity when its unit would change | Present; handler bound | Pending team walkthrough |
| P0-C02 | Scope select | Personal/household scope is retained and shown in P2/P3/P6 | Present; handler bound | Pending team walkthrough |
| P0-C03 | Protected checkbox | Protected state is retained and changes P4 eligibility | Present; handler bound | Pending team walkthrough |
| P0-C04 | Continue to quantity | Opens P1 and preserves P0 choices | Present; handler bound | Pending team walkthrough |
| P0-C05 | Illustrative example | Loads a clearly labelled 168 kWh household example | Present; handler bound | Pending team walkthrough |
| P0-C06 | Shared boundary preview | Opens P6 before quantity and does not infer a person share | Present; handler bound | Pending team walkthrough |
| P0-C07 | Reset prototype | Clears transient state and returns P0 without persistence | Present; handler bound | Pending team walkthrough |
| P1-C01 | Quantity input blank | Error appears and P1 remains visible | `aria-describedby`/validation present | Pending team walkthrough |
| P1-C02 | Quantity negative | Specific error; no result | Pure logic test | Pending team walkthrough |
| P1-C03 | Quantity huge | 0–1,000,000 boundary message | Pure logic test | Pending team walkthrough |
| P1-C04 | Unit output | Read-only unit text updates with category; category owns unit to prevent mismatch | Present; semantic output | Pending team walkthrough |
| P1-C05 | Back | Returns P0 without losing current form state | Present; handler bound | Pending team walkthrough |
| P1-C06 | Preview estimate | Valid input reaches universal P2 without saving | Present; handler bound | Pending team walkthrough |
| P2-C01 | Inspect assumptions | Opens P3 with metric/source/year/geography/boundary/formula | Present; handler bound | Pending team walkthrough |
| P2-C02 | Find realistic action | Opens P4 and renders explicit rule-based action | Present; handler bound | Pending team walkthrough |
| P2-C03 | Unavailable explanation | Visible only for unsupported categories; opens P5 | Present; handler bound | Pending team walkthrough |
| P2-C04 | Shared-total link | Opens P6 and reflects current scope | Present; handler bound | Pending team walkthrough |
| P3-C01 | Back to result | Returns P2 with quantity/state preserved | Present; handler bound | Pending team walkthrough |
| P3-C02 | Continue to action | Opens P4 | Present; handler bound | Pending team walkthrough |
| P4-C01 | Defer | Shows no-plan feedback and retains evidence boundary | Present; handler bound | Pending team walkthrough |
| P4-C02 | Not feasible | Shows non-judgemental dismissal feedback | Present; handler bound | Pending team walkthrough |
| P4-C03 | Choose plan | Shows current-plan feedback, never completed saving | Present; handler bound | Pending team walkthrough |
| P4-C04 | Protected action state | Disables action decisions and explains protection | Pure logic test; UI contract present | Pending team walkthrough |
| P5-C01 | Back to result | Returns P2 without losing category/quantity | Present; handler bound | Pending team walkthrough |
| P5-C02 | Record another activity | Returns P0 for a new route | Present; handler bound | Pending team walkthrough |
| P6-C01 | Shared scope display | Household shows recorded total; personal shows unavailable preview | Present; handler bound | Pending team walkthrough |
| P6-C02 | Return to start | Returns P0 without attribution | Present; handler bound | Pending team walkthrough |
| A11Y-C01 | Journey buttons | Keyboard-focusable, visible active state and `aria-current` | Static contract passed | Pending keyboard walkthrough |
| A11Y-C02 | State heading focus | New panel heading receives focus | Static handler present | Pending keyboard walkthrough |
| A11Y-C03 | Error association | Quantity uses `aria-describedby` for help/error | Static contract passed | Pending screen-reader walkthrough |
| A11Y-C04 | Narrow viewport | Journey remains discoverable; controls wrap | CSS contract present | Pending responsive walkthrough |

## Commands

```sh
node --test prototype-logic.test.mjs
python prototype-structure-check.py
node --check app.js
node --check prototype-logic.mjs
```

The automated checks verify the pure rules and structural contract. They do not
claim that a browser, screen reader or participant has completed the manual rows.
