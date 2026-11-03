# Semester 2 Week 08 — D5 boundary and partition testing

> **FICTIONAL RECONSTRUCTION — created by AI from the project description, team working profiles, available artefacts and COMP2300 specification. This is an illustrative simulation, not a verified record of an event. The team must review, correct and replace it with an accurate account before any formal use.**

**Status:** FICTIONAL RECONSTRUCTION  
**Date:** illustrative date — verify  
**Attendees/minute-taker:** simulated team; not verified

## Test design

Keshav proposed partitions for quantity, factor status, scope, protected flag and
comparison. Hiranya added UI states for blank and invalid input. Arnav insisted
that the expected result include the evidence boundary, not only a numeric value.

## Boundary cases

Zero is a valid known quantity; blank is missing. Negative is invalid. A missing
factor is not zero. A protected activity remains visible but cannot be ranked as a
discretionary change. Household totals are not divided among residents.

## Disagreement

The team debated whether a zero factor should be accepted. The simulated outcome
was to reject it for the current factor model because it could hide a missing
source. The decision requires actual engineering review before adoption.
