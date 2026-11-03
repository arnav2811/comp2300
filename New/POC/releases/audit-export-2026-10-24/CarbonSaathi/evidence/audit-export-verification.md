# Personal audit export verification — 24 October 2026

This is a new engineering snapshot supporting the D5 audit workflow, not a completed academic sprint or participant evaluation.

Changes: immutable personal-only export payload; inclusive whole-period date selection; exact read-only CSV preview; explicit provenance/privacy/backup explanations; disabled save for no results; cancellation before file selection; writes the same frozen text that was previewed. Household rows are excluded regardless of overview scope. No person identity system exists, so ownership of personal-labelled records remains the user’s responsibility. Household sharing cards (US12) remain outstanding; US16 is partially addressed rather than claimed fully accepted.

Validation: Maven verify passed 85 tests, zero failures/errors/skips, including seven new privacy/filter/payload tests in AuditExportTest. The real JavaFX --export-smoke scenario passed with two synthetic personal rows, household exclusion, read-only preview, cancellation before the save chooser and unchanged project state. Screenshot review identified and corrected clipped explanatory labels; the final screenshot was inspected. This scenario does not automate the native save chooser or establish participant usability.

Evidence: audit-export-tests.log, target/surefire-reports, audit-export-ui.log and audit-export-preview.png. Source: AuditExport.java, AuditExportTest.java, ExportUiSmoke.java and CarbonApp.java. Previous dated releases are retained unchanged. Full backups use records.cfs; personal audit CSV excludes history/actions and household records.
