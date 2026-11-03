import test from "node:test";
import assert from "node:assert/strict";
import {
  CATEGORY_DATA,
  actionFor,
  calculateEstimate,
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
