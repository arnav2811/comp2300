import {
  actionFor,
  calculateEstimate,
  getCategory,
  validateQuantity
} from "./prototype-logic.mjs";

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
  state.category = $("category").value;
  const data = selectedData();
  $("unit").value = data.defaultUnit;
  $("unit-label").textContent = data.defaultUnit;
  state.unit = data.defaultUnit;
  $("category-boundary").textContent = data.factorValue === null
    ? "This category is retained descriptively in the low-fidelity design."
    : "This category has an illustrative factor only; inspect its assumptions.";
}

function currentScopeLabel() {
  return state.scope === "household" ? "Household activity" : "Personal activity";
}

function renderResult() {
  const data = selectedData();
  const estimate = calculateEstimate(state.category, state.quantity);
  const protectedLabel = state.protectedChoice ? "Protected / necessary" : "Open to action review";
  $("result-title").textContent = `${data.label} · ${state.quantity} ${state.unit}`;
  $("result-card").innerHTML = `
    <div class="metric"><strong>${estimate.display}</strong><span>${estimate.available ? "illustrative result" : "quantity retained without false precision"}</span></div>
    <p>${estimate.explanation}. The result is a design fixture, not verified India-specific emissions data.</p>
    <dl class="meta-grid">
      <div><dt>Scope</dt><dd>${currentScopeLabel()}</dd></div>
      <div><dt>Choice status</dt><dd>${protectedLabel}</dd></div>
      <div><dt>Familiar context</dt><dd>${data.comparison(state.quantity)}</dd></div>
    </dl>
    <p class="route-hint">Choose an option below to inspect the evidence, understand the unknown state, or consider a realistic action.</p>`;
  $("unavailable-button").classList.toggle("hidden", estimate.available);
}

function renderAssumptions() {
  const data = selectedData();
  const estimate = calculateEstimate(state.category, state.quantity);
  const factor = data.factorValue === null ? "None configured" : data.factorLabel;
  const formula = estimate.available ? estimate.explanation : "No numeric result is produced";
  $("assumption-table").innerHTML = `
    <table><caption class="sr-only">Estimate assumptions</caption><thead><tr><th>Input</th><th>Current value</th><th>Evidence status</th></tr></thead><tbody>
      <tr><th>Activity</th><td>${data.label}</td><td>User selected</td></tr>
      <tr><th>Quantity</th><td>${state.quantity} ${state.unit}</td><td>User input; not independently verified</td></tr>
      <tr><th>Scope</th><td>${currentScopeLabel()}</td><td>Stored as a boundary, not individual attribution</td></tr>
      <tr><th>Factor</th><td>${factor}</td><td>${data.source}</td></tr>
      <tr><th>Formula</th><td>${formula}</td><td>${data.boundary}</td></tr>
    </tbody></table>`;
}

function renderAction() {
  const action = actionFor(state.category, state.protectedChoice);
  $("action-card").innerHTML = `<h3>${action.title}</h3><p>${action.explanation}</p>
    <p class="decision-boundary"><strong>Decision boundary:</strong> this is a candidate plan, not evidence that emissions will fall.</p>`;
  $("action-feedback").textContent = state.actionStatus;
  const disabled = !action.available;
  ["defer-button", "dismiss-button", "choose-button"].forEach(id => $(id).disabled = disabled);
}

function renderUnavailable() {
  const data = selectedData();
  $("unavailable-category").textContent = data.label;
  $("unavailable-reason").textContent = `${data.boundary}. The activity remains useful as a descriptive record.`;
}

function renderShared() {
  $("shared-total").textContent = state.quantity === null ? "Not calculated" : `${state.quantity} ${state.unit} recorded`;
  $("shared-scope").textContent = currentScopeLabel();
}

function validateAndContinue() {
  const validation = validateQuantity($("quantity").value);
  $("validation").textContent = validation.message;
  $("quantity").setAttribute("aria-invalid", String(!validation.valid));
  if (!validation.valid) return;
  state.quantity = validation.value;
  state.unit = $("unit").value;
  state.actionStatus = "";
  renderResult();
  show("result", "Record preview ready. No data has been saved.");
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
  syncCategory();
  show("entry", "Prototype reset. No data was saved.");
}

$("category").addEventListener("change", syncCategory);
$("scope").addEventListener("change", () => { state.scope = $("scope").value; });
$("protected").addEventListener("change", () => { state.protectedChoice = $("protected").checked; });
$("begin-button").addEventListener("click", () => show("quantity", "Describe the known quantity; unknown is different from zero."));
$("example-button").addEventListener("click", () => {
  $("category").value = "electricity";
  $("scope").value = "household";
  $("protected").checked = false;
  $("quantity").value = "168";
  state.scope = "household";
  state.protectedChoice = false;
  syncCategory();
  show("quantity", "Illustrative example loaded: 168 kWh household electricity. Review the assumptions before continuing.");
});
$("shared-button").addEventListener("click", () => { renderShared(); show("shared", "Shared activity is visible without assigning it to a person."); });
$("save-button").addEventListener("click", validateAndContinue);
$("reset-button").addEventListener("click", reset);
$("unavailable-button").addEventListener("click", () => { renderUnavailable(); show("unavailable", "No defensible factor is configured for this category."); });

document.querySelectorAll("[data-go]").forEach(button => button.addEventListener("click", () => {
  const next = button.dataset.go;
  if (next === "assumptions") renderAssumptions();
  if (next === "action") renderAction();
  if (next === "unavailable") renderUnavailable();
  if (next === "shared") renderShared();
  show(next);
}));

document.querySelectorAll("[data-back]").forEach(button => button.addEventListener("click", () => {
  const next = button.dataset.back;
  if (next === "result") renderResult();
  if (next === "action") renderAction();
  show(next);
}));

$("defer-button").addEventListener("click", () => { state.actionStatus = "Deferred. No plan was recorded in this prototype."; renderAction(); });
$("dismiss-button").addEventListener("click", () => { state.actionStatus = "Marked not feasible. The activity remains visible and no penalty was applied."; renderAction(); });
$("choose-button").addEventListener("click", () => { state.actionStatus = "Candidate plan chosen for this walkthrough; no completed saving is claimed."; renderAction(); });

document.querySelectorAll("#journey-list button").forEach(button => button.addEventListener("click", () => {
  const requested = button.closest("li").dataset.state;
  if (requested === "entry") { show("entry"); return; }
  if (state.quantity === null) {
    show("entry", "Start a record and enter a quantity before opening this stage.");
    return;
  }
  if (requested === "quantity") { show("quantity"); return; }
  if (requested === "result") { renderResult(); show("result"); return; }
  if (requested === "assumptions") { renderAssumptions(); show("assumptions"); return; }
  if (requested === "action") { renderAction(); show("action"); return; }
  if (requested === "unavailable") {
    if (calculateEstimate(state.category, state.quantity).available) {
      show("result", "This record has an illustrative estimate; the unavailable-estimate branch is shown for unsupported categories.");
      return;
    }
    renderUnavailable(); show("unavailable"); return;
  }
  if (requested === "shared") { renderShared(); show("shared"); }
}));

syncCategory();
show("entry");
