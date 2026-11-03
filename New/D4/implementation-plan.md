# D4 implementation plan

## Proposed vertical slice

1. `ActivityInput` validates category, unit, quantity and personal/household scope.
2. `FactorReference` carries source, year, geography, boundary and status.
3. `EstimateService` returns numeric or unavailable status without coercing
   missing factors to zero.
4. JavaFX entry/result views expose validation and assumptions.
5. A protected-choice flag is stored but recommendation behaviour is deferred to
   a later slice unless D3 confirms it belongs in Increment 1.

## Acceptance test outline

| ID | Case | Expected result |
|---|---|---|
| D4-T01 | Valid supported quantity/factor | Numeric result with provenance |
| D4-T02 | Missing factor | Activity retained; estimate unavailable |
| D4-T03 | Negative quantity | Specific error; no save |
| D4-T04 | Blank quantity | Specific error; no save |
| D4-T05 | Household scope | Scope visible; no individual attribution |
| D4-T06 | Cancel after error | Earlier valid state preserved |

Replace this outline with actual tests and results after implementation.
