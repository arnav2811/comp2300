# System logic and technical reasoning

**Status:** D1-derived logic implemented in `application/` review build; clean
Maven/UI/team verification remains pending.

## 1. Terminology

- **Activity:** a user-described lifestyle action or household record.
- **Estimate:** a calculated impact value only when the input, factor, unit and
  boundary are sufficiently defined.
- **Assumption:** a visible input or placeholder that has not been verified as an
  authoritative emission factor.
- **Necessary/protected choice:** an activity constrained by safety, curfew,
  shift work, heat, housing or another user-stated limitation. It may remain in
  the record, but it must not be treated as a discretionary failure.
- **Shared total:** communal activity shown without claiming that it is one
  resident's personal share.
- **Familiar comparison:** a contextual unit such as rupees, bus trips, fan-hours
  or water buckets. It must be labelled with its assumptions and must not imply
  carbon savings where it only describes volume or cost.

## 2. Evidence-to-behaviour rules

| Rule | Rationale/evidence | Status and verification |
|---|---|---|
| Show the input and the factor/boundary behind an estimate | D1 S1, P2 and E2 address distrust of opaque or foreign numbers | Proposed; verify through D2 walkthrough and later unit tests |
| Keep unknown factors distinct from zero | Prevents false certainty and follows D1's explicit placeholder limitation | Proposed; test missing, invalid and user-supplied-factor paths |
| Retain necessary activities without punitive advice | D1 L3, SC2/SC3 and E3 describe safety, shift and heat constraints | Proposed; walkthrough protected journeys and test recommendation eligibility |
| Show shared totals before any individual attribution | D1 L1/L2, RQ3 and E7 identify unsupported attribution as a risk | Open design decision; require team confirmation and consent model |
| Use familiar comparisons only with visible assumptions | D1 RQ1 and the tension between familiar units and raw CO2 weight | Proposed hypothesis; evaluate comprehension rather than assume benefit |
| Do not rely on speculative ML | Official D2 requirement (p. 543) and Week 6 minutes | Constraint; any segmentation must have a justified deterministic fallback |
| Do not claim behaviour change from a calculator | D1 literature review distinguishes planned/intended changes from observed effects | Permanent reporting rule; D7 requires actual evaluation data |

## 3. Calculation model proposed for D2

The generic dimensional relationship is:

```text
impact = activity quantity × applicable factor
```

This is not an accepted factor library. Before implementation, each factor must
record:

- quantity unit and denominator;
- CO2 versus CO2e status;
- source and publication/reference year;
- geographical applicability and activity boundary;
- whether it is verified, illustrative or user-supplied;
- uncertainty or excluded stages; and
- the valid comparison scope.

Scenario values in the D1 report, including `0.716 kg CO2/kWh`, `0.015 kg CO2
per passenger-km`, `Rs. 8/kWh`, a 7.5 L/min shower and device powers, are marked
by D1 as illustrative/unsourced or requiring verification. They must not be
presented as authoritative implementation data without a source audit.

## 4. Input and validation rules to settle

The D2 design must define, for every input:

1. category and unit;
2. required/optional state;
3. accepted range and precision;
4. missing/unknown behaviour;
5. whether the value is personal, household or descriptive;
6. whether the activity can be protected/necessary;
7. the output and its provenance; and
8. a recovery path after invalid input or cancelled entry.

No implementation should silently substitute a default factor, convert a missing
quantity into zero, allocate a shared quantity to a person, or rank a protected
choice as a failure.

## 5. Core user journeys to prototype

The low-fidelity D2 prototype should demonstrate, at minimum:

1. **Understand:** enter a small set of everyday activities and inspect an
   understandable result (E1).
2. **Audit:** open the assumptions/formula explanation and distinguish verified
   from illustrative values (E2).
3. **Act safely:** mark a constraint and receive a feasible, non-judgemental
   action (E3).
4. **Represent Indian/circular activity:** record or acknowledge food,
   packaging, waste, repair or composting without hiding excluded categories (E4).
5. **Log lightly/offline:** complete the intended short path and show the
   language/accessibility boundary honestly (E5).

Each journey must map to a persona, scenario, story, screen/card and later test.

## 6. Failure and uncertainty behaviour

- **Missing factor:** retain the activity and show “estimate unavailable” with
  the reason and the required evidence; do not show zero.
- **Invalid input:** identify the field, preserve the user's other entries and
  provide correction/cancel controls.
- **Protected activity:** retain its impact/record, suppress punitive ranking and
  offer advice only when the user has an actionable alternative.
- **Shared data:** show communal scope and its limitations; do not infer a
  person's share from an equal bill or household total.
- **Unverified assumption:** label it at the point of use and in any export or
  detailed view.
- **Evaluation absence:** report the design as untested; do not create synthetic
  participant outcomes.

## 7. Architecture status

The review build implements the architecture in `application/`: domain models
and `CarbonService`, an explicit `ActionPlanner`, `Workspace`/`LocalStore`, and
the JavaFX `FootprintLensApp`. Direct JavaFX compilation and the dependency-free
core smoke passed in the audit environment. The next engineering gate is a clean
Maven build, desktop run and team inspection on the supported platform.

## 8. Correctness checklist

Before a rule is treated as implemented and verified, the team must provide:

- an authoritative source file and method reference;
- a linked story/requirement;
- a test for normal, boundary, missing and invalid cases;
- the actual command and result;
- a review record; and
- a note of remaining limitations.
