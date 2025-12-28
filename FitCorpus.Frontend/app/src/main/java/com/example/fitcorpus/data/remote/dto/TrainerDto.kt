package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrainerDto(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "surname") val surname: String,
    @Json(name = "photoUrl") val photoUrl: String?,
    @Json(name = "ratingAvg") val ratingAvg: Double,
    @Json(name = "priceFrom") val priceFrom: Int,
    @Json(name = "modes") val modes: List<String>,
    @Json(name = "bio") val bio: String?,
    @Json(name = "achievements") val achievements: List<String>?,
    @Json(name = "gender") val gender: String?,
    @Json(name = "location") val location: String? = null
)

@JsonClass(generateAdapter = true)
data class PackageDto(
    @Json(name = "id") val id: String,
    @Json(name = "trainerId") val trainerId: String,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String?,
    @Json(name = "price") val price: Int,
    @Json(name = "durationWeeks") val durationWeeks: Int,
    @Json(name = "mode") val mode: String,
    @Json(name = "services") val services: List<String>? = null
)












