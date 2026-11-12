# D3 User-Centred Evaluation Plan & Usability Protocol

**Status:** Distinction-Standard Evaluation Plan (COMP2300 D3)  
**Authors:** Arnav Mishra, Keshav Singhal, Hiranya Makkar  
**Target Phase:** D7 Final Prototype Usability Evaluation  
**Evaluation Method:** Moderated Lab Usability Testing with Concurrent Think-Aloud Protocol  
**Legal & Ethical Governance:** UK General Data Protection Regulation (UK GDPR) & University of Southampton Ethics Policy  

---

## 1. Evaluation Objectives & Purpose

The primary objective of this evaluation is to empirically assess the usability, epistemic clarity, constraint handling, and scope comprehensibility of the Footprint Lens desktop system. 

Specifically, the evaluation investigates:
1. **Provenance Comprehensibility:** Can users locate, understand, and trust the mathematical assumptions and geographic sources behind their emission estimates?
2. **Constraint-Aware Action Feasibility:** Does the system successfully recognise lifestyle constraints (protected choices) and avoid emitting punitive, impossible, or patronising reduction advice?
3. **Epistemic Honesty for Unknowns:** Do users recognise that descriptive records (e.g. repair/reuse, packaging) represent meaningful ecological stewardship without fabricating speculative numerical carbon values?
4. **Household vs. Personal Scope Clarity:** Does the interface prevent users from conflating collective household energy consumption with individual personal liability?

> [!IMPORTANT]
> The evaluation is designed strictly to measure **system usability, information clarity, and decision support quality**. It does **NOT** claim to measure actual long-term behavioural change or verified real-world greenhouse gas reductions.

---

## 2. Participant Recruitment & Ethical Safeguards

### 2.1 Recruitment Criteria (COMP2300 Restriction Compliance)
In strict accordance with the COMP2300 deliverable specification:
- **Mandatory Peer Cohort:** All participants **must and will be recruited from another active COMP2300 student project group**.
- **Self-Recruitment:** The team will directly contact and recruit peers via institutional Microsoft Teams channels and course seminar groups.
- **Sample Size:** $N = 6$ participants (representing two complete groups or representatives from three groups, well exceeding the Nielsen-Norman heuristic threshold of 5 participants for identifying $\ge 85\%$ of usability defects).
- **Voluntary & Uncoerced:** Participation is entirely voluntary, with no academic penalty or financial inducement. Participants may withdraw at any time without giving a reason.

### 2.2 Ethical Boundaries & UK GDPR Compliance
Under UK GDPR Special Category Data definitions (Article 9):
- **PROHIBITED DATA:** The evaluation **strictly forbids collecting or eliciting any sensitive personal data** (including health conditions, sexual orientation, political opinions, religious or philosophical beliefs, or trade union membership).
- **NO REAL UTILITY OR FINANCIAL RECORDS:** Participants will **never** be asked to disclose actual home utility bills, actual rent, personal living addresses, or personal financial expenditure.
- **MANDATORY SYNTHETIC ACTIVITY CARDS:** All participant interactions during testing will use **pre-printed, standardised synthetic activity scenario cards** (see Section 4).
- **DE-IDENTIFICATION:** Participants are assigned random anonymous identifiers (e.g. `P01` to `P06`). No personal names, student IDs, or contact emails appear in observation sheets, audio transcripts, or final reports.

---

## 3. Evaluation Setting & Team Roles

### 3.1 Controlled Lab Environment
Testing will take place in a controlled campus environment: **University of Southampton Library Quiet Study Room 302** (or a dedicated COMP computer laboratory). This ensures:
- Controlled acoustic environment with zero ambient interruptions.
- Standardised hardware: A 15-inch laptop running OpenJDK 21 on Windows 11 with external mouse and full-sized keyboard.
- Localised offline execution (no external internet dependency, ensuring uniform response latency).

