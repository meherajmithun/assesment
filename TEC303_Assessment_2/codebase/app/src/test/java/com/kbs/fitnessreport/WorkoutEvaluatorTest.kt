package com.kbs.fitnessreport

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class WorkoutEvaluatorTest {
    @Test
    fun boundaryValuesAreAchieved() {
        val report = WorkoutEvaluator.evaluate(
            WorkoutDurations(running = 60, cycling = 30, swimming = 30, weightlifting = 30),
        )

        assertEquals(150, report.total.actualMinutes)
        assertTrue(report.running.achieved)
        assertTrue(report.cycling.achieved)
        assertTrue(report.swimming.achieved)
        assertTrue(report.weightlifting.achieved)
        assertTrue(report.total.achieved)
    }

    @Test
    fun valuesBelowEveryTargetAreNotAchieved() {
        val report = WorkoutEvaluator.evaluate(
            WorkoutDurations(running = 50, cycling = 20, swimming = 20, weightlifting = 5),
        )

        assertFalse(report.running.achieved)
        assertFalse(report.cycling.achieved)
        assertFalse(report.swimming.achieved)
        assertFalse(report.weightlifting.achieved)
        assertFalse(report.total.achieved)
    }

    @Test
    fun mixedValuesProduceCorrectStatuses() {
        val report = WorkoutEvaluator.evaluate(
            WorkoutDurations(running = 70, cycling = 20, swimming = 35, weightlifting = 15),
        )

        assertEquals(140, report.total.actualMinutes)
        assertTrue(report.running.achieved)
        assertFalse(report.cycling.achieved)
        assertTrue(report.swimming.achieved)
        assertTrue(report.weightlifting.achieved)
        assertFalse(report.total.achieved)
    }

    @Test
    fun highValuesProduceAchievedReport() {
        val report = WorkoutEvaluator.evaluate(
            WorkoutDurations(running = 100, cycling = 50, swimming = 40, weightlifting = 20),
        )

        assertEquals(210, report.total.actualMinutes)
        assertTrue(report.running.achieved)
        assertTrue(report.cycling.achieved)
        assertTrue(report.swimming.achieved)
        assertTrue(report.weightlifting.achieved)
        assertTrue(report.total.achieved)
    }

    @Test
    fun allZeroValuesAreNotAchieved() {
        val report = WorkoutEvaluator.evaluate(
            WorkoutDurations(running = 0, cycling = 0, swimming = 0, weightlifting = 0),
        )

        assertEquals(0, report.total.actualMinutes)
        assertFalse(report.running.achieved)
        assertFalse(report.cycling.achieved)
        assertFalse(report.swimming.achieved)
        assertFalse(report.weightlifting.achieved)
        assertFalse(report.total.achieved)
    }

    @Test
    fun totalAndIndividualTargetsAreCheckedSeparately() {
        val report = WorkoutEvaluator.evaluate(
            WorkoutDurations(running = 150, cycling = 0, swimming = 0, weightlifting = 0),
        )

        assertTrue(report.total.achieved)
        assertFalse(report.cycling.achieved)
        assertFalse(report.allTargetsAchieved)
    }
}
