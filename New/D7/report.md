# D7 report — Evaluation and recommendations

**Status:** complete review-ready report framework; participant results pending.
**Product stage:** final evaluation-ready working item.

## Evaluation materials

The D3 protocol defines synthetic task inputs, recruitment from another COMP2300
group, a controlled quiet-room setting, participant information/consent, an
observer form and de-identified analysis. Tasks test activity entry, provenance,
protected choices, unsupported categories and shared totals. The materials must
be approved by the team before recruitment.

## Conduct and results boundary

No participant session or summative result is present in the supplied evidence.
The final report therefore reports the application tests as engineering evidence
and leaves the participant results table empty. It does not invent a sample,
completion rates, quotations, usability findings or carbon-reduction effect.

When the actual study is complete, add the de-identified dataset and run the
analysis sequence in [`analysis-plan.md`](analysis-plan.md), using the executable
[`tools/analyse_evaluation.py`](tools/analyse_evaluation.py). Every result must
include its denominator, missingness and method limitation.

## Evidence-led recommendations already supported by the design

1. Keep factor source, year, geography and boundary visible at the point of use.
2. Preserve an unavailable-estimate state for unsupported categories.
3. Keep protected activities visible without punitive advice.
4. Separate household totals from personal attribution.
5. Test language, keyboard/focus and comprehension with real participants before
   claiming accessibility or behaviour change.

These are design recommendations, not findings from a participant study. The
final D7 discussion must distinguish them from actual observations.

## Analysis quality gate

The analyser rejects duplicate participant/task rows, impossible comprehension
scores, negative/non-finite times and malformed headers. It reports an explicit
`not_conducted` state for the empty template. This protects the final report from
quietly converting missing observations into zeros, while still leaving the
interpretation of actual findings to the team.

## Final submission gate

The D7 package is drafted and linked to a functioning review build. Before
submission, the team must attach real recruitment/conduct records, de-identified
data, reproducible analysis, results, student reflections, official reviews,
rendered PDF inspection and the exact GenAI disclosure.
