import csv
import tempfile
import unittest
from pathlib import Path

from analyse_evaluation import FIELDS, TASKS, analyse


class AnalysisTests(unittest.TestCase):
    def write(self, rows):
        folder = tempfile.TemporaryDirectory()
        path = Path(folder.name) / "observations.csv"
        with path.open("w", newline="", encoding="utf-8") as handle:
            writer = csv.writer(handle)
            writer.writerow(FIELDS)
            writer.writerows(rows)
        return folder, path

    def row(self, participant="P01", task="T1", completion="success", seconds="10",
            errors="0", help_count="0", correct="2", total="2", critical="no"):
        return [participant, task, "1", completion, seconds, errors, help_count,
                correct, total, critical, "synthetic test fixture"]

    def complete(self, count=3):
        return [self.row(f"P{i:02}", task) for i in range(1, count + 1) for task in TASKS]

    def test_empty_template_is_not_conducted(self):
        folder, path = self.write([])
        try:
            result = analyse(path)
            self.assertEqual(result["status"], "not_conducted")
            self.assertFalse(result["all_task_gates_met"])
        finally:
            folder.cleanup()

    def test_complete_fixture_is_descriptive_and_gateable(self):
        folder, path = self.write(self.complete())
        try:
            result = analyse(path)
            self.assertEqual(result["participant_count"], 3)
            self.assertTrue(result["all_task_gates_met"])
        finally:
            folder.cleanup()

    def test_duplicate_task_and_critical_issue_are_rejected(self):
        rows = [self.row(), self.row()]
        folder, path = self.write(rows)
        try:
            with self.assertRaises(ValueError):
                analyse(path)
        finally:
            folder.cleanup()

        rows = self.complete()
        rows[0][9] = "yes"
        folder, path = self.write(rows)
        try:
            self.assertFalse(analyse(path)["all_task_gates_met"])
        finally:
            folder.cleanup()


if __name__ == "__main__":
    unittest.main()
