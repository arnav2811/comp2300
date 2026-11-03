# D5 boundary, partition and scenario plan

## Partitions to define after the source model is agreed

| Input/rule | Valid partition | Invalid/boundary partitions | Evidence |
|---|---|---|---|
| Quantity | positive supported value | zero, negative, blank, very large, non-numeric | Actual test result |
| Factor | compatible verified/user-labelled value | missing, zero where prohibited, incompatible unit, unsupported geography | Actual test result |
| Scope | personal or household | missing/ambiguous scope | Actual test result |
| Protected flag | protected or discretionary | transition from protected to discretionary | Actual test result |
| Comparison | compatible baseline/alternative | necessary activity, missing factor, negative alternative | Actual test result |

## Scenario tests

Run the final implementation against at least the selected D1 scenarios, with
SC2/SC3 safety and shift constraints and SC1/SC4 shared scope explicitly tested.
Record actual inputs, expected result, actual result, environment and defect
disposition. This file is a plan, not test evidence.