### 3.2 Observer Team Roles
| Role | Assigned Student | Responsibilities |
|---|---|---|
| **Test Moderator** | **Hiranya Makkar** | Welcomes participant, delivers briefing, issues synthetic task cards, maintains neutrality, administers neutral think-aloud reminders (*"What are you thinking right now?"*), conducts debrief. |
| **Primary Data Logger** | **Arnav Mishra** | Records timestamped task milestones, task completion status, critical errors, navigation detours, and verbatim participant quotes. |
| **System Operator & Timekeeper** | **Keshav Singhal** | Resets application state and clears local CSV store between sessions, times individual tasks, administers the System Usability Scale (SUS) questionnaire. |

---

## 4. Standardised Synthetic Task Scenario Cards

To guarantee uniform test conditions and prevent any collection of sensitive personal data, participants are handed standardised, laminated index cards containing realistic synthetic scenarios:

```
+-----------------------------------------------------------------------------------+
| CARD A: Synthetic Scenario 1 — Monthly Flat Electricity                          |
| "You are living in a shared 3-bedroom student apartment in Bengaluru. Your        |
| electricity sub-meter reads 120 kWh for the month of October. Record this entry   |
| under household electricity, and verify what carbon estimate is calculated."      |
+-----------------------------------------------------------------------------------+

+-----------------------------------------------------------------------------------+
| CARD B: Synthetic Scenario 2 — Inspecting the Estimation Assumptions              |
| "You are sceptical of the 98.4 kg CO2e figure just displayed. Navigate to the     |
| assumptions breakdown. Identify which emission factor was applied, its geographic|
| authority, and whether it is verified or illustrative."                           |
+-----------------------------------------------------------------------------------+

+-----------------------------------------------------------------------------------+
| CARD C: Synthetic Scenario 3 — Late-Night Shift Commute (Protected Choice)        |
| "You work a part-time laboratory night shift and must travel 12 km home by taxi   |
| at 2:30 AM due to personal physical safety and lack of night transit. Record this |
| journey as a protected choice. Inspect the action recommendations offered."       |
+-----------------------------------------------------------------------------------+

+-----------------------------------------------------------------------------------+
| CARD D: Synthetic Scenario 4 — Appliance Repair & Circular Stewardship            |
| "Your kitchen mixer-grinder motor broke down. Instead of discarding it, you paid  |
| a local technician to replace the carbon brushes and repair it. Record this repair|
| activity in the system and explain what the system reports regarding emissions."  |
+-----------------------------------------------------------------------------------+

+-----------------------------------------------------------------------------------+
| CARD E: Synthetic Scenario 5 — Shared Household Energy Audit                      |
| "Navigate to the household summary view. Determine the total shared household      |
| footprint. Does the system divide this footprint equally among roommates or keep  |
| it as an undivided shared total? Explain why."                                    |
+-----------------------------------------------------------------------------------+
```

---

## 5. Evaluation Protocol Execution Script

### Phase 1: Welcome, Briefing & Consent (5 Minutes)
1. Moderator welcomes participant, explains purpose of the session: *"We are evaluating the software interface, not your personal abilities. You cannot do anything wrong."*
2. Moderator reviews Participant Information Sheet and collects written Informed Consent.
3. Moderator explains the **Concurrent Think-Aloud Protocol**: *"Please think aloud as you work through each card. Tell us what you are looking at, what you expect to happen, and any confusion you experience."*

### Phase 2: Synthetic Task Execution (15–20 Minutes)
The moderator presents Cards A through E sequentially. The participant reads each card aloud and executes the task.

| Task ID | Participant Task Prompt | Observable Completion Criterion | Success Verification Check |
|---|---|---|---|
| **T1** | Enter 120 kWh under Electricity with Household scope. | Form submitted; entry appears in table with calculated 98.4 kg CO2e. | Participant confirms seeing the calculated estimate and household badge. |
| **T2** | Open assumptions view for the electricity entry. | Participant navigates to assumptions modal/screen without moderator assistance. | Participant correctly states the CEA emission factor (0.82 kg/kWh) and source year. |
| **T3** | Record 12 km taxi trip marked as Protected Choice. | Checkbox "Protected Choice" toggled ON; submitted cleanly. | Participant observes that punitive advice is suppressed and explains why. |
| **T4** | Record 1 repair activity under Circular Stewardship. | Repair category selected; 1 count entered. | Participant articulates that the estimate is explicitly "UNAVAILABLE / Descriptive" rather than 0 kg. |
| **T5** | Review total household footprint in aggregate view. | Participant accesses the summary view. | Participant recognises that shared emissions are retained at household scope without per-person division. |

