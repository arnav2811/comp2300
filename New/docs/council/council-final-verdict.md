# Council final verdict — 3 November 2026

**Status:** CONDITIONALLY READY FOR TEAM REVIEW; NOT READY FOR FORMAL
SUBMISSION.

This verdict follows the independent Keshav engineering review, independent
Hiranya UX/inclusion review and the clearly labelled Astra fallback
Arnav-research/editorial review. The requested Agent Arnav subagent failed first
with an upstream interruption and again with a server-overload response; no
separate returned Agent Arnav findings are claimed.

## Evidence reviewed after implementation

- D2 prototype logic: **7/7 Node tests passed**.
- D2 structure contract: **passed** for seven panels and required controls.
- D2 JavaScript syntax: **passed**.
- D7 analysis tests: **4/4 passed**.
- Java/JavaFX direct compilation: **passed** after the repair pass.
- Core smoke: **10 checks passed**, including quantity boundary, exact aggregate
  rounding and strict invalid-boolean rejection.
- Markdown relative-link audit: **176 files, zero missing links** at the latest
  recorded audit point; re-run after final edits is still required.
- Maven clean verification: **blocked** because Maven is unavailable.
- Graphical JavaFX launch and manual browser/accessibility walkthrough:
  **pending**, not inferred from static checks.

## Common priorities accepted by all reviewers

1. Close environment gates: Maven, JavaFX launch, browser walkthrough, keyboard,
   screen-reader, scaling and contrast checks.
2. Preserve the distinction between illustrative, user-supplied, verified,
   unavailable, proposed, fictional and pending evidence.
3. Obtain human approval of the Footprint Lens name, Concept A, MVP boundary and
   novelty comparison.
4. Keep D7 at `not_conducted` until an approved real evaluation produces
   de-identified observations.
5. Replace fictional/reconstructed meetings, peer reviews, hours and reflections
   with genuine records before formal use.
6. Keep numeric factors provenance-bearing and prevent household totals from
   being interpreted as individual attribution.

## Accepted decisions

| Decision | Reason | Status |
|---|---|---|
| Retain Footprint Lens as provisional product name | Clear current-facing identity; team approval remains open | Pending team confirmation |
| Retain Concept A as provisional MVP candidate | Covers the Must path with inspectable result and feasible action | Pending team confirmation |
| Keep unsupported categories descriptive | Avoids fabricated zero or unsupported carbon factors | Implemented |
| Keep JavaFX factor inputs blank until explicit opt-in | Prevents illustrative fixture being mistaken for user data | Implemented; UI walkthrough pending |
| Aggregate exact values and round totals once | Reduces cumulative rounding error | Implemented and smoke-tested |
| Reject malformed CSV, invalid booleans and header/row mismatches | Prevents silent data mutation | Implemented; tests added |
| Keep browser prototype preview-only | No browser persistence exists | Implemented in wording/state reset |
| Keep JavaFX action choices session-only for this review build | Durable plans require schema and restart acceptance tests | Explicitly documented; future scope |
| Use a read-only unit output rather than an inert select | Category owns unit; avoids false affordance | Implemented; browser walkthrough pending |

## Deferred or unresolved trade-offs

### Durable action plans

Keshav prioritised persistence correctness; Hiranya prioritised truthful action
feedback; Arnav prioritised scope and evidence. The council deferred persistence,
documented session-only behaviour and kept a future increment open.

### Layered architecture

The D3 target includes repository/application-service boundaries, while the
current small build uses `Workspace` and a concentrated JavaFX shell. Broad
refactoring is deferred until a team-approved increment can add meaningful
integration tests. The reports must not claim the target architecture is fully
implemented.

### Localisation

English-only remains the verified implementation boundary. Hindi or other
language support requires centralised strings, fluent human review, number-format
checks and accessibility validation. No language support is claimed yet.

## Critical/high finding disposition

| Finding | Disposition | Dependency if open |
|---|---|---|
| Maven/JUnit/package/runtime proof | Blocked | Maven 3.9+ and a supported desktop environment |
| JavaFX factor prefill/reset risk | Fixed in source; direct compile passed | Graphical walkthrough |
| Quantity boundary mismatch | Fixed in domain/UI | Maven/UI regression run |
| Aggregate rounding | Fixed and smoke-tested | Maven/JUnit confirmation |
| Malformed CSV/boolean/schema acceptance | Fixed and regression tests added; smoke covers all three | Maven/JUnit confirmation |
| Stale browser quantity | Fixed by new-record and category-change clearing | Browser walkthrough |
| Browser unavailable/persistence wording | Fixed to preview-only wording | Browser walkthrough |
| Inert browser unit control | Replaced by semantic output | Browser/accessibility walkthrough |
| JavaFX action buttons active without valid selection | Fixed with selection-aware wrapped controls | Desktop walkthrough |
| Unsupported India-specific factor/tariff claims | Deferred/blocked | Verified source metadata and team approval |
| D2 ideation/novelty/final selection evidence | Blocked | Genuine team evidence and confirmation |
| D7 empirical findings | Blocked | Approved recruitment, controlled session and dataset |

## Deliverable readiness

| Deliverable | Verdict | Reason |
|---|---|---|
| D2 | Conditionally ready for team review | Concepts, stories, prototype, inventory and report source exist; manual tests, ideation evidence, novelty, approval and PDF remain open |
| D3 | Prepared draft | Protocol, architecture, materials and threshold analysis exist; protocol approval and clean setup evidence remain open |
| D4 | Conditionally ready technical review | Increment source and acceptance material exist; Maven, JUnit-through-Maven, launch and genuine sprint evidence remain open |
| D5 | Conditionally ready technical review | Boundary/metric/action repairs and testing plan exist; integration/UI evidence and genuine project records remain open |
| D6 | Not release-ready | Release checklist, guide and quality gates exist; desktop, accessibility, packaging and Maven gates are open |
| D7 | Prepared but not conducted | Blank schema/analyser/tests exist; no participant findings or empirical conclusions may be written |
| Six-month reconstruction | Rehearsal-ready only | Detailed records exist under the required notice; they are not historical evidence |
| Individual reflections | Draft-only | Placeholders require each student's genuine contribution and experience |

## Human approvals required

- Team approval of name, Concept A, MVP boundary and D2 stories.
- Team confirmation of authentic ideation, meetings, peer reviews, hours,
  contributions and reflections.
- Verified competitor/novelty evidence.
- D3 protocol and recruitment-threshold approval before contacting another group.
- Human browser, JavaFX, accessibility and language review.
- Formal PDF/Word review, references, word limits and AI disclosure.
- Appropriate evaluation approval/consent process and real de-identified data.

## Final council conclusion

The workspace is substantially more coherent and technically defensible than the
earlier review build. The highest-risk source defects found by the engineering
and UX passes have been fixed or explicitly bounded, and the change/action logs
map findings to artefacts and checks. The project must remain a review build until
the open human, build, desktop, document and participant-evidence gates are
closed.
