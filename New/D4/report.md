# D4 report — Increment 1

**Status:** draft complete for team review.  
**Product stage:** first runnable vertical slice.

## Increment scope

D4 delivers the smallest useful Footprint Lens path: choose a category, enter a
non-negative quantity and scope, optionally attach a clearly labelled
user-supplied electricity factor, save locally, inspect the resulting estimate
and recover from invalid input. Packaging, travel, food, water, waste and repair
records can be retained descriptively, so unsupported factors do not block the
user's record.

## Implementation evidence

The vertical slice is implemented in [`../application/`](../application/). Core
rules are in `CarbonService`; the JavaFX entry flow is in `FootprintLensApp`; the
local file is managed by `LocalStore`. The dependency-free smoke runner checks
numeric estimation, unavailable-estimate behaviour, water comparison,
persistence and explicit action rules.

## Acceptance coverage

| Story | Implemented path | Evidence |
|---|---|---|
| D2-US01 | Category, quantity, unit and scope controls | `FootprintLensApp`, `CarbonServiceTest` |
| D2-US02 | Numeric result only with factor; unavailable otherwise | `Estimate`, `CarbonServiceTest` |
| D2-US03 | Inspect assumptions and provenance fields | Overview details and factor model |
| D2-US04 | Invalid number/range messages; no save on failure | `saveActivity` and domain validation |
| D2-US07 | Descriptive non-electricity records | `Category`, `Estimate`, smoke test |

## Release gate

The code is present and direct compilation passed. The final D4 claim still needs
the team's clean Maven run, desktop walkthrough, accessibility inspection,
actual issue/merge-review evidence and official report rendering.

## Product reflection

The narrow first increment is intentionally preferable to a broad but opaque
calculator: it makes the factor boundary and recovery behaviour inspectable
before adding more categories. The trade-off is that most categories remain
descriptive, so D5 must improve usefulness without inventing unsupported
precision.
