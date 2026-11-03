"""Static contract check for the browser prototype when no browser driver is available."""

from pathlib import Path
import re

ROOT = Path(__file__).parent
html = (ROOT / "index.html").read_text(encoding="utf-8")
js = (ROOT / "app.js").read_text(encoding="utf-8")
logic = (ROOT / "prototype-logic.mjs").read_text(encoding="utf-8")

required_ids = {
    "category", "scope", "protected", "quantity", "unit", "unit-label",
    "validation", "result-card", "assumption-table", "action-card",
    "unavailable-category", "unavailable-reason", "shared-total", "shared-scope",
    "example-button", "unavailable-button", "live-announcement"
}
found_ids = set(re.findall(r'id="([^"]+)"', html))
missing_ids = required_ids - found_ids
assert not missing_ids, f"Missing HTML contract ids: {sorted(missing_ids)}"

for panel in ("entry", "quantity", "result", "assumptions", "action", "unavailable", "shared"):
    assert f'data-panel="{panel}"' in html, f"Missing journey panel: {panel}"

for handler in ("validateAndContinue", "renderAssumptions", "renderAction", "renderUnavailable", "renderShared", "reset"):
    assert handler in js, f"Missing interaction handler: {handler}"

for export in ("CATEGORY_DATA", "validateQuantity", "calculateEstimate", "actionFor"):
    assert export in logic, f"Missing pure logic export: {export}"

print("Footprint Lens prototype structure: PASS (7 panels, required controls and handlers present)")
