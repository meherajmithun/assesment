package com.kbs.fitnessreport

import android.content.Context
import android.content.res.Configuration

object AppTheme {
    private const val PREFERENCES_NAME = "fitness_report_settings"
    private const val DARK_MODE_KEY = "dark_mode"

    fun wrapContext(context: Context): Context {
        val configuration = Configuration(context.resources.configuration)
        val selectedMode = if (isDarkMode(context)) {
            Configuration.UI_MODE_NIGHT_YES
        } else {
            Configuration.UI_MODE_NIGHT_NO
        }

        configuration.uiMode =
            (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or selectedMode

        return context.createConfigurationContext(configuration)
    }

    fun isDarkMode(context: Context): Boolean {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .getBoolean(DARK_MODE_KEY, false)
    }

    fun toggle(context: Context) {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(DARK_MODE_KEY, !isDarkMode(context))
            .apply()
    }
}
