# COMP2300 project index

**Project:** Footprint Lens  
**Topic:** Topic 1 — Carbon Footprint Awareness and Reduction: India-Centric Challenge  
**Audit date:** 2 November 2026  
**Official stage:** Week 9; D2 is due in Week 10 on 16 November 2026 at 16:00.

## Source hierarchy

1. Supplied COMP2300 specification: official requirements, terminology, rubrics and dates.
2. Supplied D1 PDF: established research baseline and traceability.
3. Supplied meeting minutes and peer-review record: actual project history.
4. Git history and files present in this repository: repository evidence only.
5. New proposals: clearly marked and requiring team confirmation.

The master prompt requires this separation. A proposal is not a completed task,
and an AI review is not a student peer review or Blackboard submission.

## Verified baseline

| Area | Evidence inspected | Status |
|---|---|---|
| Topic | Official PDF pp. 195–234; D1 report p. 1 | Confirmed Topic 1 |
| D1 group artefact | Supplied `COMP2300_Team1_AM_KS_HM.pdf` | Rendered D1 PDF inspected |
| D1 individual work | Supplied Arnav reflection and D1 pack | Student confirmation still required |
| Week 6 meeting | Supplied `COMP2300_Week6_Minutesfinal.pdf` | Actual record supplied |
| Week 7 meeting | Supplied `COMP2300_Week7_Minutes.pdf` | Actual record supplied |
| Peer review | Supplied `COMP2300_Week6_Week7_Peer_Reviews.docx.pdf` | Record supplied; Blackboard action unverified |
| Official requirements | Supplied `COMP2300_PG_Updated_12_10_26.pdf` | Inspected |
| Current executable source | Git repository audit | Not verified as a complete checkout |
| D2 review package | Root D2 workspace | Draft complete; team review/PDF gate pending |
| Participant evaluation | No actual results supplied | Not conducted/verified |

## Canonical project layout

```text
FootprintLens/
├── README.md
├── logic.md
├── strategy.md
├── CHANGELOG.md
├── docs/
│   ├── project-index.md
│   ├── official-requirements.md
│   ├── requirements-traceability.md
│   ├── roadmap.md
│   ├── ownership.md
│   ├── repository-structure.md
│   ├── commit-plan.md
│   ├── risks.md
│   ├── lessons-learned.md
│   ├── gitlab-handbook.md
│   ├── prism-peer-review-handbook.md
│   ├── meetings/
│   ├── reviews/
│   ├── decisions/
│   ├── evidence/
│   └── deliverables/                 # D1 evidence and deliverable index
├── D2/                               # D2 working artefacts and evidence capture
├── D3/                               # D3 working artefacts and evidence capture
├── D4/                               # D4 working artefacts and evidence capture
├── D5/                               # D5 working artefacts and evidence capture
├── D6/                               # D6 working artefacts and evidence capture
├── D7/                               # D7 working artefacts and evidence capture
├── application/
├── individual-reflections/
└── quarantine/
```

The structure is a controlled target. Empty folders are not evidence of work;
they should be created when the corresponding artefact exists.

## Immediate next actions

1. Review and confirm the D2 design artefact and prototype from D1 evidence and
   the official D2 brief.
3. Resolve the D2 acceptance gaps in the D2 work record before submission.
4. Hold the next real checkpoint and record it using the supplied minutes template.
5. Run `mvn clean verify` and a desktop walkthrough from `application/`.
6. Confirm owners, review rotation and AI disclosure with all three students.

## Missing or inaccessible evidence

- Official source PDF is outside the Git repository; retain it as the authoritative local source.
- D2 final PDF is not compiled in this environment; the formal source and
  low-fidelity prototype exist as AI-assisted working artefacts and are not
  student-approved or participant-tested.
- Week 8 and Week 9 minutes were not supplied here.
- Blackboard submissions, lecturer feedback, merge requests and approvals are not accessible in this audit.
- Maven is not installed in this audit shell; direct JavaFX compilation and a
  dependency-free core smoke check passed. A clean Maven/desktop check remains.
