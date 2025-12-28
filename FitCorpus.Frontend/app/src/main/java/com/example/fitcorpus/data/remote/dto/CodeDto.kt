package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CodeDto(
    @Json(name = "code") val code: String,
    @Json(name = "trainerId") val trainerId: String,
    @Json(name = "status") val status: String,
    @Json(name = "expiresAt") val expiresAt: String? = null, // ISO-8601
    @Json(name = "paidByTrainer") val paidByTrainer: Boolean
)

@JsonClass(generateAdapter = true)
data class PurchaseCodeRequest(
    @Json(name = "count") val count: Int
)

@JsonClass(generateAdapter = true)
data class PurchaseCodeResponseDto(
    @Json(name = "codes") val codes: List<CodeDto>
)











