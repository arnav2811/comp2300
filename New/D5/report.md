# D5 report — Increment 2

**Status:** draft complete for team review.  
**Product stage:** integrated awareness and action increment.

## Increment scope

D5 extends D4 with explicit Footprint Lens rule-based actions, protected-choice handling,
personal/household total separation, electricity comparison and a non-carbon
water-volume comparison. It preserves the deliberate product boundary: a
comparison is an illustration under the same factor, and choosing an action is
not evidence that a reduction occurred.

## Boundary and partition coverage

The testing plan covers zero, negative, blank, non-numeric and large quantities;
missing and user-supplied factors; personal versus household scope; protected
activities; and unsupported descriptive categories. `CarbonServiceTest` and
`CoreSmoke` cover the implemented core partitions. The full D5 record must add
actual Maven results, scenario runs and a non-author review.

## Definition of Done

A D5 story is release-ready when it has stable acceptance criteria, an
implementation, automated or justified manual evidence, traceability, review,
documentation and a clean regression result. Actual task-hours and sprint
records belong to the student evidence pack and must be entered from real work.

## Review gate

The product increment is drafted and implemented. The D5 submission remains
pending the team's real boundary/scenario evidence, hours summary, retrospective,
PDF inspection and peer review.

## Product reflection

D5 improves the product by making “action” a reversible decision state rather
than a promise of savings. Separating personal and household totals also reduces
the risk of moralising shared consumption. The remaining weakness is that action
appropriateness is rule-based and unvalidated; D7 must test comprehension and
record when participants need clarification.
