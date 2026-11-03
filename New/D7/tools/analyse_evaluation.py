#!/usr/bin/env python3
"""Analyse de-identified Footprint Lens task observations.

This utility deliberately refuses to invent missing observations. It is suitable
for the real D7 CSV after the team has conducted the approved study; the checked
in template produces a not_conducted report.
"""

from __future__ import annotations

import argparse
import csv
import json
import math
from collections import defaultdict
from pathlib import Path
from statistics import median

TASKS = ("T1", "T2", "T3", "T4", "T5")
FIELDS = (
    "participant_code", "task", "order", "completion", "seconds", "errors",
    "help_count", "correct", "total", "critical", "observation",
)
COMPLETIONS = {"success", "partial", "failed", "not_attempted"}
CRITICAL = {"yes", "no", "not_observed"}


def _number(row: dict[str, str], name: str, *, integer: bool = False, allow_blank: bool = False):
    value = row[name].strip()
    if not value and allow_blank:
        return None
    try:
        parsed = int(value) if integer else float(value)
    except ValueError as exc:
        raise ValueError(f"{name} must be numeric") from exc
    if not math.isfinite(parsed) or parsed < 0:
        raise ValueError(f"{name} must be finite and non-negative")
    return parsed


def _validate(row: dict[str, str], line: int) -> None:
    missing = [field for field in FIELDS if field not in row]
    if missing:
        raise ValueError(f"line {line}: missing fields {', '.join(missing)}")
    if not row["participant_code"].strip():
        raise ValueError(f"line {line}: participant code is required")
    if row["task"] not in TASKS:
        raise ValueError(f"line {line}: task must be one of {TASKS}")
    if row["completion"] not in COMPLETIONS:
        raise ValueError(f"line {line}: unsupported completion value")
    if row["critical"] not in CRITICAL:
        raise ValueError(f"line {line}: unsupported critical value")

    not_attempted = row["completion"] == "not_attempted"
    for field in ("seconds", "errors", "help_count", "correct", "total"):
        _number(row, field, integer=field in {"errors", "help_count", "correct", "total"},
                allow_blank=not_attempted)
    if not_attempted:
        if row["critical"] != "not_observed":
            raise ValueError(f"line {line}: not-attempted row must use not_observed critical value")
        return
    if row["correct"].strip() == "" or row["total"].strip() == "":
        raise ValueError(f"line {line}: attempted row needs a comprehension score")
    correct = int(row["correct"])
    total = int(row["total"])
    if total == 0 or correct > total:
        raise ValueError(f"line {line}: comprehension score is impossible")


def analyse(path: Path, required_pass: int | None = None) -> dict:
    with path.open(newline="", encoding="utf-8-sig") as handle:
        reader = csv.DictReader(handle)
        if reader.fieldnames != list(FIELDS):
            raise ValueError(f"CSV header must be exactly: {','.join(FIELDS)}")
        rows = []
        seen = set()
        for line, row in enumerate(reader, 2):
            _validate(row, line)
            key = (row["participant_code"], row["task"])
            if key in seen:
                raise ValueError(f"line {line}: duplicate participant/task row")
            seen.add(key)
            rows.append(row)

    participants = sorted({row["participant_code"] for row in rows})
    if not rows:
        return {
            "status": "not_conducted",
            "participants": [],
            "participant_count": 0,
            "observation_count": 0,
            "tasks": {task: _empty_task() for task in TASKS},
            "all_task_gates_met": False,
            "interpretation": "No participant observations supplied; no evaluation finding is reported.",
        }

    by_task = defaultdict(list)
    for row in rows:
        by_task[row["task"]].append(row)
    report = {
        "status": "conducted_data_ready",
        "participants": participants,
        "participant_count": len(participants),
        "observation_count": len(rows),
        "tasks": {},
        "all_task_gates_met": True,
        "interpretation": "Descriptive convenience-sample evidence only; no causal carbon-reduction or population claim.",
    }
    for task in TASKS:
        result = _task_summary(by_task[task], len(participants), required_pass)
        report["tasks"][task] = result
        report["all_task_gates_met"] &= result["gate_met"]
    return report


def _empty_task() -> dict:
    return {
        "enrolled_denominator": 0,
        "recorded_rows": 0,
        "attempted": 0,
        "missing_task_rows": 0,
        "incomplete_outcome_rows": 0,
        "criterion_pass_count": 0,
        "required_pass_count": None,
        "critical_issue_count": 0,
        "time_observations": 0,
        "time_median_seconds": None,
        "time_min_seconds": None,
        "time_max_seconds": None,
        "gate_met": False,
    }


def _task_summary(rows: list[dict[str, str]], denominator: int, required_pass: int | None) -> dict:
    attempted = [row for row in rows if row["completion"] != "not_attempted"]
    successful = [row for row in attempted if row["completion"] == "success"]
    times = [float(row["seconds"]) for row in attempted if row["seconds"].strip()]
    critical = sum(row["critical"] == "yes" for row in rows)
    default_required = denominator if required_pass is None else required_pass
    incomplete = sum(
        row["completion"] != "not_attempted"
        and (not row["seconds"].strip() or not row["correct"].strip() or not row["total"].strip())
        for row in rows
    )
    passed = sum(int(row["correct"]) == int(row["total"]) and row["completion"] == "success" for row in attempted)
    gate = bool(
        len(rows) == denominator
        and len(attempted) == denominator
        and incomplete == 0
        and critical == 0
        and passed >= default_required
    )
    return {
        "enrolled_denominator": denominator,
        "recorded_rows": len(rows),
        "attempted": len(attempted),
        "missing_task_rows": denominator - len(rows),
        "incomplete_outcome_rows": incomplete,
        "criterion_pass_count": passed,
        "required_pass_count": default_required,
        "critical_issue_count": critical,
        "time_observations": len(times),
        "time_median_seconds": median(times) if times else None,
        "time_min_seconds": min(times) if times else None,
        "time_max_seconds": max(times) if times else None,
        "gate_met": gate,
    }


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("csv_path", type=Path)
    parser.add_argument("--output", type=Path)
    parser.add_argument("--required-pass", type=int, default=None,
                        help="pre-registered minimum successful comprehension outcomes per task")
    args = parser.parse_args()
    result = analyse(args.csv_path, args.required_pass)
    output = json.dumps(result, indent=2) + "\n"
    if args.output:
        args.output.write_text(output, encoding="utf-8")
    else:
        print(output, end="")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
