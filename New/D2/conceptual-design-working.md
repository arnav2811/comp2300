# D2 conceptual design — working draft

**Version:** 0.2 review draft  
**Date:** 3 November 2026  
**Status labels:** `Established` = supported by supplied D1/course evidence;
`Proposal` = design work for review; `Unverified` = must not be claimed as fact.

## 1. Design challenge and problem statement

The official Topic 1 brief asks for an interactive India-centric tool that helps
people understand lifestyle activities affecting carbon footprint and consider
reduction actions across transport, home energy, food, waste and water. D1
refines the problem: users need understandable, transparent and feasible choices
that respect budget, safety, rota and shared-home constraints.

The D1 interviews are illustrative coursework material. They support design
hypotheses, not general claims about Indian users. This draft therefore tests
interaction ideas rather than claiming that a feature will reduce emissions.

## 2. Design principles derived from D1

| ID | Principle | D1 trace |
|---|---|---|
| DP1 | Show what an estimate includes and what it does not | S1, P2, E2, RQ1 |
| DP2 | Give users a feasible next step, not a moral score | L3, SC2/SC3, E3 |
| DP3 | Keep the core path short and language/accessibility-conscious | S3, P1/P4/P6, E5 |
| DP4 | Treat shared activity as shared unless attribution is supported and voluntary | L1/L2, RQ3, E7 |
| DP5 | Make food, packaging, waste and circular practices visible without pretending all have verified factors | S2, E4, E14 boundary |
| DP6 | Use comparisons as hypotheses to evaluate, not as guaranteed comprehension improvements | RQ1 and D1 literature tension |

## 3. Ideation record to complete honestly

The official D2 brief requires evidence of a wide ideation process. The following
is a structured starting canvas, not a claim that a workshop has already occurred.
The team should add dated photographs/screenshots or a faithful account of the
actual session before submission.

Candidate response families:

1. Explain-and-compare personal activity journey.
2. Shared-household commons view.
3. Low-effort weekly action cards.
4. Local travel choice explorer.
5. Food and delivery friction reducer.
6. Assumption/audit explorer for evidence-conscious users.
7. Rota-aware quiet-action planner.
8. Offline household conversation card.

The three concepts below deliberately select different primary interaction models
from these families.

## 4. Three distinct concepts

### Concept A — Contextual Choice Coach / Footprint Lens

**Primary interaction:** a user enters one activity, sees its estimate with
assumptions and familiar context, then considers one feasible alternative or action.

**Core journey:** activity → scope/constraint → transparent result → inspect
assumptions → feasible action → choose, defer or dismiss.

**Why it addresses the problem:** It joins D1's three strongest tensions in one
walkthrough: abstract numbers need explanation (S1), action must respect limits
(L3), and logging must remain small (S3). It can represent descriptive food,
packaging and circular activities while withholding an estimate when a factor is
not justified.

**Main risk:** a comparison can imply certainty or savings when its factors are
illustrative. The prototype must show provenance and uncertainty at the point of
use.

### Concept B — Household Commons Board

**Primary interaction:** a shared household sees communal resource/activity cards,
discusses one proposed action and optionally creates a consented personal view.

**Core journey:** household total → activity explanation → shared action card →
voluntary opt-in view → review.

**Why it addresses the problem:** It directly targets L1/L2: fixed bills and
shared responsibility make individual policing inappropriate. It can make a
shared geyser, tanker water or lounge AC visible without assigning blame.

**Main risk:** the consent and data model is substantially harder than a shared
display. A household total must not be presented as evidence of any one person's
emissions. This concept is not feasible for D2 unless that boundary can be shown
clearly in low fidelity.

### Concept C — Quiet-Week Action Cards

**Primary interaction:** the user receives a small weekly set of cards tailored to
their constraints, language and available time; each card explains why it was
shown and can be completed, postponed or dismissed.

**Core journey:** set constraints/quiet period → receive one or two cards → inspect
assumptions → choose a realistic action → weekly reflection.

**Why it addresses the problem:** It targets effort and constraint friction in S3
and L3, particularly for night-shift or busy users. It can foreground reusable
tiffins, laundry timing, food waste and travel alternatives without turning the
tool into a score.

**Main risk:** personalisation can become speculative ML or overconfident advice.
The first version must use explicit rules that the user can inspect; lightweight
ML discussed in Week 6 is not adopted as a dependency.

## 5. Provisional concept comparison

