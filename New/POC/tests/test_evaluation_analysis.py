"""Synthetic software test fixtures; these are NOT evaluation participants."""
import csv
import importlib.util
import tempfile
import unittest
from pathlib import Path

spec = importlib.util.spec_from_file_location('evaluation', Path(__file__).resolve().parents[1] / 'tools/analyse_evaluation.py')
module = importlib.util.module_from_spec(spec)
spec.loader.exec_module(module)


class AnalysisTests(unittest.TestCase):
    def run_data(self, rows):
        with tempfile.TemporaryDirectory() as folder:
            path = Path(folder) / 'synthetic.csv'
            with path.open('w', newline='') as file:
                writer = csv.writer(file)
                writer.writerow(module.FIELDS)
                writer.writerows(rows)
            return module.analyse(path)

    def row(self, pid='P01', task='T1', order=1, completion='success', seconds='10', errors='0', help_count='0', correct='2', total='2', critical='no'):
        return [pid, task, order, completion, seconds, errors, help_count, correct, total, critical, 'synthetic unit-test fixture']

    def complete(self, n):
        return [self.row(f'P{i:02}', task, order) for i in range(1,n+1) for order,task in enumerate(module.TASKS,1)]

    def test_empty_is_not_conducted(self):
        self.assertEqual(self.run_data([])['status'], 'not_conducted')
        self.assertFalse(self.run_data([])['all_task_gates_met'])

    def test_complete_three_pass(self):
        self.assertTrue(self.run_data(self.complete(3))['all_task_gates_met'])

    def test_four_requires_four_and_five_requires_four(self):
        for n, expected in [(4,False),(5,True)]:
            rows=self.complete(n)
            rows[0][3]='failed'
            self.assertEqual(self.run_data(rows)['tasks']['T1']['gate_met'], expected)

    def test_critical_issue_always_blocks(self):
        rows=self.complete(5)
        rows[0][9]='yes'
        self.assertFalse(self.run_data(rows)['tasks']['T1']['gate_met'])

    def test_missing_task_blocks(self):
        result=self.run_data(self.complete(3)[1:])
        self.assertEqual(result['tasks']['T1']['missing_task_rows'],1)
        self.assertFalse(result['all_task_gates_met'])

    def test_missing_measurement_not_zero(self):
        rows=self.complete(3)
        rows[0][4]=''
        result=self.run_data(rows)['tasks']['T1']
        self.assertEqual(result['time_observations'],2)
        self.assertEqual(result['incomplete_outcome_rows'],1)
        self.assertFalse(result['gate_met'])

    def test_duplicate_task_rejected(self):
        with self.assertRaises(ValueError): self.run_data([self.row(),self.row()])

    def test_nonfinite_time_rejected(self):
        for value in ('nan','inf','-1'):
            with self.assertRaises(ValueError): self.run_data([self.row(seconds=value)])

    def test_impossible_score_rejected(self):
        with self.assertRaises(ValueError): self.run_data([self.row(correct='3')])

    def test_not_attempted_has_no_measures(self):
        r=self.row(completion='not_attempted',seconds='',errors='',help_count='',correct='',total='',critical='not_observed')
        self.assertEqual(self.run_data([r])['tasks']['T1']['attempted'],0)
        r[4]='0'
        with self.assertRaises(ValueError): self.run_data([r])

    def test_assistance_and_comprehension_matter(self):
        rows=self.complete(3)
        rows[0][6]='1'
        rows[1][7]='1'
        result=self.run_data(rows)
        self.assertFalse(result['tasks']['T1']['gate_met'])
        self.assertFalse(result['tasks']['T2']['gate_met'])

    def test_outside_planned_sample_is_not_automatic_pass(self):
        self.assertFalse(self.run_data(self.complete(6))['all_task_gates_met'])


if __name__ == '__main__':
    unittest.main()
