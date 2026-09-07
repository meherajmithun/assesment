package com.kbs.fitnessreport

/** The minutes entered on the first activity. */
data class WorkoutDurations(
    val running: Int,
    val cycling: Int,
    val swimming: Int,
    val weightlifting: Int,
) {
    val total: Int
        get() = running + cycling + swimming + weightlifting
}

/** The result of comparing one workout duration with its target. */
data class TargetResult(
    val actualMinutes: Int,
    val targetMinutes: Int,
) {
    val achieved: Boolean
        get() = actualMinutes >= targetMinutes

    val remainingMinutes: Int
        get() = (targetMinutes - actualMinutes).coerceAtLeast(0)
}

/** A complete report used by the second activity. */
data class WorkoutReport(
    val running: TargetResult,
    val cycling: TargetResult,
    val swimming: TargetResult,
    val weightlifting: TargetResult,
    val total: TargetResult,
) {
    val allTargetsAchieved: Boolean
        get() = running.achieved &&
            cycling.achieved &&
            swimming.achieved &&
            weightlifting.achieved &&
            total.achieved
}

/** Keeps the assessment rules in one testable location. */
object WorkoutEvaluator {
    const val RUNNING_TARGET = 60
    const val CYCLING_TARGET = 30
    const val SWIMMING_TARGET = 30
    const val WEIGHTLIFTING_TARGET = 10
    const val TOTAL_TARGET = 150

    fun evaluate(durations: WorkoutDurations): WorkoutReport = WorkoutReport(
        running = TargetResult(durations.running, RUNNING_TARGET),
        cycling = TargetResult(durations.cycling, CYCLING_TARGET),
        swimming = TargetResult(durations.swimming, SWIMMING_TARGET),
        weightlifting = TargetResult(durations.weightlifting, WEIGHTLIFTING_TARGET),
        total = TargetResult(durations.total, TOTAL_TARGET),
    )
}
