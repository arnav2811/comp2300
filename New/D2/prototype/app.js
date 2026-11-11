import {
  actionFor,
  calculateEstimate,
  getCategory,
  validateQuantity
} from "./prototype-logic.mjs";

const CATEGORY_PLACEHOLDERS = {
  electricity: "e.g. 150",
  water: "e.g. 120",
  travel: "e.g. 25",
  food: "e.g. 5",
  packaging: "e.g. 3",
  waste: "e.g. 4",
  repair: "e.g. 2"
};

const state = {
  panel: "entry",
  category: "electricity",
  scope: "personal",
  protectedChoice: false,
  quantity: null,
  unit: "kWh",
  actionStatus: "",
  previousPanel: "entry"
};

const $ = id => document.getElementById(id);
const panels = [...document.querySelectorAll("[data-panel]")];

function setNotice(message = "") {
  $("notice").textContent = message;
}

function announce(message) {
  setNotice(message);
  $("live-announcement").textContent = message;
}

function show(panel, message = "") {
  const previous = state.panel;
  state.panel = panel;
  state.previousPanel = previous;
  panels.forEach(item => item.classList.toggle("hidden", item.dataset.panel !== panel));
  document.querySelectorAll("#journey-list li").forEach(item => {
    const active = item.dataset.state === panel;
    item.classList.toggle("active", active);
    item.setAttribute("aria-current", active ? "step" : "false");
    const btn = item.querySelector("button");
    if (btn) {
      btn.setAttribute("aria-current", active ? "step" : "false");
    }
  });
  if (message) announce(message); else setNotice("");
  const focusTarget = document.querySelector(`[data-panel="${panel}"] h2`);
  if (focusTarget) {
    focusTarget.setAttribute("tabindex", "-1");
    focusTarget.focus({ preventScroll: true });
  }
  window.scrollTo({ top: 0, behavior: window.matchMedia("(prefers-reduced-motion: reduce)").matches ? "auto" : "smooth" });
}

function selectedData() {
  return getCategory(state.category);
}

function syncCategory() {
  const previousCategory = state.category;
  const newCategory = $("category").value;
  state.category = newCategory;
  const data = selectedData();
  const previousUnit = state.unit;
  const newUnit = data.defaultUnit;

  // Only clear the quantity if the units are actually incompatible
  if (previousUnit !== newUnit && $("quantity").value !== "") {
    $("quantity").value = "";
    state.quantity = null;
    $("validation").textContent = "";
    $("quantity").removeAttribute("aria-invalid");
    $("quantity").removeAttribute("aria-errormessage");
  }

  $("unit").textContent = newUnit;
  $("unit-label").textContent = newUnit;
  state.unit = newUnit;

  if (CATEGORY_PLACEHOLDERS[newCategory]) {
    $("quantity").placeholder = CATEGORY_PLACEHOLDERS[newCategory];
  }

  $("category-boundary").textContent = data.factorValue === null
    ? "This category is retained descriptively in the low-fidelity design."
    : "This category has an illustrative factor only; inspect its assumptions.";
}

function currentScopeLabel() {
  return state.scope === "household" ? "Household activity" : "Personal activity";
}

function renderResult() {
  const data = selectedData();
  const hasQuantity = state.quantity !== null && Number.isFinite(state.quantity);
  const estimate = hasQuantity ? calculateEstimate(state.category, state.quantity) : {
    available: false,
    display: "Estimate unavailable",
    explanation: "No quantity entered"
  };
  const protectedLabel = state.protectedChoice ? "Protected / necessary" : "Open to action review";
  const qtyStr = hasQuantity ? `${state.quantity} ${state.unit}` : `0 ${state.unit}`;

  $("result-title").textContent = `${data.label} · ${qtyStr}`;
  $("result-card").innerHTML = `
    <div class="metric"><strong>${estimate.display}</strong><span>${estimate.available ? "illustrative result" : "quantity retained without false precision"}</span></div>
    <p>${estimate.explanation}. The result is a design fixture, not verified India-specific emissions data.</p>
    <dl class="meta-grid">
      <div><dt>Scope</dt><dd>${currentScopeLabel()}</dd></div>
      <div><dt>Choice status</dt><dd>${protectedLabel}</dd></div>
      <div><dt>Familiar context</dt><dd>${data.comparisonLabel}: ${hasQuantity ? data.comparison(state.quantity) : "N/A"}</dd></div>
    </dl>
    <p class="route-hint">Choose an option below to inspect the evidence, understand the unknown state, or consider a realistic action.</p>`;
  $("unavailable-button").classList.toggle("hidden", estimate.available);
}

