# Footprint Lens — Application Runtime & Build Guide

**Application:** Footprint Lens Desktop System  
**Language & Platform:** Java 21+ / JavaFX 21+  
**Architecture:** Clean Layered Architecture (`in.carbon.core` decoupled from `in.carbon.ui`)  
**Status:** Review-Ready Working Build  

---

## 1. System Requirements & Prerequisites

- **Java Development Kit (JDK):** OpenJDK 21 or later (verified compatible with Temurin OpenJDK 25 LTS).
- **Build Tool:** Apache Maven 3.9+ (optional for standard builds) or direct Java CLI toolchain (`javac`, `java`).
- **Operating System:** Windows, macOS, or Linux.
- **Display Server:** JavaFX-compatible graphical environment for UI launch; headless CLI for domain and smoke testing.

---

## 2. Quick Verification: Running the Headless Smoke Test

The domain logic (`in.carbon.core`) has zero dependencies on JavaFX or graphical displays. You can immediately verify the core business logic, carbon math, and persistence without Maven or an active GUI display.

### From Application Directory (`New/application`):
```powershell
java -cp "build/classes;build/test-classes" in.carbon.core.CoreSmoke
```

### From Repository Root (`COMP2300`):
```powershell
java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke
```

### Expected Output:
```text
Footprint Lens core smoke: PASS (10 checks)
```

---

## 3. Building and Running with Apache Maven

If Apache Maven (`mvn`) is installed on your workstation, use the standard Maven workflow:

### Step 1: Clean Build and Execute Automated Tests
```powershell
cd New/application
mvn clean verify
```
This compiles all sources, runs the JUnit 5 test suite (`CarbonServiceTest`), and produces code coverage reports via JaCoCo in `target/site/jacoco`.

### Step 2: Launch the JavaFX Desktop Application
```powershell
mvn javafx:run
```

### Custom Data Store Location:
By default, the application saves user records to `~/.footprint-lens/records.csv`. For an isolated test run that does not modify your personal profile:
```powershell
mvn javafx:run -Dfootprint.lens.store="./test-records.csv"
```

---

## 4. Standalone Recompilation (Without Maven)

If building in an environment without Maven installed, use the direct OpenJDK CLI:

### On Windows (PowerShell):
```powershell
cd New/application

# 1. Create target directories
New-Item -ItemType Directory -Force build/classes
New-Item -ItemType Directory -Force build/test-classes

# 2. Compile pure domain core
javac -d build/classes (Get-ChildItem src/main/java/in/carbon/core/*.java).FullName

# 3. Compile core smoke test harness
javac -cp "build/classes" -d build/test-classes (Get-ChildItem src/test/java/in/carbon/core/CoreSmoke.java).FullName

# 4. Execute smoke suite
java -cp "build/classes;build/test-classes" in.carbon.core.CoreSmoke
```

*(Note: To recompile the JavaFX presentation layer `in.carbon.ui`, supply `--module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml` to `javac`)*

---

## 5. Architectural Structure & Package Separation

```text
src/
├── main/
│   ├── java/
│   │   └── in/carbon/
│   │       ├── core/                      # PURE DOMAIN LAYER (Zero JavaFX imports)
│   │       │   ├── ActionPlan.java        # Immutable record: recommendation & potential savings
│   │       │   ├── ActionPlanner.java     # Deterministic constraint-aware rule engine
│   │       │   ├── Activity.java          # Immutable record: recorded activity entity
│   │       │   ├── CarbonService.java     # Application service orchestrator facade
│   │       │   ├── Category.java          # Lifestyle categories (ELECTRICITY, TRAVEL, etc.)
│   │       │   ├── Estimate.java          # Immutable record: emissions estimate & formula
│   │       │   ├── Factor.java            # Immutable record: factor rate & provenance
│   │       │   ├── FactorStatus.java      # Provenance tiers (VERIFIED, USER_SUPPLIED, etc.)
│   │       │   ├── LocalStore.java        # Atomic CSV file persistence adapter
│   │       │   ├── Scope.java             # Scope boundary: PERSONAL vs. HOUSEHOLD
│   │       │   └── Workspace.java         # In-memory aggregate collection manager
│   │       │
│   │       └── ui/                        # PRESENTATION LAYER (JavaFX Shell)
│   │           └── FootprintLensApp.java  # Accessible JavaFX desktop GUI & event handlers
│   │
│   └── resources/
│       └── style.css                      # Accessible CSS with high-contrast styling
│
└── test/
    └── java/
        └── in/carbon/core/
            ├── CarbonServiceTest.java     # Full JUnit 5 automated test suite
            └── CoreSmoke.java             # Standalone headless verification runner
```

---

## 6. Key Functional Guarantees

1. **Epistemic Honesty:** Only electricity activities with certified Central Electricity Authority (CEA) factors or explicit `USER_SUPPLIED` factors emit numeric emission totals. Unverified categories (water, packaging, repair/reuse) remain descriptive and return `Estimate.unavailable()`.
2. **Protected Choice Honouring:** Activities flagged as necessary for health, safety, or night shifts suppress reduction advice, preventing patronising or harmful nudges.
3. **Strict Scope Separation:** Household energy totals are never artificially divided among occupants, preventing interpersonal conflict and maintaining mathematical integrity.
4. **Offline Privacy:** Zero cloud telemetry, zero remote tracking, zero personal data collection. Persisted locally to versioned CSV format.
