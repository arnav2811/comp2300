# Council cross-examination — 3 November 2026

**Status:** FICTIONAL RECONSTRUCTION — AI-created council record based on the
three written review passes. It is not a student meeting, peer assessment or
formal approval.

The Arnav pass is explicitly a fallback research/editorial review because the
requested subagent session failed upstream. Keshav and Hiranya reviews contain
their independent file-specific findings. No reviewer saw the others' conclusions
before the first-pass records were prepared.

## Issue 1 — One factor model or deliberately separate stage artefacts?

**Keshav:** Browser and JavaFX have different factor journeys. A user must not
see an illustrative fixture presented as their own factor, and the same category
should have the same unit/status semantics wherever the product claims continuity.

**Hiranya:** The browser is a low-fidelity conceptual artefact and the JavaFX
application is a later technical increment, so some difference is acceptable.
The problem is discoverability: users and assessors need the relationship stated,
and disabled/prefilled controls must not look like failures.

**Arnav fallback:** Editorially, the distinction can be retained only if the
documents call it out as progression. A polished prototype cannot silently
become evidence-backed software. The formal report should show inherited and
changed behaviour.

**Evidence examined:** `D2/prototype/prototype-logic.mjs`,
`application/FootprintLensApp.java`, `application/USER-GUIDE.md`, D4/D5 reports.

**Resolution:** Accepted a shared evidence rule, not forced feature parity.
JavaFX factor fields now start blank and are explicitly user-supplied; the D2
illustrative fixture remains labelled. The handover must describe this as a
stage transition. **Owner:** team technical/editorial review. **Status:** repair
implemented; Maven/UI confirmation pending.

## Issue 2 — Implement comparison and plans, or narrow the concept claim?

**Hiranya:** A generic action message is weaker than the stated Contextual Choice
Coach. “Choose this plan” should either save a durable plan or be renamed as a
preview. Protected users need a neutral outcome rather than a dead-end set of
disabled buttons.

**Keshav:** Durable plan persistence adds schema and restart tests. It is not
just a label change. For the current increment, session-only action state is a
defensible scope if the UI and guide say so.

**Arnav fallback:** The D2 selection can remain coherent if the result/assumption
checkpoint is treated as the core novelty hypothesis and comparison is explicitly
proposed rather than claimed as a validated behaviour-change feature.

**Evidence examined:** D2 concept matrix, `D2/prototype/index.html/app.js`,
`Workspace.java`, `USER-GUIDE.md`.

**Resolution:** Deferred durable action persistence to a future increment;
clarified session-only behaviour and preview language, cleared stale browser
state on a new record, and made JavaFX action controls selection-aware. A later
team decision is required before claiming full comparison capability. **Status:**
implemented wording/control repairs; feature completion deferred.

## Issue 3 — How much architecture is justified now?

**Keshav:** D3 proposes repository and application-service boundaries that the
442-line JavaFX shell does not implement. Either add those boundaries or mark
the architecture as target design rather than delivered structure.

**Arnav fallback:** A distinction-quality report should explain the adaptation;
adding abstractions without tests risks cosmetic architecture. The current domain
separation is more defensible than claiming a complete layered design.

**Hiranya:** The practical priority is a testable, navigable user journey. A
repository interface matters if it enables isolated data-path and UI acceptance
tests, not as a diagram-only requirement.

**Resolution:** Deferred broad refactoring. Updated evidence language to call the
current shell a small review-build simplification and added an isolated store-path
property for future walkthroughs. **Status:** accepted deferral, rationale
recorded; team may expand the boundary if D4–D6 scope permits.

## Issue 4 — Is the project India-centric enough?

**Arnav fallback:** India-centred context, household constraints and INR wording
are useful, but an illustrative factor cannot support a verified national claim.

**Hiranya:** India-specific relevance must include realistic transport, safety,
affordability and shared-home contexts; a generic protected checkbox is a start,
not validation.

**Keshav:** More categories and regional factors increase data/provenance and test
burden. Unsupported values should stay unavailable rather than become fabricated
coverage.

**Resolution:** Keep the evidence-conscious narrow scope. Add no unsupported
numeric categories. Record India-specific factor, tariff, safety and language
work as pending evidence/product decisions. **Status:** accepted boundary;
human research/source confirmation required.

## Issue 5 — What counts as ready?

All reviewers agreed that source compilation, pure tests and static inventories
do not prove browser usability, JavaFX launch, accessibility, participant findings
or formal submission readiness.

**Final resolution:** conditional team-review readiness only. No fictional meeting,
peer review, reflection, participant result, approval, hour or GitLab contribution
is promoted to verified evidence.