| Criterion | A: Choice Coach | B: Commons Board | C: Quiet-Week Cards |
|---|---:|---:|---:|
| Covers D1 Must E1–E5 in one small journey | High | Medium | Medium |
| Makes uncertainty/provenance visible | High | Medium | Medium |
| Handles shared-home boundary | Medium | High | Low/medium |
| Respects protected choices | High | Medium | High |
| Feasible for Java/JavaFX MVP | High | Medium | Medium |
| D3 evaluation clarity | High | Medium | Medium |
| Main evidence risk | false precision | unsupported attribution | over-personalised advice |

**Provisional recommendation:** Concept A is the strongest D2 candidate because it
offers a complete, reviewable Must-epic path while leaving Concept B's attribution
problem and Concept C's personalisation risk visible. This is a recommendation,
not an adopted team decision. The team must compare actual existing tools before
claiming novelty. “Different interaction boundary” is a hypothesis requiring
evidence, not proof of novelty.

## 6. Initial small stories for Concept A

These stories split the D1 Must epics. They are proposals for team review.

| ID | Epic/stakeholder | Story | Priority | Acceptance criteria |
|---|---|---|---|---|
| D2-US01 | E1 / P1–P6 | As a user, I want to record one activity using a familiar category and unit so that I can understand what is being represented. | Must | Given a supported category, the user can enter a valid quantity and unit; the screen states personal/household scope; invalid/missing input is explained without deleting other entries. |
| D2-US02 | E1 / P1–P6 | As a user, I want the result beside kg CO2/CO2e and a labelled familiar comparison so that I can interpret its scale. | Must | A result identifies metric/unit and comparison assumptions; if no valid factor exists, the UI says estimate unavailable rather than zero; no cost/volume comparison is labelled as carbon reduction. |
| D2-US03 | E2 / P2/P3/P6 | As an evidence-conscious user, I want to inspect the factor, formula, year, geography and boundary. | Must | An assumptions route is visible from the result; every numeric factor has provenance status; illustrative/user-supplied values are labelled and editable only where the design permits. |
| D2-US04 | E2 / P2/P3/P6 | As a user, I want invalid assumptions to be rejected with recovery controls. | Must | Negative/blank/out-of-range values produce a specific message; Cancel/Back preserves the last valid state; no failed save is presented as successful. |
| D2-US05 | E3 / P1/P4 | As a user with a safety, shift or heat constraint, I want to mark an activity as protected. | Must | The activity remains visible; no punitive score/ranking is applied; the interface explains that a protected flag changes advice eligibility, not the underlying record. |
| D2-US06 | E3 / P1/P3/P4 | As a user, I want one feasible alternative or action rather than a lecture. | Must | The action states its eligibility reason and limitation; the user can choose, dismiss or defer; the prototype does not claim that choosing it caused an emission reduction. |
| D2-US07 | E4 / P1–P6 | As a user, I want food, delivery packaging, waste, repair or reuse represented when relevant. | Must | The category list states included/excluded scope; descriptive records remain possible when no defensible factor exists; the UI does not invent an offset/credit. |
| D2-US08 | E5 / P1/P4/P6 | As a busy user, I want a fast entry path ($\le 60$s, $\le 3$ clicks), so that logging does not waste time. | Must | Given P0, when user loads example or enters quantity and hits Enter, then result renders in $\le 2$ transitions without mandatory extra forms. |
| D2-US09 | E5 / P1/P6 | As an assistive tech user, I want full keyboard and screen-reader support, so that I can navigate accessibly. | Must | Text labels do not rely on colour alone; all controls are keyboard-focusable with visible focus rings; status changes announced via ARIA live region. |
| D2-US10 | E1 / P1, P3 | As a household manager, I want to view a monthly summary of logged routines, so that I can see recurring patterns. | Should | Given multiple logged entries, when summary is opened, then activities are aggregated by category and unit without cross-unit math errors. |
| D2-US11 | E5 / P2 | As a privacy-conscious user, I want local CSV export of my data, so that my personal routines remain offline. | Should | Given session records, when export is selected, then standard CSV file downloads locally with zero network transmission. |
| D2-US12 | E2 / P3 | As a resident, I want to adjust electricity tariff rates to my state (e.g. Delhi vs Mumbai), for accurate cost comparisons. | Should | Given electricity result, when tariff rate is modified, then variable cost calculation updates immediately with an illustrative note. |
| D2-US13 | E4 / P1 | As a student, I want seasonal monsoon/heatwave routine presets, so that my logging reflects extreme weather. | Could | Given seasonal preset toggle, when activated, then water/cooling baseline recommendations adjust parameters transparently. |
| D2-US14 | E3 / P3 | As a consumer, I want to compare bulk vs single-use delivery packaging, to evaluate e-commerce impact. | Could | Given packaging entry, when compared, then displays descriptive packaging reduction options without carbon claims. |
| D2-US15 | E3 / P1–P3 | Out of scope: Automated black-box AI predictions that infer lifestyle patterns without user consent. | Won't | Boundary rationale: Violates COMP2300 prohibition against speculative machine learning; compromises transparent provenance. |
| D2-US16 | E5 / P1, P2 | Out of scope: IoT hardware sensors or smart meter API integration. | Won't | Boundary rationale: Unfeasible for urban Indian rental flatshares; introduces complex third-party network dependencies. |
| D2-US17 | E3 / P1–P3 | Out of scope: Social leaderboards or peer-shaming rating mechanisms. | Won't | Boundary rationale: Directly contradicts JTBD; promotes guilt and ignores structural socio-economic constraints. |

