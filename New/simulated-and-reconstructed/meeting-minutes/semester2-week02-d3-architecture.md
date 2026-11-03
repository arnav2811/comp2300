# Semester 2 Week 02 — Architecture and domain boundary

> **FICTIONAL RECONSTRUCTION — created by AI from the project description, team working profiles, available artefacts and COMP2300 specification. This is an illustrative simulation, not a verified record of an event. The team must review, correct and replace it with an accurate account before any formal use.**

**Status:** FICTIONAL RECONSTRUCTION  
**Date:** illustrative date — verify  
**Attendees/minute-taker:** simulated team; not verified

## Discussion

Keshav presented a domain/presentation/store separation: `Activity`, `Factor`,
`Estimate`, `Scope`, action rules and local persistence. Arnav challenged whether
the factor status was strong enough to prevent a fixture being mistaken for a
verified source. Hiranya asked that the UI make the same boundary visible rather
than relying on code comments.

## Simulated decision

Keep calculation rules free from JavaFX, attach source/year/geography/boundary to
every numeric factor, represent unavailable estimates explicitly, and preserve
personal versus household scope. Do not adopt external ML or network services.

## Acceptance gate

The architecture is accepted only after a clean checkout compiles, core rules are
tested for normal/missing/invalid/protected/household cases, and the JavaFX shell
can be launched by the team.
