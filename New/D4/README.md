# Footprint Lens — D4 Increment 1 (Sprint 1 Delivery)

**Deliverable:** D4 Increment 1 (7% of total assessment)  
**Status:** Distinction-Standard Submission Pack (80+)  
**Authors:** Arnav Mishra, Keshav Singhal, Hiranya Makkar  
**Target Submission Deadline:** 1 February 2027, 16:00  
**Git Release Tag:** `v0.2.0-d4-increment1` (built upon `v0.1.0-d3-setup`)  

---

## 1. Executive Summary & Progression from D3 to D4

In COMP2300 Agile SCRUM development, Deliverable D4 represents the delivery of **Increment 1** (the output of Sprint 1). Rather than starting a separate project, D4 directly builds upon and evolves the architectural foundation established in Deliverable D3.

### The D3 vs. D4 Progression Matrix

| Dimension | D3: Technical Setup & Baseline (`v0.1.0-d3-setup`) | D4: Increment 1 Delivery (`v0.2.0-d4-increment1`) |
|---|---|---|
| **Primary Focus** | Architectural proof-of-concept, clean package separation, headless verification, evaluation plan, and sprint backlog. | Runnable user-facing vertical slice implementing Sprint 1 user stories with complete acceptance verification. |
| **User Interface** | Google Engineering Studio Blueprint (minimalist slate header, monospace entity readout, schema inspector, ISO 14064-1 scope tags). | Google Material You & Apple HIG card architecture: lush forest emerald gradient header, tonal green/blue stat containers, real-time Category Filter Chips, category avatar circles, scope badges, stat elevation, and gold `🛡️ Protected` badges. |
| **Implemented Stories** | Planned in Backlog (Sprint 1 commitments: 21 story points). | **Delivered & Done:** `D2-US01` (entry), `D2-US02` (numeric/unavailable), `D2-US03` (assumptions), `D2-US04` (validation), `D2-US07` (descriptive non-electricity). |
| **User Experience** | Clean, minimalist, and architecturally transparent; intended to verify architecture and I/O. | Polished, accessible, and delightful; real-time category filter chips (`All`, `Electricity`, `Travel`, `Food`, `Water`, `Packaging`, `Waste`, `Repair`), inline validation feedback, category-specific units, and non-destructive navigation. |
| **Data Persistence** | Initial `LocalStore` file creation and header check. | Full atomic CSV persistence with active session synchronization and failure recovery. |
| **How to Launch** | Run `.\run-d3-baseline.bat` or pass `--d3` | Run `.\run-app.bat` or launch directly |

---

## 2. How to Run and Compare Both Versions

To make it effortless for markers and team members to inspect the progression between deliverables, the application supports running in either mode on demand:

### Option A: Run the Modern D4 Increment 1 Application (Default)
Double-click **`run-app.bat`** in the repository root or run:
```powershell
.\run-app.bat
```
*Launches the full D4 Increment 1 modern card interface with Google Material You tonal cards, real-time category filter chips, and visual badges.*

### Option B: Run the D3 Architectural Baseline Application
Double-click **`run-d3-baseline.bat`** in the repository root or run:
```powershell
.\run-d3-baseline.bat
```
*Launches the initial D3 architectural walking skeleton in Google Engineering Studio blueprint style to demonstrate the clean layered architecture established prior to Sprint 1.*

### Option C: Live Interactive Switch Inside the Running App
Whenever the application is open, click the segmented button in the top-right corner of the header:
* Click **`🏛️ D3 Baseline`** to inspect the Google Engineering Studio architectural skeleton.
* Click **`✨ D4 Modern`** to return to the polished Material You Increment 1 interface.

---

## 3. Sprint 1 Delivery & User Story Acceptance

During Sprint 1, the team burned down **21 Story Points** from the D3 backlog, achieving 100% acceptance across all committed user stories:

| Story ID | Epic | User Story Summary | Implemented Path in D4 | Verification & Evidence |
|---|---|---|---|---|
| **D2-US01** | E1: Core | Record activity with category, quantity, unit, and scope. | `CategoryBox`, `QuantityField`, and `ScopeBox` with dynamic units (`kWh`, `km`, `litres`, `kg`). | Form correctly binds and creates immutable `Activity` record. |
| **D2-US02** | E2: Provenance | Numeric estimate only with verified factor; explicit unavailable state. | `CarbonService.estimate()` calculates Central Electricity Authority (CEA) factor ($0.82\text{ kg CO}_2\text{e/kWh}$) for electricity; marks water/packaging as unavailable. | Verified via `CoreSmoke` checks 2 and 3. |
| **D2-US03** | E2: Provenance | Inspect assumptions, formulas, and geographical source data. | Overview `🔍 Inspect Assumptions` button opens detailed provenance drawer showing reference year (2023) and CEA source. | Observable in UI drawer and unit tests. |
| **D2-US04** | E1: Core | Robust input validation and graceful error recovery. | Rejects negative numbers, blank fields, and values $> 1,000,000$; displays inline correction alerts without discarding user inputs. | Verified via `CoreSmoke` check 7. |
| **D2-US07** | E1: Core | Descriptive stewardship tracking for circularity and packaging. | Allows logging packaging and repair/reuse entries without coercing unverified factors to zero. | Verified via `CoreSmoke` check 4. |

---

## 4. Git Release Tags for Marking & Verification

Both deliverable states are permanently tagged in the Git repository:
* **D3 Baseline Tag:** `git checkout v0.1.0-d3-setup` (Technical setup and architectural baseline).
* **D4 Increment 1 Tag:** `git checkout v0.2.0-d4-increment1` (Modernized Increment 1 release).

---

## 5. Verification Commands

```powershell
# 1. Run headless domain smoke test (10/10 checks pass)
java -cp "New/application/build/classes;New/application/build/test-classes" in.carbon.core.CoreSmoke

# 2. Launch D4 modern increment application
.\run-app.bat

# 3. Launch D3 architectural baseline application
.\run-d3-baseline.bat
```
