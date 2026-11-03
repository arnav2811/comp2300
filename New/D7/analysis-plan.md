# D7 analysis and evidence plan

The executable analyser is [`tools/analyse_evaluation.py`](tools/analyse_evaluation.py)
and its unit tests are in [`tools/test_analyse_evaluation.py`](tools/test_analyse_evaluation.py).
Running it against the checked-in template produces a `not_conducted` report.

## Data package

Retain a de-identified observation dataset, data dictionary, consent/withdrawal
record, session log, analysis script or reproducible calculation sheet, and a
hash/version of the evaluated application. Do not commit sensitive personal data.

## Analysis sequence

1. Check schema, denominators, missingness and duplicate rows.
2. Report participant count, setting, task attempts and incomplete observations.
3. Calculate task completion and critical-error summaries with explicit
   denominators.
4. Summarise time/interaction observations only when valid and comparable.
5. Code qualitative comments against the D2/D3 criteria and record an audit trail.
6. Triangulate qualitative and quantitative evidence without overstating causality.
7. State what the sample, prototype fidelity and method cannot establish.

The current analyser applies the proposed 3/4/5 participant rule (2/3, 3/4 or
4/5 successful observations per task). The team must approve or revise that rule
before recruitment; the analyser's output is not a substitute for that decision.

## Recommendation rule

Every recommendation must link to a reported observation, distinguish design
improvement from behavioural effect, and identify a verification step. No results
are entered until the study has actually occurred.
