# Carbon Saathi user guide

Carbon Saathi is an offline JavaFX desktop prototype for exploring lifestyle records, transparent electricity estimates and feasible actions. Its numbers are partial estimates, not a complete footprint or evidence of achieved emissions reduction. It has no approved emission-factor library.

## Start the application

For the extracted macOS Apple Silicon release, double-click **Start Carbon Saathi.command**. Keep the complete extracted folder together, including `lib/`, `run.sh` and the JAR. The launcher selects installed Java 21 and loads the packaged JavaFX libraries. The release requires Java 21 and a graphical desktop; Maven is unnecessary. Opening the JAR directly will fail because its JavaFX dependencies are separate files. From Terminal, use `./run.sh --demo` for isolated examples.

For development from this source checkout, Java 21, Maven and a graphical desktop are required. This checkout's wrapper uses its local toolchain when present; other systems must supply Java 21 and Maven. The first dependency download requires network access; the application itself has no network workflow.

```sh
./scripts/mvn.sh javafx:run
```

Explore synthetic examples without changing personal data:

```sh
./scripts/mvn.sh javafx:run -Djavafx.args=--demo
```

A demo banner stays visible. “Leave demo” restores the saved workspace; demo changes disappear. Demo exports remain marked illustrative and cannot be imported into the real workspace.

By default, application data lives in `~/.carbon-saathi/records.cfs`. To select another directory:

```sh
./scripts/mvn.sh javafx:run '-Djavafx.args=--data=/absolute/path/to/data-directory'
```

The directory is created when needed. Data is not encrypted. People with access to your OS account can read the files. Do not enter personal names or medical information. If loading fails, the app preserves the existing store and disables editing instead of replacing it.

## Record and inspect activities

On **Overview & records**, choose **Add activity**. Select category, known total quantity, inclusive start/end dates and personal/household scope. Units follow categories: electricity kWh; travel passenger-km; water litres; food, packaging and waste kilograms; repairs, clothes and devices counts. Zero means a known zero; an unknown quantity is not entered as zero.

Quantity covers the entire inclusive period. The end date cannot precede the start. Whole-house electricity and another electricity record with any overlapping dates are blocked, including a daily appliance record inside a monthly bill. Adjacent non-overlapping periods are allowed. Different component records can coexist; the tool cannot identify unmarked duplicate components. A household total is never allocated to individual residents.

Mark **Not changeable** if a choice is constrained; no explanation is required. That record remains visible and retains its estimate, but does not generate discretionary suggestions.

A factor is optional and available only for electricity. To supply one, enable **Use my own unverified electricity factor**, enter its value and metric (CO2 or CO2E), source, reference year, geographical applicability and boundary/method. Ensure its denominator is kWh and its scope is appropriate. These inputs do not become “verified” because they have been entered. Without a factor, carbon is unknown while quantity remains visible. Other categories are descriptive.

**Save record** validates and persists the entire proposed change; errors keep the form available to correct or cancel. **Cancel** discards the pending edit. Select a row and use **Inspect / edit selected** to see its assumptions. Use **Preview estimate (no save)** to check proposed values. Saving an edit updates the current record and retains its previous complete inputs and factor in local revision history. A no-op save creates no duplicate revision. Select the record and use **History of selected** to inspect current and previous estimates and provenance. Historical snapshots never enter current totals. Earlier edits made before history support cannot be recovered.

The overview shows partial totals separately for personal/household scope and CO2/CO2E. Records with different factor values, provenance, geography, boundary, year or status are not aggregated: the overview asks you to inspect them individually. Scope and inclusive From/To filters affect displayed records and totals, not stored data. A record appears only if its entire period lies within the filter. A monthly total is never prorated or included as if it covered just one selected day. An inverted filter shows an error and no results. Coverage states what is excluded. Household figures do not indicate anybody's personal share.

## Compare and choose

Select an electricity record on the overview, then open **Actions & comparisons** and choose **Compare selected electricity record**. Enter an alternative kWh quantity for the same period. The signed difference retains the same scope and factor: a negative value means an increase. An optional flat INR/kWh tariff shows a variable-cost illustration excluding slab pricing, fixed charges and taxes. Preview changes no record. Protected records cannot be used in discretionary comparisons.

