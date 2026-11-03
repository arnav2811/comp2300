# Footprint Lens user guide — review build

## What the application does

Footprint Lens helps a user record a small set of lifestyle activities and inspect
what the record means. It is an offline awareness and design prototype. It is not
a complete carbon-accounting service and does not measure achieved reduction.

## Start

1. Install JDK 21+ and Maven 3.9+.
2. From `application/`, run `mvn clean verify`.
3. Run `mvn javafx:run`.
4. If the application cannot load a local store, it reports the error and does
   not silently replace the file.

## Record an activity

Open **Add activity**, select a category, enter a non-negative quantity and choose
Personal or Household scope. Use the protected checkbox when safety, shift work,
heat, housing or another constraint makes the activity not freely changeable.
Add a note only if it contains non-sensitive context.

The electricity factor form is labelled **user-supplied, unverified**. Its value,
metric, source, year, geography and boundary are stored with the record. Other
categories remain descriptive in this build because no approved factor library
has been supplied.

## Understand and compare

The Overview tab separates personal and household estimated totals. Select a row
to inspect the estimate explanation. **Inspect assumptions** shows the formula
and provenance. **Actions & comparisons** uses explicit rules; a protected record
does not receive discretionary advice. Electricity comparison changes nothing in
the saved data and is an illustration under the same factor.

## Important boundaries

- Missing factors are shown as unavailable, never as zero.
- Household totals are not divided among household members.
- An action selected in the application is a current plan, not completed savings.
- Illustrative or user-supplied values are not verified national emissions data.
- The interface is English and has not yet been validated with screen readers or
  course participants.
- Do not enter names, medical information, financial details or sensitive records.

## Data location

Records are saved to `~/.footprint-lens/records.csv`. The file is local and not
encrypted. Back it up before deleting or moving data. The CSV uses a versioned
header and atomic replacement to avoid a partial write.
