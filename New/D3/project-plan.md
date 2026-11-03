# D3 project plan — working version

## Sprint allocation proposal

| Sprint/value slice | Stories | Lead | Support/reviewer | Acceptance gate |
|---|---|---|---|---|
| Setup and thin vertical slice | D2-US01, D2-US04 | Keshav | Hiranya, Arnav | Clean Java/JavaFX checkout and one validated record path |
| Trust/provenance | D2-US02–US04 | Arnav/Keshav | Hiranya | Numeric and unavailable estimates are distinguishable and inspectable |
| Constraint-aware action | D2-US05–US06 | Hiranya | Keshav, Arnav | Protected choice and action states are testable |
| Category/scope boundary | D2-US07–US09 | Whole team | Rotating reviewer | Descriptive categories, language/accessibility boundary and shared scope are explicit |

This allocation is proposed from the role descriptions and must be confirmed by
the students. It is not a record of hours or completed work.

## Definition of Done proposal

A story is done only when its acceptance criteria are written, implemented in the
canonical source, covered by an appropriate automated or manual test, reviewed by
a non-author, documented in `logic.md`/traceability where relevant, and integrated
without breaking the clean build.

## Setup gate

1. Confirm JDK, Maven/Gradle and JavaFX versions.
2. Commit a complete source tree and build descriptor.
3. Build on a clean checkout without ignored local files.
4. Run the test suite and record command, count and result.
5. Run the application and record platform limitations.
6. Link architecture diagrams and package responsibilities.

## Management evidence to retain

Issues, merge requests, review comments, actual task records, burndown snapshots,
sprint review, retrospective and changes to scope. No hours, ceremonies or
reviews may be reconstructed from this plan after the fact.