The water calculator divides litres by a positive bucket capacity. For example, 40 litres with a 20-litre bucket equals two buckets. This is a volume comparison, never carbon savings. Zero water is valid; zero bucket capacity is invalid.

Suggestions follow explicit category rules and are not machine learning. **Choose action** records one current plan, not completed savings. **Not feasible** remembers a dismissal and hides the suggestion text. **Undo plan** or **Undo dismissal** reverses that choice. If no eligible activity remains, the application says so. You decide whether an action fits your actual circumstances.

## Import, export and delete

**Export personal audit** previews the exact CSV for personal records wholly contained within the overview's From/To dates (blank dates are unbounded). Household records are always excluded, regardless of the overview scope filter. This workspace has no person identities: confirm that the personal records belong to you. Empty results cannot be saved. Review all fields, including free-text provenance and the not-changeable flag; this audit is not a household sharing card. Cancel the preview or save chooser to create no file. The saved contents exactly match the preview.

Action choices and revision history are excluded. To preserve the complete workspace, close the application and copy records.cfs to a separate backup location; CSV alone cannot restore it. Open CSV as text or import spreadsheet columns as text to avoid interpreting provenance as formulas. Household summary sharing remains unimplemented.

**Import CSV** accepts the application's documented schema, validates the complete file and asks before replacing all current records and clearing revision history and action choices. Invalid files do not replace the existing dataset. The import size limit is 5 MB. Illustrative factors are refused in the real workspace. Back up the local project file before replacing a dataset if you need the history.

**Delete selected** asks for confirmation, then removes the selected record and its revision history. There is no recycle bin; recover from a separately saved project-file backup if required. If another application changes the project file, the store refuses an unsafe overwrite; restart and inspect the changed data.

## Accessibility and limitations

The interface is English. Hindi translation, screen-reader/user validation, mobile deployment, individual consent and household shares, smart devices, preset logging and longitudinal charts are not delivered. Tab and Shift+Tab navigate standard controls, with visible focus and textual labels. Colours carry no exclusive meaning. Use platform accessibility settings as appropriate; usability with assistive technology remains to be evaluated.

The synthetic 168 kWh / 0.716 kg CO2 per kWh example is an arithmetic fixture, not verified India-specific emissions data. Neither short-session usability nor choosing a plan establishes actual reduction.

## Older files and period migration

The current project format is version 3, retaining revision history; CSV retains its endDate column and current-record-only scope. Version 1 and 2 project files and the original CSV schema remain readable. For version 1 files, the single stored date becomes both start and end because the original period cannot be recovered automatically. Review and correct any legacy monthly records before relying on overlap protection. Loading does not modify a legacy file. Its first explicit save keeps the original bytes in a sibling records.cfs.v1.bak or records.cfs.v2.bak file before writing version 3; a conflicting existing backup blocks the save. Preserve that backup until you have checked the migrated records. Never open the newer file with an older application build.

## Smoke mode and verification boundary

```sh
./scripts/mvn.sh javafx:run '-Djavafx.args=--smoke --snapshot=/absolute/path/to/smoke.png'
```

This opens synthetic data, logs stage visibility, tab count and personal-scope filtering, optionally writes a screenshot, then exits after three seconds. It never writes the real dataset. A functioning graphical session is required. On 24 October 2026, desktop smoke succeeded in isolated demo mode, including the period-aware build. The screenshot was inspected and a clipped summary corrected. This checks launch, visible records and basic scope filtering; it does not establish complete UI acceptance or participant usability. See docs/evidence/period-model-verification.md for the current evidence and limits.


The optional `--history-smoke` mode runs an isolated scripted edit/preview/cancel/save/history check, captures the history dialog when --snapshot is supplied, and exits. It returns a failure exit code on a failed assertion. This is a software test mode, not a user evaluation. See docs/evidence/revision-history-verification.md for the exact checks and limits.
