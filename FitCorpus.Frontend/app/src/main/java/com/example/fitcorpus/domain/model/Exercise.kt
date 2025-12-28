package com.example.fitcorpus.domain.model

data class Exercise(
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String? = null,
    val description: String? = null,
    val type: ExerciseType
)

enum class ExerciseType {
    WORKOUT,
    WARMUP
}