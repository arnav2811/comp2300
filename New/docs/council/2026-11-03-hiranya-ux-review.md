# Agent Hiranya review — Innovation, Accessibility and Workflow Excellence

**Status:** AI council review; not a student review, GitLab review or PRISM
submission. Findings were independently returned by the UX reviewer.

## Strong findings

- The prototype now has a universal result route, explicit P3–P6 branches,
  protected-choice behaviour, category-specific units, focus announcements and
  a manual scenario matrix.
- Shared-total safety is an appropriate boundary, but it must not label a
  personal record as a household total.
- D1’s shared-home, safety, shift-work and low-waste constraints are visible in
  the product rather than reduced to a generic carbon score.

## Priority findings and implemented responses

| Finding | Response |
|---|---|
| Provenance was not structured enough | Prototype P3 now displays metric, source, year, geography, boundary and status fields |
| Familiar context could be misread as savings | Context labels now state “not carbon” where appropriate; action copy retains the no-reduction boundary |
| Personal scope could appear as household total | P6 now distinguishes household total from a shared-total preview and shows current scope |
| Invalid categories could fall back to electricity | Pure logic now fails closed for unknown categories |
| Invalid quantities were not independently guarded | Calculation logic now rejects non-finite/negative quantities; validation includes a one-million upper boundary |
| Mobile journey hid all non-active stages | All journey controls remain visible and horizontally scrollable |
| JavaFX labels/form scaling needed improvement | Labels have `setLabelFor` relationships and the entry form scrolls |

## Remaining human verification

Run keyboard-only, screen-reader, enlarged-text, contrast and narrow-viewport
walkthroughs. Review any Hindi/local-language copy with a competent human before
claiming localisation. Run the manual browser scenarios with browser/version/date.

## Verdict

**Conditionally ready for team review.** The interaction model is coherent and
evidence-conscious, but accessibility and usability remain claims to test, not
findings to report.
