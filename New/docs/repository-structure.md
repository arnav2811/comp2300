# Repository structure and evidence policy

## Canonical tree

```text
FootprintLens/
├── README.md                         # project entry point
├── logic.md                          # system rules and technical reasoning
├── strategy.md                       # scope, execution and decisions
├── CHANGELOG.md                      # material repository changes
├── docs/
│   ├── project-index.md              # navigation and verified status
│   ├── source-register.md             # authoritative inputs and gaps
│   ├── official-requirements.md       # course requirements summary
│   ├── requirements-traceability.md   # requirement/design/test links
│   ├── roadmap.md                     # checkpoints and official gates
│   ├── ownership.md                   # responsibility and review rotation
│   ├── repository-structure.md        # this guide
│   ├── commit-plan.md                 # planned versus actual commits
│   ├── risks.md                       # risk register
│   ├── lessons-learned.md             # confirmed learning records
│   ├── gitlab-handbook.md              # executable workflow
│   ├── prism-peer-review-handbook.md   # official minimum + local proposal
│   ├── meetings/                      # actual official-template records
│   ├── reviews/                       # substantive review records
│   ├── decisions/                     # dated decision records
│   ├── evidence/                      # logs, checksums and result files
│   └── deliverables/D1-D7/            # report sources and checklists
├── application/                      # canonical Java/JavaFX source
├── individual-reflections/            # student-owned Markdown reports
└── quarantine/                        # unverified historical material only
```

## Purpose and commit policy

| Area | Store | Do not store | Owner/review |
|---|---|---|---|
| `application/` | Java/JavaFX source, tests, build files, resources | compiled output, secrets, personal data | Keshav lead; Hiranya test review; Arnav requirement review |
| `docs/` | editable evidence-linked project records | invented minutes, fabricated results or duplicated source files | relevant workstream lead; non-author review |
| `docs/meetings/` | actual minutes in official format, with date/version | simulated meetings or unconfirmed attendance | meeting chair and minute-taker |
| `docs/reviews/` | actual review records and responses | AI review presented as student approval | author plus independent reviewer |
| `docs/evidence/` | commands, logs, hashes and machine-readable results | claims without command/context | QA owner and reviewer |
| `docs/deliverables/` | report sources, criterion matrices, submission checklists | random generated PDFs without source | report lead and whole-team gate |
| `individual-reflections/` | student-authored Markdown and clearly labelled drafts | writing on behalf of a student without confirmation | named student owns final text |
| `quarantine/` | unverified historical/duplicate material pending decision | active source used by build or reports | whole-team disposition |

## Generated and sensitive material

Ignore build output (`target/`, `bin/`, `out/`), temporary renders, IDE files,
logs that are not evidence, local data stores and all credentials. Commit a
small reproducibility log instead of a large generated directory. If a generated
PDF is a submission artefact, retain its editable source and a recorded render
command.

## Review procedure for structure changes

1. Open an issue describing the problem and affected deliverables.
2. Move/copy only after checking links and preserving the original evidence.
3. Update the project index, source register and commit plan in the same change.
4. Run link/path checks and the relevant build or render check.
5. Obtain a non-author review and record the disposition.
