# D3 report — Evaluation Plan, Architectural Design and Technical Setup

**Status:** draft complete for team review.  
**Product stage:** architecture and evaluation foundation for the D4 runnable increment.

## Work completed in this draft

The D3 package converts the D2 Conceptual Choice Coach into an implementable
Java/JavaFX design. The domain layer now defines `Activity`, `Factor`, `Estimate`,
`Scope`, `ActionPlan` and `Workspace`; `CarbonService` keeps arithmetic and
boundary rules independent of the UI; `LocalStore` provides a versioned local CSV
file; and `FootprintLensApp` supplies the desktop interaction. The D3 evaluation protocol
defines task cards, observation fields, consent boundaries and a descriptive
analysis approach.

## Architecture rationale

The presentation/application/domain/store separation makes the calculation and
uncertainty rules testable without a graphical session. It also ensures that a
missing factor cannot become zero merely because a control has no value. The UI
does not decide whether a record is personal or household, and the action planner
uses visible category rules rather than speculative machine learning.

The architecture diagram is in [`architecture.mmd`](architecture.mmd). The
implementation is in [`../application/`](../application/). The main remaining
team decision is the final evidence-backed factor library; this review build
intentionally accepts only labelled user-supplied electricity factors.

## Evaluation plan

The proposed controlled, moderated study recruits from another COMP2300 group,
uses synthetic activity values, avoids sensitive data and covers five tasks:
recording, inspecting provenance, protecting a constraint, recording a
descriptive category and explaining the shared-total boundary. Measures are
completion, critical errors, help requests, comprehension and carefully recorded
interaction observations. The plan does not claim behaviour change or carbon
reduction.

The draft materials are separated into an information sheet, consent script,
task cards and an observer form. This separation makes the protocol executable
and gives the moderator one stable prompt while keeping the analysis fields
structured. The actual participant count, recruitment record and pilot decision
must be added from team evidence.

## Project management and tools

The project uses Git/GitLab, short-lived feature branches, issue-linked stories,
review gates, Java/JavaFX, Maven, JUnit, Markdown and LaTeX. D3 establishes the
Definition of Done: a story needs acceptance criteria, implementation, suitable
test evidence, non-author review, traceability and a clean integration result.

## D3 review checklist

- [ ] Team confirms the architecture and package names.
- [ ] Team confirms Java/JavaFX/Maven versions on the supported checkout.
- [ ] Team agrees the participant protocol and sample decision rule.
- [ ] Team fills the real sprint allocation and burndown from actual work.
- [ ] Team adds the official meeting, peer-review and reflection records.
- [ ] Clean Maven build and desktop launch are recorded.