function renderAssumptions() {
  const data = selectedData();
  const hasQuantity = state.quantity !== null && Number.isFinite(state.quantity);
  const estimate = hasQuantity ? calculateEstimate(state.category, state.quantity) : { available: false, explanation: "Enter a quantity to calculate" };
  const factor = data.factorValue === null ? "None configured" : data.factorLabel;
  const formula = estimate.available ? estimate.explanation : (data.factorValue === null ? data.boundary : "No quantity entered yet");
  const qtyDisplay = hasQuantity ? `${state.quantity} ${state.unit}` : `Not entered (default: ${state.unit})`;

  $("assumption-table").innerHTML = `
    <table><caption class="sr-only">Estimate assumptions</caption><thead><tr><th>Input</th><th>Current value</th><th>Evidence status</th></tr></thead><tbody>
      <tr><th>Activity</th><td>${data.label}</td><td>User selected</td></tr>
      <tr><th>Quantity</th><td>${qtyDisplay}</td><td>User input; not independently verified</td></tr>
      <tr><th>Scope</th><td>${currentScopeLabel()}</td><td>Stored as a boundary, not individual attribution</td></tr>
      <tr><th>Factor</th><td>${factor}</td><td>${data.source}</td></tr>
      <tr><th>Metric</th><td>${data.metric}</td><td>Must match the factor's stated boundary</td></tr>
      <tr><th>Reference year</th><td>${data.year}</td><td>Required before a factor can be verified</td></tr>
      <tr><th>Geography</th><td>${data.geography}</td><td>Applicability remains a review question</td></tr>
      <tr><th>Formula</th><td>${formula}</td><td>${data.boundary}</td></tr>
    </tbody></table>`;
}

function renderAction() {
  const action = actionFor(state.category, state.protectedChoice);
  $("action-card").innerHTML = `<h3>${action.title}</h3><p>${action.explanation}</p>
    <p class="decision-boundary"><strong>Decision boundary:</strong> this is a candidate plan, not evidence that emissions will fall.</p>`;
  const disabled = !action.available;
  ["defer-button", "dismiss-button", "choose-button"].forEach(id => $(id).disabled = disabled);
  if (disabled) {
    state.actionStatus = "Action decisions are disabled because this activity is marked protected or necessary.";
  }
  $("action-feedback").textContent = state.actionStatus;
}

function renderUnavailable() {
  const data = selectedData();
  $("unavailable-category").textContent = data.label;
  $("unavailable-reason").textContent = `${data.boundary}. The activity remains useful as a descriptive preview, but this browser artefact does not save it.`;
}

function renderShared() {
  const isHousehold = state.scope === "household";
  $("shared-heading").textContent = isHousehold ? "Household total" : "Shared-total preview";
  $("shared-total").textContent = state.quantity === null
    ? "Not calculated"
    : isHousehold ? `${state.quantity} ${state.unit} recorded` : "Not available from a personal record";
  $("shared-scope").textContent = currentScopeLabel();

  const backBtn = $("shared-back-button");
  if (backBtn) {
    backBtn.classList.toggle("hidden", state.quantity === null);
  }
}

function validateAndContinue() {
  const validation = validateQuantity($("quantity").value);
  $("validation").textContent = validation.valid ? "" : (validation.message || "");
  $("quantity").setAttribute("aria-invalid", String(!validation.valid));
  if (!validation.valid) {
    $("quantity").setAttribute("aria-errormessage", "validation");
    $("quantity").focus();
    return;
  }
  $("quantity").removeAttribute("aria-errormessage");
  state.quantity = validation.value;
  state.unit = $("unit").textContent;
  state.actionStatus = "";
  renderResult();
  show("result", "Record preview ready. No data has been saved.");
}

function startNewRecord() {
  state.quantity = null;
  state.actionStatus = "";
  $("quantity").value = "";
  $("validation").textContent = "";
  $("quantity").removeAttribute("aria-invalid");
  $("quantity").removeAttribute("aria-errormessage");
  show("entry", "Ready for a new activity. No data was saved.");
}

function reset() {
  state.panel = "entry";
  state.category = "electricity";
  state.scope = "personal";
  state.protectedChoice = false;
  state.quantity = null;
  state.unit = "kWh";
  state.actionStatus = "";
  $("category").value = "electricity";
  $("scope").value = "personal";
  $("protected").checked = false;
  $("quantity").value = "";
  $("validation").textContent = "";
  $("quantity").removeAttribute("aria-invalid");
  $("quantity").removeAttribute("aria-errormessage");
  syncCategory();
  show("entry", "Prototype reset. No data was saved.");
}

$("category").addEventListener("change", syncCategory);
$("scope").addEventListener("change", () => { state.scope = $("scope").value; });
$("protected").addEventListener("change", () => {
  state.protectedChoice = $("protected").checked;
  state.actionStatus = "";
});

