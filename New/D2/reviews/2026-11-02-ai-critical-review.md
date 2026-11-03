# D2 working-pack critical review

**Reviewer:** OpenCode coding assistant  
**Date:** 2 November 2026  
**Review type:** AI critical review, not a student PRISM review or Blackboard submission

## Artefacts reviewed

- `D2/conceptual-design-working.md`
- `D2/prototype/index.html`, `app.js`, `styles.css`
- `D2/report/main.tex`
- `D2/rubric-matrix.md`
- D1 report and official D2 specification

## Strengths

1. Three concepts differ by primary interaction model rather than presentation
   styling.
2. The provisional choice is tied to E1–E5 and preserves uncertainty around
   shared attribution, factors and speculative ML.
3. Stories include observable validation, unavailable-estimate, protected-choice
   and recovery behaviour.
4. The prototype exposes a shared-total boundary and avoids turning descriptive
   categories into fabricated carbon credits.
5. The report source uses 12 pt A4 formatting, 1.5 cm margins and a reference
   section consistent with the supplied citation requirement.

## Findings before D2 submission

| Priority | Finding | Required closure evidence |
|---|---|---|
| Major | Actual ideation evidence is missing; the candidate response list is not proof of a team ideation session | Add dated authentic sketches/screenshots or a confirmed account of the real session |
| Major | Concept A is only a provisional recommendation | Record the team's selected concept, rationale and dissent/alternative handling |
| Major | Novelty is a hypothesis, not an evidenced comparison | Compare a small verified set of existing products on interaction, assumptions and constraints |
| Major | No complete Java/JavaFX source is present for later implementation | Restore authorised source and record a clean build before D3 claims setup |
| Moderate | Factor values are illustrative fixtures | Verify source/year/geography/unit/boundary or retain estimate-unavailable behaviour |
| Moderate | Language/accessibility review is not complete | Have a competent reviewer inspect actual copy and keyboard/error paths |
| Moderate | No student peer review or team approval is recorded for this pack | Obtain real review and record accepted/deferred changes |
| Submission | LaTeX source has not been compiled in this environment | Compile with the team's TeX toolchain and inspect every PDF page |

## Verification performed

- JavaScript syntax check passed with Node.
- Local HTTP smoke fetched `index.html`, `styles.css` and `app.js` successfully.
- Markdown relative-link check passed for the repository.
- LaTeX compilation was not run because no LaTeX compiler was available.

## Decision

The pack is a substantive working draft, not D2 submission-ready. The listed
closures are mandatory evidence tasks, not cosmetic improvements.
