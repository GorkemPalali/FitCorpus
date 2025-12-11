package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class WorkoutEntryDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "clientRequestId") val clientRequestId: String? = null,
    @Json(name = "planId") val planId: String? = null,
    @Json(name = "date") val date: String, // ISO-8601
    @Json(name = "movement") val movement: String,
    @Json(name = "sets") val sets: Int,
    @Json(name = "reps") val reps: List<Int>,
    @Json(name = "weight") val weight: List<Float>,
    @Json(name = "rpe") val rpe: Int? = null,
    @Json(name = "assignedBy") val assignedBy: String? = null,
    @Json(name = "restSeconds") val restSeconds: Int? = null
)












