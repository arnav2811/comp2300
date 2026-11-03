# Agent Keshav review — Engineering and Systems Excellence

**Status:** AI council review; not a student review, GitLab review or PRISM
submission. Findings were independently returned by the engineering reviewer.

## Verdict

**Not release-ready; suitable as an internally labelled review build.**

## High-impact findings and implemented responses

| Finding | Response in this pass | Remaining gate |
|---|---|---|
| Factor was selected by default and could be treated as user-supplied | Factor is now opt-in; domain factor includes denominator unit and metric validation | Team UI walkthrough |
| Totals combined incompatible metrics and showed unknown as zero | UI now uses metric-partitioned totals and displays “No numeric estimates” for unavailable-only scopes | Clean Maven/UI test |
| Unreadable store could be overwritten after startup | Save is disabled after load failure and recovery warning is shown | Corrupt-store desktop test |
| Workspace cleared before successful load | Load now reads into a temporary list before replacing state | Persistence tests |
| JavaFX form was not resilient at small windows | Entry form is now inside a fit-to-width scroll pane; labels set explicit controls | Enlarged-text walkthrough |
| D7 sample threshold was incomplete | Protocol and analyser now define proposed 3/4/5 thresholds | Team approval before recruitment |

## Remaining engineering findings

- Maven is not installed and `mvn clean verify` has not run.
- JavaFX desktop launch has not run in the current shell.
- JUnit source compiles, but the JUnit suite has not executed through Maven.
- Action-plan choices remain session-memory state and should be documented or
  persisted before claiming durable action history.
- Application/UI, domain and persistence concerns remain concentrated in the
  JavaFX shell; a later refactor should introduce application services/repository
  interfaces if the scope permits.
- A Maven wrapper/CI configuration would improve clean-checkout reproducibility.

## Evidence checked

`application/`, `D3/architecture.md`, `D5/testing-plan.md`,
`application/BUILD-VERIFICATION.md`, direct JavaFX compilation and core smoke.
