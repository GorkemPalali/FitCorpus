package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StudentDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "surname") val surname: String,
    @Json(name = "photoUrl") val photoUrl: String? = null,
    @Json(name = "matchId") val matchId: String
)

@JsonClass(generateAdapter = true)
data class StudentLogsDto(
    @Json(name = "workouts") val workouts: List<WorkoutEntryDto>,
    @Json(name = "diets") val diets: List<DietEntryDto>
)

@JsonClass(generateAdapter = true)
data class AssignPlanRequest(
    @Json(name = "planId") val planId: String,
    @Json(name = "date") val date: String // ISO-8601
)











