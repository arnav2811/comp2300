# GitLab handbook

This handbook applies to the UoS GitLab repository
`git@git.soton.ac.uk:am31g25/comp2300carbon.git`. It follows the official
requirement to use GitLab for source and preserves truthful contribution evidence.

## Repository workflow

1. Keep `main` releasable and do not work directly on it for substantive changes.
2. Create a short-lived branch from current `main`:

   ```sh
   git switch main
   git pull --ff-only
   git switch -c docs/D2-story-traceability
   ```

3. Link the branch/MR to a GitLab issue where useful. Use names such as
   `feature/D2-concepts`, `fix/core-validation`, `test/D5-boundaries` or
   `docs/D3-architecture`.
4. Make one coherent change, update affected documentation and run the narrowest
   relevant check.
5. Inspect the diff:

   ```sh
   git status --short
   git diff --check
   git diff --stat
   ```

6. Commit as the actual contributor. Push the branch and open a merge request.
7. A non-author reviews requirements, evidence, tests, clarity and limitations.
8. Resolve comments, rerun checks and record the final verification in the MR.
9. Merge only after the agreed review gate; update `CHANGELOG.md` and the commit
   plan for material work.

## Commit messages

Use `<type>(<scope>): <description>`:

```text
docs(D2): map must stories to prototype routes
feat(core): validate activity input
test(D4): cover protected-choice boundaries
fix(ui): preserve entries after validation error
refactor(core): separate factor provenance from calculation
chore(build): record clean checkout command
```

Use the body for rationale, alternatives, limitations and issue references. Do
not use vague messages such as `updates`, `final version` or `fixed stuff`.
Do not create artificial commits to inflate contribution evidence.

## Merge-request checklist

- [ ] Linked issue and deliverable/requirement IDs.
- [ ] Author and actual contribution are truthful.
- [ ] Requirements, `logic.md`/`strategy.md` and traceability updated where needed.
- [ ] Tests/render checks run with commands and results recorded.
- [ ] No credentials, personal data or generated build directories included.
- [ ] AI-assisted material is disclosed and understood by the student author.
- [ ] A non-author review is recorded and unresolved limitations are visible.

## Recovery and safety

- `git restore <file>` discards uncommitted changes to that file; inspect the
  diff first and never use it to erase another person's work.
- `git reset --hard`, force-push and deleting a shared branch are destructive;
  do not use them on shared history without an explicit team decision and backup.
- Resolve conflicts by preserving both the requirement/evidence trail and the
  implementation; rerun checks after resolution.
- Never commit passwords, access tokens, private keys, `.env` files or personal
  participant data. Use local configuration and commit only safe examples.

## Reproducibility evidence

Record JDK/Maven/JavaFX versions, OS assumptions, exact commands, exit status,
test count and known limitations in `docs/evidence/`. A green local command is
not evidence of a clean-clone build until another teammate reproduces it.
