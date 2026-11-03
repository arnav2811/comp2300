# Period-model engineering verification - 24 October 2026

## Change and purpose

The old Activity had one date. A monthly meter total and a daily appliance record could therefore evade overlap checks by using different dates. Activity now stores inclusive start/end dates, validated by ObservationPeriod. The same period appears in entry/edit controls, record rows and comparison context. Quantities remain period totals; no implicit averaging or proration occurs.

Whole-house electricity and any electricity record with overlapping periods are rejected in either insertion order, including overlap on a shared endpoint. Adjacent periods remain valid. Different component records may coexist: the tool has no device identity and cannot automatically identify duplicate components. Filters include only fully contained periods, and reversed filter bounds show an error rather than a misleading empty total. These are current engineering choices consistent with D2; team review of the wider product remains pending.

CSV now appends endDate. Project format version 2 retains both dates. Legacy CSV and version 1 stores load their known date as a one-day interval; the UI and guide warn that the true historical period must be checked. Loading writes nothing. Before the first explicit version-2 save, the original version-1 bytes are preserved in a .v1.bak sibling. A conflicting backup blocks saving. Invalid stored dates are reported as load failures and retained rather than repaired silently.

The first successful graphical screenshot revealed truncated summary/coverage labels. Minimum label sizing and a scrollable record view now keep their content readable. This was a concrete visual defect and correction, not participant feedback.

## Checks actually performed

- Java 21 Maven verify: **65 tests, zero failures/errors/skips** in period-tests.log and Surefire XML. The previous 51 tests remain, plus 14 period/migration tests.
- New tests cover inclusive/leap-day arithmetic, reversed/null dates, contained/partial/endpoint overlap, adjacent periods, component coexistence, whole-period filters, unscaled estimates, legacy/current CSV, strict schema lengths, version-1 backup migration, conflicting backups, restart and corrupt persisted dates.
- Desktop launch: baseline and period-aware app both showed the stage, four isolated demo records and three tabs; personal scope displayed two records. Screenshots were captured and inspected. No ordinary dataset was loaded or changed by smoke mode.
- Runtime JavaFX dependencies resolved from the existing local cache into target/lib, including macOS aarch64 natives.
- Source/binary baseline before changes preserved in releases/engineering-baseline-2026-10-24 with hashes. It is not a historical coursework increment.

## Scope of the evidence

These checks establish automated domain/persistence regression and basic graphical launch/layout. They do not establish complete UI acceptance, assistive-technology usability, a team review, approved requirement changes or participant outcomes. Full dialog workflows and small-screen accessibility still require dedicated checks. The packaged build requires Java 21 and a macOS Apple Silicon graphical desktop.

Presets, versioned assumption history, reviewed Hindi, voluntary individual shares and final export-preview requirements remain open. D2 paper design is unchanged; D3 working documentation tracks actual implementation progress separately.

## Package verification

The package was extracted into a fresh temporary directory and all file hashes checked. Its included build.sh compiled the main sources offline against the packaged JavaFX JARs. The rebuilt application then launched through its own run.sh: stage visible, four demo records, three tabs and two personal records. The captured package screenshot was inspected. Results are in package-verification.log and package-smoke.log. These checks used the local Java 21 JDK and an active macOS Apple Silicon desktop; no other platform was tested.
