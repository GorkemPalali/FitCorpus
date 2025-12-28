package com.example.fitcorpus.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PurchaseIntentRequest(
    @Json(name = "packageId") val packageId: String
)

@JsonClass(generateAdapter = true)
data class PurchaseIntentResponseDto(
    @Json(name = "clientSecret") val clientSecret: String
)

@JsonClass(generateAdapter = true)
data class ConfirmPurchaseRequest(
    @Json(name = "packageId") val packageId: String,
    @Json(name = "paymentResult") val paymentResult: String
)

@JsonClass(generateAdapter = true)
data class RedeemCodeRequest(
    @Json(name = "code") val code: String
)

@JsonClass(generateAdapter = true)
data class MatchDto(
    @Json(name = "id") val id: String,
    @Json(name = "athleteId") val athleteId: String,
    @Json(name = "trainerId") val trainerId: String,
    @Json(name = "packageId") val packageId: String,
    @Json(name = "status") val status: String,
    @Json(name = "createdAt") val createdAt: String
)












