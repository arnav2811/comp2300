import test from "node:test";
import assert from "node:assert/strict";
import {
  CATEGORY_DATA,
  actionFor,
  calculateEstimate,
  isUnitCompatible,
  validateQuantity
} from "./prototype-logic.mjs";

test("every prototype category has a unit and boundary", () => {
  assert.deepEqual(Object.keys(CATEGORY_DATA), ["electricity", "water", "travel", "food", "packaging", "waste", "repair"]);
  for (const data of Object.values(CATEGORY_DATA)) {
    assert.ok(data.label);
    assert.ok(data.defaultUnit);
    assert.ok(data.boundary);
    assert.equal(typeof data.comparison, "function");
  }
});

test("quantity validation distinguishes blank, invalid, negative and zero", () => {
  assert.equal(validateQuantity("").valid, false);
  assert.equal(validateQuantity("not-a-number").valid, false);
  assert.equal(validateQuantity("-1").valid, false);
  assert.equal(validateQuantity("1000001").valid, false);
  assert.deepEqual(validateQuantity("0"), { valid: true, value: 0 });
  assert.deepEqual(validateQuantity("12.5"), { valid: true, value: 12.5 });
});

test("electricity produces a labelled illustrative estimate", () => {
  const result = calculateEstimate("electricity", 168);
  assert.equal(result.available, true);
  assert.equal(result.display, "120.288 kg CO2");
  assert.match(result.explanation, /168 × 0\.716/);
});

test("unsupported categories remain unavailable rather than becoming zero", () => {
  for (const category of ["water", "travel", "food", "packaging", "waste", "repair"]) {
    const result = calculateEstimate(category, 4);
    assert.equal(result.available, false, category);
    assert.equal(result.display, "Estimate unavailable", category);
  }
});

test("protected choices cannot receive an actionable plan", () => {
  const action = actionFor("travel", true);
  assert.equal(action.available, false);
  assert.match(action.explanation, /protected|Safety/i);
  assert.equal(actionFor("travel", false).available, true);
});

test("category metadata exposes provenance fields for the assumptions route", () => {
  for (const data of Object.values(CATEGORY_DATA)) {
    assert.ok(data.metric);
    assert.ok(data.year);
    assert.ok(data.geography);
    assert.ok(data.comparisonLabel);
  }
});

test("unknown categories and invalid calculation quantities fail closed", () => {
  assert.throws(() => calculateEstimate("unknown", 4), /Unsupported category/);
  assert.throws(() => calculateEstimate("electricity", -1), /finite non-negative/);
});

test("unit compatibility correctly groups kg categories and isolates others", () => {
  assert.equal(isUnitCompatible("food", "packaging"), true);
  assert.equal(isUnitCompatible("food", "waste"), true);
  assert.equal(isUnitCompatible("packaging", "waste"), true);
  assert.equal(isUnitCompatible("electricity", "water"), false);
  assert.equal(isUnitCompatible("electricity", "travel"), false);
  assert.equal(isUnitCompatible("unknown", "food"), false);
});
