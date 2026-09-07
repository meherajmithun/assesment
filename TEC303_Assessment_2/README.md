# TEC303 Assessment 2 - Fitness Training Report

This is a simple Android application made with Kotlin in Android Studio.

The application has two screens. On the first screen, the user enters the
number of minutes completed for:

- Running
- Cycling
- Swimming
- Weightlifting

The total is calculated automatically. The **Reset** button clears all input,
and the **Get Report** button opens the second screen.

The second screen shows the entered minutes, each workout target, the result
for each workout, and the result for the combined 150-minute target.

The app supports light and dark mode. Use the theme button at the top of the
first screen to change the appearance. The app remembers the selected mode
and does not depend on the phone system theme.

## Workout Targets

- Running: 60 minutes
- Cycling: 30 minutes
- Swimming: 30 minutes
- Weightlifting: 10 minutes
- Combined target: 150 minutes

## How to Run

1. Open the `codebase` folder in Android Studio.
2. Wait for Gradle to finish syncing.
3. Select an Android emulator.
4. Click the **Run** button.

To test dark mode, tap **Dark mode** inside the app. Tap **Light mode** to
change it back.

The project requires JDK 17 and Android SDK 35.
