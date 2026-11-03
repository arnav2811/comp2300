/** Pure decision logic for the Footprint Lens D2 prototype. */

export const CATEGORY_DATA = Object.freeze({
  electricity: Object.freeze({
    label: "Home electricity",
    defaultUnit: "kWh",
    factorValue: 0.716,
    factorLabel: "0.716 kg CO2/kWh",
    metric: "CO2",
    year: "2026 — illustrative",
    geography: "India — applicability pending",
    comparisonLabel: "Variable cost context (not carbon)",
    source: "Illustrative D1 scenario fixture — source, year and geography pending",
    boundary: "Electricity quantity × illustrative factor; no lifecycle claim",
    comparison: quantity => `₹${(quantity * 8).toFixed(2)} variable-cost illustration`,
    action: "Check whether one discretionary appliance hour can be moved or avoided, if that is safe and practical.",
    constraint: "A protected activity remains visible; no punitive score is applied."
  }),
  water: Object.freeze({
    label: "Water use",
    defaultUnit: "litres",
    factorValue: null,
    factorLabel: "No factor",
    metric: "Not available",
    year: "Not available",
    geography: "Not available",
    comparisonLabel: "Volume context (not carbon)",
    source: "No verified factor selected in this design prototype",
    boundary: "Water volume only; buckets are a volume comparison, not carbon savings",
    comparison: quantity => `${(quantity / 15).toFixed(1)} illustrative 15-litre buckets`,
    action: "Compare the amount with a bucket-based routine that fits the household's water access.",
    constraint: "The comparison does not claim carbon savings."
  }),
  travel: Object.freeze({
    label: "Travel",
    defaultUnit: "passenger-km",
    factorValue: null,
    factorLabel: "No factor",
    metric: "Not available",
    year: "Not available",
    geography: "Not available",
    comparisonLabel: "Distance context (not carbon)",
    source: "No verified travel factor selected in this design prototype",
    boundary: "Passenger distance is retained descriptively; mode and occupancy need an explicit sourced boundary",
    comparison: quantity => `${quantity} passenger-km recorded descriptively`,
    action: "Compare a shared or public-transport option only when it is safe, available and affordable.",
    constraint: "Safety-locked journeys are not treated as discretionary failures."
  }),
  food: Object.freeze({
    label: "Food",
    defaultUnit: "kg",
    factorValue: null,
    factorLabel: "No factor",
    metric: "Not available",
    year: "Not available",
    geography: "Not available",
    comparisonLabel: "Descriptive context (not carbon)",
    source: "No verified food factor selected in this design prototype",
    boundary: "Descriptive food record; factor and boundary must be sourced before a number",
    comparison: quantity => `${quantity} kg recorded descriptively`,
    action: "Choose one food-waste or packaging reduction that fits the user's routine; do not moralise diet.",
    constraint: "No numeric carbon estimate is shown without a verified factor."
  }),
  packaging: Object.freeze({
    label: "Delivery packaging",
    defaultUnit: "kg",
    factorValue: null,
    factorLabel: "No factor",
    metric: "Not available",
    year: "Not available",
    geography: "Not available",
    comparisonLabel: "Descriptive context (not carbon)",
    source: "No verified packaging factor selected in this design prototype",
    boundary: "Descriptive packaging record only",
    comparison: quantity => `${quantity} kg recorded descriptively`,
    action: "Record the packaging pattern and consider a lower-packaging option only when available.",
    constraint: "No invented offset or carbon credit is produced."
  }),
  waste: Object.freeze({
    label: "Waste",
    defaultUnit: "kg",
    factorValue: null,
    factorLabel: "No factor",
    metric: "Not available",
    year: "Not available",
    geography: "Not available",
    comparisonLabel: "Descriptive context (not carbon)",
    source: "No verified waste factor selected in this design prototype",
    boundary: "Descriptive waste record only; treatment pathway and factor need an explicit source",
    comparison: quantity => `${quantity} kg recorded descriptively`,
    action: "Record the disposal context and check whether reuse, repair or the available local route is feasible.",
    constraint: "No unsupported avoided-emission credit is produced."
  }),
  repair: Object.freeze({
    label: "Repair or reuse",
    defaultUnit: "count",
    factorValue: null,
    factorLabel: "No factor",
    metric: "Not available",
    year: "Not available",
    geography: "Not available",
    comparisonLabel: "Descriptive context (not carbon)",
    source: "Circular practice is acknowledged descriptively in this prototype",
    boundary: "Count of practice; no unverified avoided-emission credit",
    comparison: quantity => `${quantity} repair/reuse event${quantity === 1 ? "" : "s"} recorded`,
    action: "Keep the repair/reuse record visible as a practice rather than converting it into an unsupported credit.",
    constraint: "No numeric carbon estimate is implied."
  })
});

export const getCategory = category => {
  const data = CATEGORY_DATA[category];
  if (!data) throw new Error(`Unsupported category: ${category}`);
  return data;
};

export function validateQuantity(rawValue) {
  const raw = String(rawValue ?? "").trim();
  if (raw === "") return { valid: false, message: "Enter a quantity before continuing." };
  const value = Number(raw);
  if (!Number.isFinite(value)) return { valid: false, message: "Enter a finite number, such as 4 or 12.5." };
  if (value < 0) return { valid: false, message: "Quantity cannot be negative. Your other entries remain available." };
  if (value > 1000000) return { valid: false, message: "Quantity is outside this prototype's 0–1,000,000 range. Check the unit or enter a smaller record." };
  return { valid: true, value };
}

export function calculateEstimate(category, quantity) {
  if (!Number.isFinite(quantity) || quantity < 0) throw new Error("Quantity must be a finite non-negative number");
  const data = getCategory(category);
  if (data.factorValue === null) {
    return {
      available: false,
      display: "Estimate unavailable",
      metric: null,
      explanation: data.boundary
    };
  }
  const value = quantity * data.factorValue;
  return {
    available: true,
    value,
    display: `${value.toFixed(3)} kg CO2`,
    metric: "CO2",
    explanation: `${quantity} × ${data.factorValue} = ${value.toFixed(3)} kg CO2`
  };
}

export function actionFor(category, protectedChoice) {
  const data = getCategory(category);
  if (protectedChoice) return { available: false, title: "Protected choice", explanation: data.constraint };
  return { available: true, title: "One possible next step", explanation: data.action };
}
