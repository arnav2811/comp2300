# Footprint Lens D2 low-fidelity prototype

This is an **AI-assisted working prototype** for D2 Concept A — Contextual
Choice Coach. It is a design artefact, not the Java/JavaFX application and not
evidence of participant testing.

## Run it

Because the prototype uses an ES module, serve this directory locally rather than
opening the file directly:

```sh
python -m http.server 8080 --directory D2/prototype
```

Then open `http://localhost:8080`.

For the automated checks from this directory:

```sh
node --test prototype-logic.test.mjs
python prototype-structure-check.py
node --check app.js
```

## Deliberate boundaries

- All factors and tariff values are clearly labelled **illustrative**.
- Packaging and repair/reuse demonstrate an “estimate unavailable” state rather
  than inventing a numeric factor.
- Protected choices remain visible but do not produce punitive advice.
- Household totals are not allocated to a person.
- Data is held only in browser memory; there is no persistence or network call.
- This prototype does not prove usability, behaviour change or carbon reduction.

## Interaction quality improvements

- P2 is now the common result state for numeric and unavailable estimates.
- P3 assumptions is reachable from every result and shows activity, quantity,
  scope, factor, formula, source status and boundary.
- P4 protects necessary choices by disabling action decisions and explaining why.
- P5 is an explicit unavailable-estimate explanation with a recovery route.
- P6 shows the current household context without dividing it into a person share.
- The category list includes water, travel, food, packaging, waste and repair/reuse;
  each category sets its own unit to prevent incompatible calculations.
- The journey controls are keyboard-focusable, support recovery and announce
  state changes to assistive technology.
- The illustrative example button makes the principal walkthrough reproducible.

## D2 coverage

| Prototype state | Purpose | Story coverage |
|---|---|---|
| P0 | Entry, category and scope | D2-US01, D2-US05, D2-US07 |
| P1 | Quantity and validation | D2-US01, D2-US04, D2-US08 |
| P2 | Result and familiar context | D2-US02 |
| P3 | Assumptions and provenance | D2-US03 |
| P4 | Constraint-aware action | D2-US05, D2-US06 |
| P5 | Estimate-unavailable category state | D2-US07 |
| P6 | Shared-total boundary | E7 boundary / D2-US01 |

The team must review the interaction, wording, accessibility and concept choice
before treating this as D2 submission evidence.
