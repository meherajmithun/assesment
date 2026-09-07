package com.kbs.fitnessreport

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var runningInput: EditText
    private lateinit var cyclingInput: EditText
    private lateinit var swimmingInput: EditText
    private lateinit var weightliftingInput: EditText
    private lateinit var totalText: TextView
    private lateinit var totalProgress: ProgressBar
    private lateinit var themeButton: Button

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(AppTheme.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
        addLiveTotalListeners()
        setupThemeButton()

        findViewById<Button>(R.id.resetButton).setOnClickListener { resetInputs() }
        findViewById<Button>(R.id.reportButton).setOnClickListener { openReport() }
    }

    private fun bindViews() {
        runningInput = findViewById(R.id.runningInput)
        cyclingInput = findViewById(R.id.cyclingInput)
        swimmingInput = findViewById(R.id.swimmingInput)
        weightliftingInput = findViewById(R.id.weightliftingInput)
        totalText = findViewById(R.id.totalText)
        totalProgress = findViewById(R.id.totalProgress)
        themeButton = findViewById(R.id.themeButton)
    }

    private fun setupThemeButton() {
        val darkModeEnabled = AppTheme.isDarkMode(this)
        themeButton.setText(
            if (darkModeEnabled) {
                R.string.use_light_mode
            } else {
                R.string.use_dark_mode
            },
        )
        themeButton.setCompoundDrawablesWithIntrinsicBounds(
            if (darkModeEnabled) R.drawable.ic_sun else R.drawable.ic_moon,
            0,
            0,
            0,
        )

        themeButton.setOnClickListener {
            AppTheme.toggle(this)
            recreate()
        }
    }

    private fun addLiveTotalListeners() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(
                text: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) = Unit

            override fun onTextChanged(
                text: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
                updateLiveTotal()
            }

            override fun afterTextChanged(text: Editable?) = Unit
        }

        inputFields().forEach { it.addTextChangedListener(watcher) }
        updateLiveTotal()
    }

    private fun updateLiveTotal() {
        val total = inputFields().sumOf { it.text.toString().toIntOrNull() ?: 0 }
        totalText.text = getString(R.string.total_progress_value, total)
        totalProgress.progress = total.coerceAtMost(WorkoutEvaluator.TOTAL_TARGET)
    }

    private fun resetInputs() {
        inputFields().forEach {
            it.text.clear()
            it.error = null
        }
        runningInput.requestFocus()
    }

    private fun openReport() {
        val running = validateMinutes(runningInput, getString(R.string.running))
        val cycling = validateMinutes(cyclingInput, getString(R.string.cycling))
        val swimming = validateMinutes(swimmingInput, getString(R.string.swimming))
        val weightlifting = validateMinutes(
            weightliftingInput,
            getString(R.string.weightlifting),
        )

        if (listOf(running, cycling, swimming, weightlifting).any { it == null }) {
            Toast.makeText(this, R.string.fix_errors, Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(this, ReportActivity::class.java).apply {
            putExtra(EXTRA_RUNNING, requireNotNull(running))
            putExtra(EXTRA_CYCLING, requireNotNull(cycling))
            putExtra(EXTRA_SWIMMING, requireNotNull(swimming))
            putExtra(EXTRA_WEIGHTLIFTING, requireNotNull(weightlifting))
        }
        startActivity(intent)
    }

    private fun validateMinutes(field: EditText, workoutName: String): Int? {
        val rawValue = field.text.toString().trim()
        if (rawValue.isEmpty()) {
            field.error = getString(R.string.minutes_required, workoutName)
            return null
        }

        val minutes = rawValue.toIntOrNull()
        if (minutes == null) {
            field.error = getString(R.string.minutes_invalid)
            return null
        }

        if (minutes < 0) {
            field.error = getString(R.string.minutes_negative)
            return null
        }

        field.error = null
        return minutes
    }

    private fun inputFields(): List<EditText> = listOf(
        runningInput,
        cyclingInput,
        swimmingInput,
        weightliftingInput,
    )

    companion object {
        const val EXTRA_RUNNING = "com.kbs.fitnessreport.RUNNING"
        const val EXTRA_CYCLING = "com.kbs.fitnessreport.CYCLING"
        const val EXTRA_SWIMMING = "com.kbs.fitnessreport.SWIMMING"
        const val EXTRA_WEIGHTLIFTING = "com.kbs.fitnessreport.WEIGHTLIFTING"

    }
}
