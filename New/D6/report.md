# D6 report — Increment 3 and release candidate

**Status:** draft complete for team review.  
**Product stage:** review-ready release candidate.

## Release improvements

The application now has a coherent end-to-end desktop flow: overview totals,
record entry, transparent assumptions, explicit action rules, protected-choice
handling, safe local persistence, comparison feedback and a user guide. Styling
provides hierarchy, visible explanatory copy and a non-colour-only text path.

## Quality position

The core source compiles with JavaFX 23 on the audit machine and the dependency-
free core smoke passes five checks. The project includes JUnit tests for normal,
missing-factor, protected-choice, volume, scope and persistence paths. A clean
Maven run, graphical launch, accessibility review and full regression report are
still team release gates because they cannot be honestly inferred from source
presence.

## User guide

[`../application/USER-GUIDE.md`](../application/USER-GUIDE.md) explains setup,
recording, assumptions, actions, boundaries and local data handling. It matches
the review build and identifies unsupported translation and participant-testing
claims.

## Final-increment review gate

The D6 artefact set is drafted and the implementation is review-ready. Team
members must run the clean package checks, inspect the UI, complete the official
meeting/peer-review/reflection records and record accepted/deferred findings.

## Product/process reflection

The strongest design decision is the explicit separation between a quantity,
factor, estimate and action plan. It keeps the interface useful when evidence is
missing and gives later evaluation a clear comprehension target. The principal
process improvement is to treat every release gate as an evidence checklist:
source, test, reviewer and limitation must travel with the story rather than be
reconstructed at the report deadline.
