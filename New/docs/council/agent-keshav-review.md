# Agent Keshav — independent engineering and systems review

**Status:** FICTIONAL RECONSTRUCTION — AI-created illustrative council review
based on inspected workspace artefacts and executed local checks. It is not a
student peer review, lecturer approval or formal assessment evidence.

## 1. Scope inspected

Inspected `application/pom.xml`, README, user guide, build scripts, Java/JavaFX
source, domain tests and `CoreSmoke`; D2 prototype source/tests/scenarios;
D3–D7 architecture, reports, protocol and analysis tools; changelog, audit and
prior council files. The review also ran direct Java compilation, prototype
checks and D7 tests.

## 2. Verified strengths

- `CarbonService` separates personal/household scope and partitions CO2/CO2E.
- Missing factors remain unavailable rather than becoming numeric zero.
- `Factor` carries denominator, metric, source, year, geography, boundary and
  status.
- `LocalStore` uses a versioned CSV and temporary replacement; unreadable stores
  disable editing in the UI.
- The D2 prototype has seven explicit panels, fail-closed category lookup and
  pure tests for invalid quantities, unavailable factors and protected choices.
- Direct JavaFX source compilation and the dependency-free smoke runner pass.

## 3. Findings

### K-01 — Maven/UI execution remains unverified — Critical

`application/pom.xml` is present, but Maven is unavailable in the environment.
No clean Maven test run, Surefire output, packaged build or JavaFX launch has
been demonstrated. Direct compilation is useful evidence but is not equivalent.

**Required acceptance:** run `mvn clean verify`, inspect test reports, run
`mvn javafx:run` with `-Dfootprint.lens.store` and record a real desktop journey.

### K-02 — Action choices are session-only — High

`Workspace.choose()` and `dismiss()` update in-memory sets only. The UI now
states this limitation explicitly, but the architecture/report language must
not imply durable action history.

**Decision:** retain session-only scope for the review build; label it in the
guide and do not claim progress tracking.

### K-03 — Persistence validation needed strict boundaries — High

CSV parsing previously accepted malformed quoted fields, invalid booleans and
header/row shape mismatches. Strict parsing and schema pairing are now present;
regression checks were added to `CarbonServiceTest` and `CoreSmoke`.

### K-04 — Aggregate precision needed an explicit policy — High

Per-record rounding could accumulate error. Estimates now retain exact products
and totals round after aggregation. A small-factor regression is included.

### K-05 — Prototype and desktop factor journeys diverged — High

The browser uses an illustrative electricity fixture while JavaFX uses an
explicit user-supplied factor path. The divergence is acceptable only if both
artefacts are described as stage-specific and the desktop never pre-fills a
fixture. The JavaFX factor fields now start blank.

### K-06 — Architecture drift — Medium

D3 describes repository/application-service boundaries, while the current shell
constructs `Workspace` directly and concentrates orchestration in
`FootprintLensApp`. This is reasonable for a small review build but must be
reported as an implemented simplification, not as completed layered
architecture.

### K-07 — UI test gaps — High

Missing automated/UI evidence includes JavaFX launch, corrupt-store walkthrough,
factor opt-in interaction, protected action control state, scaling and restart
behaviour. These remain human/environment gates.

## 4. Commands and results

- Direct JavaFX compilation: **passed**.
- Core smoke after the repair pass: **10 checks passed**.
- D2 Node tests: **7 passed**.
- D2 structure check: **passed**.
- D7 Python analysis tests: **4 passed**.
- Maven: **not available**.
- Graphical JavaFX launch: **not run**.

## 5. Priority ranking

1. P0: clean Maven verification and desktop walkthrough in a supported setup.
2. P1: preserve strict persistence and calculation regression tests.
3. P1: keep action state explicitly session-only or implement persistence.
4. P2: reconcile D3 architecture with the actual implementation.
5. P2: add UI/integration evidence only where the environment supports it.

## 6. Confidence and limitations

High confidence applies to source-level defects and executed command outputs.
Desktop layout, accessibility APIs and user comprehension were not established
without a graphical run. No participant result is inferred.
