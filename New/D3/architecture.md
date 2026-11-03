# D3 technical architecture — proposed design

**Status:** review-build architecture; based on Topic 1, D1 and the D2 Contextual
Choice Coach. Clean Maven/UI/team verification remains pending.

## Design goals

- Separate JavaFX presentation from domain rules and persistence.
- Keep calculation/provenance rules testable without a graphical environment.
- Represent unknown estimates explicitly.
- Preserve personal versus household scope.
- Make protected-choice behaviour a domain rule rather than a visual convention.
- Avoid speculative ML and unnecessary external services.

## Proposed components

```text
JavaFX views/controllers
        │ user commands and view models
Application services (record, estimate, action, export)
        │ validated domain objects
Domain model (Activity, Scope, Factor, Estimate, ActionPlan)
        │ repository interface
Local persistence (versioned file or agreed store)
```

### Responsibilities

| Component | Responsibility | Must not do |
|---|---|---|
| View/controller | Collect input, display state and accessible errors | Calculate factors or silently repair invalid data |
| Application service | Coordinate use cases and validation | Own JavaFX layout details |
| Domain model | Enforce units, scope, factor status and protected-choice rules | Read files or display UI |
| Calculation service | Apply `quantity × factor` only when factor provenance permits | Treat missing factor as zero |
| Persistence adapter | Load/save versioned records and preserve failure safety | Expose raw storage format to views |
| Evaluation/export adapter | Produce de-identified evidence or user-approved output | Add participant identity or unsupported estimates |

## Proposed data model

```text
Activity
  id: stable identifier
  category: transport | energy | food | water | waste | circular
  quantity + unit
  scope: PERSONAL | HOUSEHOLD
  protectedChoice: boolean
  factorReference: optional FactorReference

FactorReference
  value + denominatorUnit
  metric: CO2 | CO2E
  source, referenceYear, geography, boundary
  status: VERIFIED | ILLUSTRATIVE | USER_SUPPLIED | UNAVAILABLE

Estimate
  status: NUMERIC | UNAVAILABLE
  value + metric + explanation

ActionPlan
  status: PROPOSED | CHOSEN | DEFERRED | NOT_FEASIBLE
  reason, constraintRespect, createdFromActivityId
```

## Core use-case pseudocode

```text
record(activityInput):
    validate category, quantity, unit and scope
    retain protectedChoice and uncertainty metadata
    save only after complete validation

estimate(activity):
    if factor is absent or unavailable: return Estimate.UNAVAILABLE(reason)
    convert only with an explicit compatible unit
    return quantity × factor, with provenance attached

suggest(activity):
    if activity.protectedChoice: suppress punitive advice
    choose only a rule whose preconditions are visible
    return one feasible proposal or no eligible action
```

## Architecture acceptance gate

The proposal becomes final D3 evidence after the team confirms package
names/interfaces, inspects the architecture diagram, runs a clean build and
reviews unit tests against normal, missing-factor, invalid-input, protected and
household cases. The current source and core smoke runner are already present in
`application/`.
