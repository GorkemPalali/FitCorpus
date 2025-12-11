package com.example.fitcorpus.domain.model

data class Plan(
    val id: String,
    val trainerId: String? = null, // null if created by athlete
    val name: String,
    val type: PlanType,
    val exercises: List<PlanExercise>? = null,
    val description: String? = null
)

enum class PlanType {
    WORKOUT,
    DIET
}

data class PlanExercise(
    val exerciseName: String,
    val sets: Int,
    val reps: List<Int>,
    val weight: List<Float>,
    val restSeconds: Int? = null
)