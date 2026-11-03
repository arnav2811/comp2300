# PRISM peer-review handbook

## Official minimum

The supplied COMP2300 specification describes **Peer Review Informed Skill
Assessment (PRISM)** as an individual assessment informed by peer assessments,
GitLab individual contributions and supervisor observations. It requires weekly
peer feedback through Blackboard and states that low engagement can reduce the
PRISM mark. It does not provide an official expansion of the term beyond that
description, nor does it prescribe the local P/R/I/S headings used in the
supplied Week 6/7 review PDF.

This handbook therefore separates official requirements from the team's proposed
working method.

## Proposed local review method

For each substantive artefact:

1. **Prepare:** author links the issue, deliverable criterion and version.
2. **Review:** reviewer checks evidence, correctness, traceability, usability,
   formatting and limitations.
3. **Respond:** author records accepted, rejected or deferred comments and why.
4. **Verify:** author reruns affected checks; reviewer confirms closure.
5. **Retain:** keep the MR/issue link or a concise record in `docs/reviews/`.

The P/R/I/S headings may be used when the Blackboard form or team record requires
them: Positive contribution, Recommendation, Impact and Summary. They are a
local mnemonic, not official course terminology.

## Review record

```text
Artefact/version:
Deliverable/criteria:
Author:
Reviewer:
Date:
Evidence inspected:

Strengths:
1.

Actionable findings (priority, location, consequence, requested change):
1.

Author response and disposition:
1.

Verification after revision:
Command/file/result:

Status: open / revised / deferred / closed
Remaining limitation:
```

## Rotation

The proposed rotation in `docs/ownership.md` prevents self-review and balances
research, engineering, requirements and accessibility perspectives. Students
must confirm the actual Blackboard reviewer assignment and submit their own
feedback. A drafted comment is not a completed peer assessment.

## Quality standard

Feedback must identify a specific location, explain why it matters against a
criterion or user need, request an actionable change, and avoid unsupported
ratings. Reviewers must not invent observations, hours, attendance, approvals or
test results. Disagreements are recorded with both positions and escalated to the
team/supervisor or module team where appropriate.
