# Foundation checks — 2 November 2026

**Reviewer:** OpenCode coding assistant; this is an AI audit, not a student PRISM review.

## Evidence inspected

- Supplied official COMP2300 specification, including Topic 1, D1–D7 briefs,
  rubrics, meetings, PRISM, formatting, citation and schedule sections.
- Supplied D1 group PDF and Arnav D1 reflection.
- Supplied Week 6 and Week 7 official-format minutes.
- Supplied Week 6/7 peer-review PDF.
- Git repository history, status and tracked-file inventory.

## Checks run

| Check | Command/result | Interpretation |
|---|---|---|
| Markdown links | Python relative-link scan: 40 Markdown files, 0 missing links | Passed for current documentation |
| Git whitespace | `git diff --check` | Passed; Git reported normal LF/CRLF warning for README |
| Official PDF presence | `pdftotext` confirmed Topic 1, Java/JavaFX, D2 and D7 text | Copied source opens and contains expected requirements |
| D1 PDF presence | `pdftotext` confirmed Topic 1, JTBD and E1 text | Supplied D1 baseline opens and contains expected content |
| Java source/core smoke | Direct `javac` JavaFX compile and `CoreSmoke` passed (5 checks) | Review build is present; clean Maven/UI test remains pending |
| Participant evaluation | Not run | No actual participant evidence was supplied |

## Findings

1. The repository entry point was a GitLab template and lacked the continuity
   layer required by the master prompt.
2. The supplied D1 and meeting evidence supports a coherent baseline, but source
   transcripts, Blackboard records and some course materials are unavailable for
   independent re-verification.
3. The next deliverable is D2. Its working pack is now present, but it is not a
   final report or proof of team approval.
4. The `application/` review build is now present; clean Maven, desktop and team
   review evidence remain separate gates.
