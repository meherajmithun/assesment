package com.kbs.fitnessreport

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class ReportActivity : Activity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppTheme.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        val durations = readDurationsFromIntent()
        if (durations == null) {
            Toast.makeText(this, R.string.report_data_missing, Toast.LENGTH_LONG).show()
            finish()
            return
        }

        renderReport(WorkoutEvaluator.evaluate(durations))
        findViewById<Button>(R.id.editButton).setOnClickListener { finish() }
    }

    private fun readDurationsFromIntent(): WorkoutDurations? {
        val running = intent.getIntExtra(MainActivity.EXTRA_RUNNING, MISSING_VALUE)
        val cycling = intent.getIntExtra(MainActivity.EXTRA_CYCLING, MISSING_VALUE)
        val swimming = intent.getIntExtra(MainActivity.EXTRA_SWIMMING, MISSING_VALUE)
        val weightlifting = intent.getIntExtra(
            MainActivity.EXTRA_WEIGHTLIFTING,
            MISSING_VALUE,
        )

        if (listOf(running, cycling, swimming, weightlifting).any { it < 0 }) {
            return null
        }
        return WorkoutDurations(running, cycling, swimming, weightlifting)
    }

    private fun renderReport(report: WorkoutReport) {
        findViewById<TextView>(R.id.totalMinutesText).text =
            getString(R.string.total_progress_value, report.total.actualMinutes)

        val overallStatus = findViewById<TextView>(R.id.overallStatusText)
        if (report.total.achieved) {
            overallStatus.setText(R.string.overall_reached)
            overallStatus.setBackgroundResource(R.drawable.hero_status_success)
            overallStatus.setTextColor(getColor(R.color.hero_success))
        } else {
            overallStatus.text = resources.getQuantityString(
                R.plurals.overall_remaining,
                report.total.remainingMinutes,
                report.total.remainingMinutes,
            )
            overallStatus.setBackgroundResource(R.drawable.hero_status_warning)
            overallStatus.setTextColor(getColor(R.color.hero_warning))
        }

        findViewById<TextView>(R.id.summaryText).setText(
            if (report.allTargetsAchieved) {
                R.string.all_targets_reached
            } else {
                R.string.some_targets_remaining
            },
        )

        renderWorkout(
            report.running,
            findViewById(R.id.runningResult),
            findViewById(R.id.runningMinutesText),
            findViewById(R.id.runningStatusText),
        )
        renderWorkout(
            report.cycling,
            findViewById(R.id.cyclingResult),
            findViewById(R.id.cyclingMinutesText),
            findViewById(R.id.cyclingStatusText),
        )
        renderWorkout(
            report.swimming,
            findViewById(R.id.swimmingResult),
            findViewById(R.id.swimmingMinutesText),
            findViewById(R.id.swimmingStatusText),
        )
        renderWorkout(
            report.weightlifting,
            findViewById(R.id.weightliftingResult),
            findViewById(R.id.weightliftingMinutesText),
            findViewById(R.id.weightliftingStatusText),
        )
    }

    private fun renderWorkout(
        result: TargetResult,
        container: LinearLayout,
        minutesText: TextView,
        statusText: TextView,
    ) {
        minutesText.text = getString(
            R.string.actual_and_target,
            result.actualMinutes,
            result.targetMinutes,
        )

        if (result.achieved) {
            container.setBackgroundResource(R.drawable.status_success)
            statusText.setText(R.string.target_reached)
            statusText.setTextColor(getColor(R.color.success))
        } else {
            container.setBackgroundResource(R.drawable.status_warning)
            statusText.text = getString(R.string.target_remaining, result.remainingMinutes)
            statusText.setTextColor(getColor(R.color.warning))
        }
    }

    companion object {
        private const val MISSING_VALUE = -1
    }
}