**Proposed short-path target:** P0 → P1 → P2 uses three primary decisions and
should be completable in approximately 60 seconds for a prepared synthetic
example. This is a design target to test in D3, not a measured result.

## 7. Prototype specification

The revised low-fidelity prototype contains these cards/screens, with arrows,
stable IDs and recovery paths:

1. **P0 Entry and scope:** choose activity category, personal/household scope and
   whether the choice is protected.
2. **P1 Quantity:** enter quantity/unit, with missing and invalid states.
3. **P2 Result:** show estimate state, familiar comparison, source status and
   `Inspect assumptions`.
4. **P3 Assumptions:** show formula, factor, source/year/geography/boundary,
   scope and illustrative status.
5. **P4 Constraint-aware action:** show one eligible action with choose, defer,
   dismiss and explanation states.
6. **P5 Category boundary:** show descriptive category records and “estimate not
   available” behaviour for unsupported factors.
7. **P6 Shared-total warning:** demonstrate that a household total is not a
   personal share; do not imply voluntary attribution is implemented.

The route is intentionally universal at P2: a supported illustrative estimate
and an unavailable estimate are both results first. P2 then routes to assumptions,
action, unavailable explanation or shared-total boundary. This avoids a hidden
branch in which a user cannot inspect why a category is unsupported.

Coverage map: P0–P2 cover E1/E5; P2–P3 cover E2; P0/P4 cover E3; P0/P5 cover
E4; P6 covers the E7 boundary identified by D1 even though E7 is not a Must.

## 8. Evaluation plan for D2 refinement

The working low-fidelity prototype is at [`prototype/index.html`](prototype/index.html),
with coverage and limitations in [`prototype/README.md`](prototype/README.md).
It is an AI-assisted design artefact and requires student review before it can be
included in a submission.

This is a draft plan, not a completed evaluation.

**Method:** moderated, task-based usability walkthrough with think-aloud as an
allowed user-centred method. The final D3 protocol must recruit participants from
another COMP2300 group, run in a controlled quiet room and avoid sensitive
personal data.

**Tasks:**

1. Enter a valid household activity and explain what the result means.
2. Find the factor/boundary and identify whether it is verified or illustrative.
3. Mark a night-safety or shift-work activity as protected and select an eligible
   action.
4. Record a food/packaging or circular activity and identify whether it has a
   numeric estimate.
5. Explain what a shared total does and does not say about one person.

**Measures:** task completion/error, time or interaction count where appropriate,
comprehension questions, observed uncertainty/confusion, and participant comments.
No claim of carbon reduction is measured by this prototype walkthrough.

**Progression rule:** the team must define the permitted sample size and decision
rule for every allowed count before D3. The current draft intentionally does not
invent participant numbers or results.

## 9. Actual management and process record

The Week 7 minutes record that the team planned to work from a single shared
document, use GitLab Issues, translate E1–E5 into stories, start concept sketches,
and keep testing/project-management documentation current. The Week 6 minutes
record the initial GenAI boundary and the investigation-only status of lightweight
ML. These are actual supplied records.

The remaining D2 process evidence must be created from actual work: ideation
notes/screenshots, issue links, review comments, revisions, render checks and the
final team decision. This draft must not claim those actions are complete.

## 10. Unresolved D2 questions

- Which categories can be supported with evidence before D2, and which are
  descriptive only?
- Which language(s) can be reviewed by a team member who understands the wording?
- What is the exact shared-total/attribution boundary?
- Which existing products should be compared to support the novelty argument?
- What is the authorised Java/JavaFX source and reproducible build setup?