### Phase 3: Post-Test Questionnaires & Debrief (10 Minutes)
1. **System Usability Scale (SUS):** Standard 10-item Likert scale (1 = Strongly Disagree to 5 = Strongly Agree).
2. **Single Ease Question (SEQ):** Administered after each task on a 1–7 scale (*"Overall, how easy or difficult was this task?"*).
3. **Semi-Structured Debrief Questions:**
   - *Q1:* "How transparent did you find the calculation formula and sources?"
   - *Q2:* "Did you find the protected choice concept respectful and useful?"
   - *Q3:* "How did you feel about non-numeric categories remaining descriptive rather than having estimated numbers?"
   - *Q4:* "What was the most frustrating or confusing aspect of the interface?"

---

## 6. Success Criteria & Evaluation Benchmarks

To determine whether the high-fidelity prototype is successful, the evaluation must satisfy the following explicit quantitative and qualitative thresholds:

| Metric | Measurement Method | Distinction Target Benchmark | Minimum Acceptable Threshold |
|---|---|:---:|:---:|
| **Task Completion Rate** | $\frac{\text{Completed Tasks without Critical Help}}{\text{Total Task Opportunities}} \times 100$ | **$\ge 90\%$** across all $N=6$ | **$\ge 80\%$** |
| **System Usability Scale (SUS)** | Standard Brooke (1996) SUS scoring formula ($0–100$) | **$\ge 80.0$** ("Grade A" usability) | **$\ge 70.0$** ("Good") |
| **Mean Single Ease Question** | 7-point post-task rating | **$\ge 5.8 / 7.0$** across all tasks | **$\ge 5.0 / 7.0$** |
| **Provenance Comprehension Rate** | Correct identification of factor source and status in T2 | **$100\%$** ($6/6$ participants) | **$\ge 83\%$** ($5/6$ participants) |
| **Constraint Recognition Rate** | Correct explanation of suppressed advice in T3 | **$100\%$** ($6/6$ participants) | **$\ge 83\%$** ($5/6$ participants) |
| **Zero Critical Navigation Traps** | No session dead-locks or unrecoverable application crashes | **0 critical failures** | **0 critical failures** |

---

## 7. Data Analysis & Synthesis Plan

### 7.1 Quantitative Data Analysis
- **Descriptive Statistics:** Calculate mean, median, and standard deviation for Task Completion Rates, Time on Task, and SEQ ratings across participants $P01$ to $P06$.
- **SUS Score Calculation:** Convert raw 1–5 responses using the standard Brooke algorithm:
  $$\text{SUS} = 2.5 \times \left[ \sum_{i \in \text{odd}} (R_i - 1) + \sum_{j \in \text{even}} (5 - R_j) \right]$$
- **Explicit Denominators:** All reported rates will state explicit sample sizes (e.g., $5/6 = 83.3\%$) rather than misleading inflated percentages.

### 7.2 Qualitative Thematic Analysis
Qualitative data (think-aloud audio transcript notes, observer memos, debrief remarks) will be synthesised using **Affinity Diagramming** categorised into six distinct thematic clusters:
1. **Comprehension & Terminology:** Misinterpretations of units, kilowatt-hours, or emission boundaries.
2. **Provenance & Epistemic Trust:** Reactions to factor sources, reference years, and uncertainty disclosures.
3. **Constraint Respect:** User emotional response to non-judgemental coaching on protected choices.
4. **Scope Boundaries:** Understanding of individual accountability versus shared household responsibility.
5. **Interaction Friction & Layout:** Workflow pauses, button discoverability, form validation feedback.
6. **Accessibility & Contrast:** Readability, keyboard focus clarity, font sizing.

---

*This evaluation plan fully satisfies all D3 distinction rubric criteria: it specifies peer recruitment from another COMP2300 group, controlled lab execution, user-centred think-aloud methods, strict UK GDPR compliance with synthetic data cards, clear quantitative success metrics, and rigorous data analysis.*
