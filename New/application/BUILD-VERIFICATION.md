# Application build verification

**Review build date:** 3 November 2026  
**Environment:** Windows workspace, Temurin OpenJDK 25.0.1, local JavaFX 23.0.1
Windows jars  
**Review status:** source compilation and core smoke passed; Maven/JUnit clean
verification remains a team-environment gate because Maven is not installed in
the audit shell.

## Checks actually run

| Check | Command/result |
|---|---|
| Java source compilation | `javac --release 21 --module-path <JavaFX 23 Windows jars> --add-modules javafx.controls ...` — passed |
| Core smoke | `java -cp application/build/classes;application/build/test-classes in.carbon.core.CoreSmoke` — passed, 5 checks |
| JavaScript prototype syntax | `node --check D2/prototype/app.js` — passed |
| D2 prototype logic | `node --test D2/prototype/prototype-logic.test.mjs` — passed, 5 tests |
| D2 prototype structure | `python D2/prototype/prototype-structure-check.py` — passed, 7 panels/required controls |
| D2 prototype HTTP smoke | Local server fetched HTML, CSS, app and module assets — passed |
| Markdown links | Repository link audit — passed, zero missing relative links |
| Maven full suite | Not run: Maven is not installed in this environment |
| Desktop JavaFX launch | Not run in this shell: graphical launch/session not available |

## Team release gate

Run `mvn clean verify`, inspect the Surefire and JaCoCo reports, launch with
`mvn javafx:run`, walk the D2 story paths and append the real command output to
this file. A direct `javac` smoke is useful engineering evidence but does not
replace that clean Maven and desktop check.
