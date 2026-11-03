# D7 data audit rehearsal

> **FICTIONAL RECONSTRUCTION — created by AI from the project description, team working profiles, available artefacts and COMP2300 specification. This is an illustrative simulation, not a verified record of an event. The team must review, correct and replace it with an accurate account before any formal use.**

**Status:** SIMULATED analysis rehearsal  
**Date:** illustrative date — verify  
**Participants:** none; this is a data-governance exercise

## Exercise

The team rehearsed running the analyser on the blank D7 template. The expected
result is `not_conducted`, zero participants and zero observations. This confirms
that an empty file is not silently treated as a successful evaluation.

## Failure scenarios rehearsed

- duplicate participant/task row → reject;
- negative or non-finite time → reject;
- impossible comprehension score → reject;
- critical issue → task gate fails;
- missing task row → task gate fails.

This exercise tests the analysis utility, not a participant study.
