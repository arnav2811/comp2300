# Revision-history engineering verification - 24 October 2026

## Implemented behaviour and rationale

Editing a current activity now preserves the complete prior Activity as an immutable numbered ActivityRevision. This retains the quantity, category/unit, inclusive dates, scope, necessary/whole-house flags and full factor metadata needed to reproduce the earlier estimate. History is separate from current activities and never enters current totals. Revision numbers are contiguous per record; no-op saves and action choices do not create duplicate revisions.

This implements the retained-original requirement through full record snapshots rather than a separate mutable factor catalogue. A complete snapshot avoids combining an old factor with newly edited quantities or dates. It is appropriate to the existing per-record factor design. It does not implement cross-record factor reuse, tamper-evident audit logs, timestamps, cross-period trend analysis or restoration of earlier versions.

The real editor now offers Preview estimate (no save). Cancel discards pending edits. History of selected opens a read-only view of current and earlier revisions with their calculation inputs and provenance. The view was captured and visually inspected. A changed assumption remains explicitly illustrative or user-supplied; keeping history does not independently verify a factor.

Deletion removes the record and its history. CSV export contains current records only, and replacement import resets history after an explicit warning. The local project file is the full-fidelity backup; CSV is not a history backup. Demo switching clears demo history and never mixes it into the saved workspace.

## Persistence and migration

Project schema 3 adds R rows containing a revision number and full snapshot. Schemas 1 and 2 remain readable. Loading old files does not modify them; the first explicit save retains the old bytes in .v1.bak or .v2.bak before atomic replacement. Pre-existing edits cannot be recovered retrospectively. Conflicting backups, invalid history references, duplicate/out-of-order revision numbers and unsupported schemas fail closed. Current records still undergo normal collection validation; historical snapshots are not incorrectly rejected as overlapping current usage.

## Automated checks

`history-tests.log` records **78 passing Java tests**, zero failures/errors/skips. The 65 previous cases remain, with 13 new revision tests covering factor/quantity/metadata changes, factor removal, no-op saves, action-state preservation, immutable history, deletion/import semantics, malformed history, independent revision sequences, restart, schema-2 backup migration and a failed concurrent save preserving committed history.

The existing period migration assertions were updated to expect schema 3 as the current output; legacy schema inputs remain explicitly covered. Passing unit tests does not establish every graphical flow or participant usability.

## Executed graphical scenario

Command: `scripts/mvn.sh -o javafx:run '-Djavafx.args=--history-smoke --snapshot=/absolute/path/to/history-dialog.png'`.

The opt-in scenario operates on the isolated synthetic demo and uses the real editor controls. It asserts: (1) changing a factor and previewing does not mutate saved state, followed by Cancel; (2) saving negative quantity leaves the dialog open with a validation error and no history; (3) correcting the input and saving factor 0.8 preserves the earlier 0.716 snapshot; (4) the previous estimate remains 120.288 kg CO2 while current becomes 134.4 kg CO2; (5) a no-op save leaves one prior revision; (6) the history dialog opens and closes successfully. The scenario exits nonzero on a failed assertion or timeout. Output is in history-ui-scenario.log; the screenshot is history-dialog.png.

These are scripted software checks, not observations from participants. They do not exercise every keyboard/scaling combination or a real disk failure through the graphical dialog. Domain/storage failure paths are covered separately.

## Release boundary

The earlier period-model release remains unchanged. The history revision is a separate dated engineering package. The archive was extracted to a fresh directory and every packaged hash checked. Its main source compiled offline with the user-installed Oracle Java 21.0.12.1 ARM64 JDK and bundled JavaFX dependencies. The rebuilt package passed all four history-dialog scenario checks. Results are in history-package-build.log and history-package-smoke.log. Java 21 and macOS Apple Silicon are the tested runtime; no other platform is claimed tested.

Team concept/scope decisions, language review, repository access and participant evaluation remain pending. Presets, nonadditive overlap detail, final export-preview semantics and full accessibility acceptance remain open. This work is new engineering evidence for later deliverables, not a claim of completed academic sprints or student hours.