$("begin-button").addEventListener("click", () => show("quantity", "Describe the known quantity; unknown is different from zero."));

$("example-button").addEventListener("click", () => {
  $("category").value = "electricity";
  $("scope").value = "household";
  $("protected").checked = false;
  state.scope = "household";
  state.protectedChoice = false;
  syncCategory();
  $("quantity").value = "168";
  state.quantity = 168;
  state.actionStatus = "";
  $("validation").textContent = "";
  $("quantity").removeAttribute("aria-invalid");
  $("quantity").removeAttribute("aria-errormessage");
  show("quantity", "Illustrative example loaded: 168 kWh household electricity. Review the assumptions before continuing.");
});

$("shared-button").addEventListener("click", () => {
  renderShared();
  show("shared", "Shared activity is visible without assigning it to a person.");
});

$("save-button").addEventListener("click", validateAndContinue);
$("reset-button").addEventListener("click", reset);
$("unavailable-button").addEventListener("click", () => {
  renderUnavailable();
  show("unavailable", "No defensible factor is configured for this category.");
});

$("quantity").addEventListener("keydown", event => {
  if (event.key === "Enter") {
    event.preventDefault();
    validateAndContinue();
  }
});

document.querySelectorAll("[data-go]").forEach(button => button.addEventListener("click", () => {
  const next = button.dataset.go;
  if (next === "entry") {
    startNewRecord();
    return;
  }
  if (next === "quantity") {
    show("quantity");
    return;
  }
  if (next === "result") renderResult();
  if (next === "assumptions") renderAssumptions();
  if (next === "action") renderAction();
  if (next === "unavailable") renderUnavailable();
  if (next === "shared") renderShared();
  show(next);
}));

document.querySelectorAll("[data-back]").forEach(button => button.addEventListener("click", () => {
  const next = button.dataset.back;
  if (next === "entry") {
    show("entry");
    return;
  }
  if (next === "quantity") {
    show("quantity");
    return;
  }
  if (next === "result") {
    if (state.quantity === null) {
      show("entry");
      return;
    }
    renderResult();
    show("result");
    return;
  }
  if (next === "action") renderAction();
  if (next === "shared") renderShared();
  show(next);
}));

$("defer-button").addEventListener("click", () => {
  state.actionStatus = "Deferred. No plan was recorded in this prototype.";
  renderAction();
});

$("dismiss-button").addEventListener("click", () => {
  state.actionStatus = "Marked not feasible. The activity remains visible and no penalty was applied.";
  renderAction();
});

$("choose-button").addEventListener("click", () => {
  state.actionStatus = "Candidate plan chosen for this walkthrough; no completed saving is claimed.";
  renderAction();
});

document.querySelectorAll("#journey-list button").forEach(button => button.addEventListener("click", () => {
  const requested = button.closest("li").dataset.state;

  if (requested === "entry") {
    show("entry");
    return;
  }

  if (requested === "quantity") {
    show("quantity", "Describe the known quantity; unknown is different from zero.");
    return;
  }

  if (requested === "shared") {
    renderShared();
    show("shared", "Shared activity is visible without assigning it to a person.");
    return;
  }

  // To view result, assumptions, action, or unavailable:
  if (state.quantity === null) {
    const rawVal = $("quantity").value.trim();
    if (rawVal !== "") {
      const validation = validateQuantity(rawVal);
      if (validation.valid) {
        state.quantity = validation.value;
        state.unit = $("unit").textContent;
        $("validation").textContent = "";
        $("quantity").removeAttribute("aria-invalid");
        $("quantity").removeAttribute("aria-errormessage");
      } else {
        $("validation").textContent = validation.message || "";
        $("quantity").setAttribute("aria-invalid", "true");
        $("quantity").setAttribute("aria-errormessage", "validation");
        show("quantity", "Enter a valid quantity before moving forward.");
        $("quantity").focus();
        return;
      }
    } else {
      show("quantity", "Please enter a quantity on this step before viewing results.");
      $("quantity").focus();
      return;
    }
  }

  if (requested === "result") {
    renderResult();
    show("result");
    return;
  }

  if (requested === "assumptions") {
    renderAssumptions();
    show("assumptions");
    return;
  }

  if (requested === "action") {
    renderAction();
    show("action");
    return;
  }

  if (requested === "unavailable") {
    if (calculateEstimate(state.category, state.quantity).available) {
      show("result", "This record has an illustrative estimate; the unavailable-estimate branch is shown for unsupported categories.");
      return;
    }
    renderUnavailable();
    show("unavailable");
    return;
  }
}));

syncCategory();
show("entry");
