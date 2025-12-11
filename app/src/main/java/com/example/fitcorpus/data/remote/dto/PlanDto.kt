package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "trainerId") val trainerId: String? = null,
    @Json(name = "name") val name: String,
    @Json(name = "type") val type: String,
    @Json(name = "exercises") val exercises: List<PlanExerciseDto>? = null,
    @Json(name = "description") val description: String? = null
)

@JsonClass(generateAdapter = true)
data class PlanExerciseDto(
    @Json(name = "exerciseName") val exerciseName: String,
    @Json(name = "sets") val sets: Int,
    @Json(name = "reps") val reps: List<Int>,
    @Json(name = "weight") val weight: List<Float>,
    @Json(name = "restSeconds") val restSeconds: Int? = null
)











