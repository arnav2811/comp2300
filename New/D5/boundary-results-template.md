# D5 boundary and scenario results — evidence template

The cases below are defined for the implemented review build. The result column
must be filled from an actual command/session; planned expected behaviour is not
a pass.

| Case | Expected behaviour | Actual result | Version/command | Reviewer/date |
|---|---|---|---|---|
| Blank quantity | Specific error; no save | Pending | [ ] | [ ] |
| Negative quantity | Specific error; no save | Pending | [ ] | [ ] |
| Zero quantity | Accepted as known zero | Pending | [ ] | [ ] |
| Missing electricity factor | Record retained; estimate unavailable | Pending | [ ] | [ ] |
| Protected activity | No discretionary comparison/action | Pending | [ ] | [ ] |
| Household record | Total remains household-scoped | Pending | [ ] | [ ] |
| Unsupported category | Descriptive record; no fabricated factor | Pending | [ ] | [ ] |
| Quoted note persistence | CSV round-trips safely | Core smoke passed | `CoreSmoke` | 2 Nov 2026 audit |
