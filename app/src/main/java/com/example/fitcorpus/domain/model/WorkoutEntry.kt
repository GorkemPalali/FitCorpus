package com.example.fitcorpus.domain.model

import java.time.LocalDate

data class WorkoutEntry(
    val id: String? = null,
    val clientRequestId: String? = null,
    val planId: String? = null,
    val date: LocalDate,
    val movement: String,
    val sets: Int,
    val reps: List<Int>,
    val weight: List<Float>,
    val rpe: Int? = null,
    val assignedBy: String? = null,
    val restSeconds: Int? = null
)