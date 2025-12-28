package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Match

interface PurchaseRepository {
    suspend fun createPurchaseIntent(packageId: String): Result<PurchaseIntentResponse>
    suspend fun confirmPurchase(
        packageId: String,
        paymentResult: String
    ): Result<Match>
    suspend fun redeemCode(code: String): Result<Match>
}

data class PurchaseIntentResponse(
    val clientSecret: String
)