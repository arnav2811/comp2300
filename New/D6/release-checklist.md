# D6 release checklist

| Release gate | Evidence location | Status |
|---|---|---|
| Source and build descriptor | `../application/pom.xml` | Present; clean Maven run pending |
| JavaFX application shell | `../application/src/main/java/in/carbon/ui/FootprintLensApp.java` | Direct compile passed |
| Domain tests | `../application/src/test/java/in/carbon/core/CarbonServiceTest.java` | Source present; JUnit run pending Maven |
| Dependency-free smoke | `application/BUILD-VERIFICATION.md` | Passed: 5 checks |
| User guide | `../application/USER-GUIDE.md` | Draft complete |
| Accessibility copy/focus review | Team review record | Pending |
| Runnable archive | `application/target/` after Maven package | Pending clean package |
| PDF/report render | D6 report source | Pending TeX/render inspection |
