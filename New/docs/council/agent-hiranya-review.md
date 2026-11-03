# Agent Hiranya — independent UX, accessibility, localisation and inclusion review

**Status:** FICTIONAL RECONSTRUCTION — AI-created illustrative council review
based on inspected workspace artefacts and static checks. It is not a student
peer assessment or participant finding.

## 1. Scope inspected

Inspected D2 `index.html`, `styles.css`, `app.js`, pure logic, structure check,
interaction inventory and scenarios; JavaFX UI and user guide; D1 traceability,
concept descriptions, naming, simulation and council controls. Static control
extraction and contrast review were performed; no browser, screen reader or
desktop accessibility session was claimed.

## 2. Strengths

- P0–P6 gives a comprehensible end-to-end route.
- P2 is a universal result state rather than silently converting unavailable data
  to zero.
- Household totals are not divided into unsupported individual shares.
- Protected choices avoid punitive scoring and explicit factor provenance is
  visible.
- Native browser controls, labels, visible focus styling and responsive CSS are
  present.
- The product identifies English-only status and does not claim Hindi validation.

## 3. Findings

### H-01 — Browser “record” wording exceeded preview behaviour — High

The prototype says that an unavailable activity remains in a user record even
though the browser prototype saves nothing. The route now clears transient state
when starting another record, but copy must remain consistently “preview” unless
persistence is added.

### H-02 — Action comparison and plan claims need scope discipline — High

Concept text suggests a meaningful alternative comparison, while the browser
action is a generic candidate message and “Choose this plan” does not persist a
plan. Current copy now states that no completed saving or persistent plan is
claimed. A future increment must either implement comparison/persistence or
reduce the claim.

### H-03 — Unit control looked inert — Medium

The disabled select was technically deliberate but visually resembled a broken
control. It is now a read-only semantic `output` whose value follows category.

### H-04 — Accessibility semantics and recovery — High

The journey is now a `nav`, a skip link is present, quantity errors move focus
and expose `aria-errormessage`, the shared diagram has figure/image semantics,
and heading focus has visible styling. Actual assistive-technology validation is
still pending.

### H-05 — Component boundary contrast — Medium

Several original borders were below a 3:1 non-text boundary target. Input,
secondary-button and card borders were darkened. Browser contrast and high-
contrast mode still need manual confirmation.

### H-06 — JavaFX selection and scaling — High

The Actions tab previously displayed a row of buttons that could remain active
without a selected record and could overflow at small widths. It now shows the
selected record, disables inapplicable controls and wraps them in a scrollable
flow layout. Desktop scaling remains unverified.

### H-07 — Localisation and India-specific evidence — High

The implementation is English-only, with hardcoded strings and no reviewed Hindi
resource bundle. The India-specific factor and tariff context remain explicitly
illustrative/pending. No population or language preference is inferred.

### H-08 — Privacy and shared household context — Medium

The local CSV is unencrypted. The guide discloses this, but a future UI should
warn at save time and clarify shared-device expectations. No individual
attribution should be added without consent rules.

## 4. Verification boundaries

Static browser checks and seven pure logic tests pass. No browser version/date,
keyboard-only, screen-reader, 200% text, JavaFX launch or participant session is
represented as completed.

## 5. Priority ranking

1. P0: complete the documented human browser/accessibility and desktop walkthrough.
2. P1: align all “preview”, “record” and “plan” terminology.
3. P1: preserve explicit uncertainty, scope and provenance in every numeric view.
4. P2: centralise strings before claiming bilingual support.
5. P2: add privacy warning and shared-device explanation.

## 6. Confidence and limitations

High confidence applies to static control structure, source wording and the
recorded automated checks. Rendering, screen-reader behaviour, language quality,
affordability and participant experience require human verification.
