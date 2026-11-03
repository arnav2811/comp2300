# Project strategy

**Project:** Footprint Lens  
**Topic:** Topic 1 — Carbon Footprint Awareness and Reduction: India-Centric Challenge  
**Baseline:** D1 final report supplied by the team, October 2026  
**Status date:** 2 November 2026

## 1. Objective and intended users

The official challenge asks for an interactive tool that educates users about
transport, home energy, food, waste and water choices, provides personalised
feedback, suggests reduction actions and visualises progress. The D1 baseline
narrows this to an India-relevant, non-moralising tool for six evidence-based
personas across urban students/shared housing, time-poor professionals and
multi-generational households.

The established Job to Be Done is:

> When I want to understand how my everyday routines and household habits affect
> the environment, I want to see which activities matter most in terms I can
> picture and which realistic alternatives are open to me, so that I can make
> changes that fit my budget, safety, rota, and shared home without being
> misled, lectured, or blamed for constraints I cannot control.

This is a design hypothesis grounded in the D1 literature and illustrative
coursework transcripts, not a population-level finding.

## 2. Strategic priorities

1. **Trust before precision:** show the boundary, source status and assumptions
   behind any estimate; never turn an unknown factor into a false zero.
2. **Feasible action before moral scoring:** preserve safety, shift-work, heat and
   housing constraints; offer an achievable alternative rather than blame.
3. **Low effort and accessibility:** prioritise a short path, familiar units,
   readable language and an accessible interaction model.
4. **Shared use without unsupported attribution:** show communal activity without
   implying that a shared bill proves an individual's emissions.
5. **Traceability over feature volume:** every D2 story must link to a D1 theme,
   persona/scenario, acceptance criterion and later test or evaluation task.

## 3. Scope and boundaries

### D1-established direction

The D1 baseline identifies Must epics E1–E5: familiar interpretation, visible
assumptions, feasible actions, relevant Indian/circular categories, and brief
offline/local-language logging. D1 also identifies E6–E9 as Should, E10–E12 as
Could, and E13–E14 as Won't for the current version, subject to D2 refinement.

The three candidate gaps discussed in the Week 6 minutes are quick-commerce,
dairy/food choices and Tier-2-to-Tier-1 intercity mobility. The minutes record
these as a direction, not as proof that any one is the highest-emission activity
for every user.

### Current exclusions unless new evidence changes them

- No claim that the prototype reduces emissions or changes behaviour without
  summative evaluation evidence.
- No speculative machine learning as a dependency; the official D2 brief
  explicitly disallows relying on speculative ML.
- No automatic bank/order/hardware data collection until privacy, feasibility and
  evidence are justified.
- No forced individual attribution or rankings in shared households.
- No emission factor is accepted merely because it appears in a scenario. Factors
  require source, year, geography, unit and boundary checks.

## 4. Workstreams and decision gates

| Workstream | Near-term outcome | Gate |
|---|---|---|
| Evidence and research | D1 claims, limitations and candidate gaps are source-labelled | No claim enters D2 without provenance or an explicit hypothesis label |
| Conceptual design | Three distinct concepts and one justified choice | D2 rubric coverage and stakeholder/scenario coverage check |
| Requirements | Small stories with MoSCoW and observable acceptance criteria | Every Must story has a walkthrough route and later verification plan |
| Evaluation | D2 plan that can become the D3 controlled lab evaluation | Another COMP2300 group, user-centred method and no sensitive data |
| Engineering setup | Reproducible Java/JavaFX source, build and test command | Clean checkout compiles and tests without hidden local files |
| Project management | Issues, minutes, reviews, risks and truthful contribution records | Evidence exists before a claim is made in a report |

## 5. D2 approach

The D2 work cycle will:

1. Extract design constraints from D1 themes S1–S3 and L1–L3.
2. Generate a broad set of responses without treating the Week 6 ideas as
   automatically adopted.
3. Select three concepts that differ in interaction strategy, not merely colour
   or screen layout.
4. Compare concepts against stakeholder fit, novelty, feasibility, accessibility,
   data/evidence risk and D3–D7 implementability.
5. Split E1–E5 into small stories and retain the original epic IDs.
6. Build a low-fidelity walkthrough covering at least one representative path
   from each Must epic and explicitly mark unsupported paths.
7. Draft an evaluation plan that measures usability/comprehension of the design,
   not actual carbon reduction.
8. Review the draft against the official rubric, then record actual student
   feedback and revisions.

## 6. Roadmap and quality gates

The full checkpoint plan is in [`docs/roadmap.md`](docs/roadmap.md). Official
deadlines come from the supplied specification; Thursday checkpoints are a team
planning proposal and are not official submission dates.

## 7. Current decisions and open decisions

### Established decisions

- Topic 1 is confirmed.
- D1 uses six personas, four scenarios, themes S1–S3/L1–L3 and research
  questions RQ1–RQ3.
- The Week 6 minutes record that lightweight ML was proposed for investigation,
  not adopted.
- The Week 6 minutes record an initial GenAI boundary: scoped assistance is
  acceptable, while foundational coding should not be delegated as a substitute
  for understanding.

### Open decisions requiring team confirmation

- Which three D2 concepts will be submitted and which one is selected.
- The MVP category subset and language set.
- Whether shared-household opt-in attribution is feasible and ethically/technically
  supportable, or remains deferred.
- Which emission-factor sources are acceptable for the chosen prototype paths.
- The team-supported Java/JavaFX/Maven version and clean-checkout result for the
  review build in `application/`.
- Actual owners, review rotation and availability for the next checkpoint.

## 8. Change control

A material change must record: date, decision owner(s), evidence or requirement,
previous position, new position, downstream effects and verification plan. The
team must update this file, `logic.md`, traceability and the relevant deliverable
record in the same work cycle where practical.
