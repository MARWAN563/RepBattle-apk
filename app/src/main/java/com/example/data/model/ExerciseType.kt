package com.example.data.model

enum class ExerciseType(
    val title: String,
    val subtitle: String,
    val detail: String,
    val iconName: String,
    val recommended: Boolean = false
) {
    PUSH_UPS(
        title = "Push-ups",
        subtitle = "Chest & Triceps · Camera Verified",
        detail = "100% Rec",
        iconName = "sports_gymnastics",
        recommended = true
    ),
    DEEP_SQUATS(
        title = "Deep Squats",
        subtitle = "Legs & Glutes · Depth Tracking 90°",
        detail = "Hip > Knee",
        iconName = "accessibility_new"
    ),
    PULL_UPS(
        title = "Pull-ups",
        subtitle = "Back & Biceps · Bar Detection",
        detail = "Chin Clear",
        iconName = "directions_run"
    ),
    WALKING_LUNGES(
        title = "Walking Lunges",
        subtitle = "Legs · Stride & Knee Angle",
        detail = "Stride Lock",
        iconName = "downhill_skiing"
    )
}

enum class RuleType(
    val title: String,
    val subtitle: String,
    val targetReps: Int
) {
    FIRST_TO_50(
        title = "First to 50",
        subtitle = "Pace sprint · Locked camera",
        targetReps = 50
    ),
    MOST_IN_5_MIN(
        title = "Most in 5 Min",
        subtitle = "Endurance grit duel",
        targetReps = 75
    ),
    CENTURY_100(
        title = "Century (100)",
        subtitle = "The ultimate stamina test",
        targetReps = 100
    ),
    DAILY_DUEL(
        title = "Daily Duel",
        subtitle = "Sync streak defender",
        targetReps = 40
    )
}
