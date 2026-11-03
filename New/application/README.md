# Footprint Lens application

**Release status:** review-ready working build; pending team sign-off and
course-required user evaluation.

Footprint Lens is an offline Java/JavaFX prototype for recording lifestyle
activities, explaining an illustrative electricity estimate, preserving the
personal/household boundary and offering explicit, non-judgemental actions.
Unknown factors remain unknown. The application does not claim to measure
actual emission reduction.

## Requirements

- JDK 21 or later
- Maven 3.9 or later
- Desktop session with JavaFX support

## Build and test

```sh
mvn clean verify
```

Run the desktop application:

```sh
mvn javafx:run
```

The default data file is `~/.footprint-lens/records.csv`. For an isolated review
run, provide `-Dfootprint.lens.store=<path>` to `mvn javafx:run` or the Java
launcher. The application stores only records entered by the local user and uses
no network service.

## Supported behaviour

- Electricity records can show a numeric estimate only when the user supplies a
  factor explicitly labelled `USER_SUPPLIED`.
- Water, travel, food, packaging, waste, repair and reuse records remain
  descriptive until a defensible factor is available.
- Protected activities remain visible but do not receive discretionary advice.
- Household totals remain household totals and are never allocated to a person.
- Input errors preserve the form and provide a correction path.

## Evidence boundary

Automated tests verify domain rules and persistence. They do not replace the
required controlled user evaluation with another COMP2300 group. See `D3/` and
`D7/` for the protocol and evidence-capture gates.
