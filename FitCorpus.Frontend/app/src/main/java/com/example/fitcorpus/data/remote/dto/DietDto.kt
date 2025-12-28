package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DietEntryDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "date") val date: String, // ISO-8601
    @Json(name = "calories") val calories: Int,
    @Json(name = "meal") val meal: String? = null,
    @Json(name = "mealName") val mealName: String? = null,
    @Json(name = "portion") val portion: String? = null,
    @Json(name = "note") val note: String? = null
)












