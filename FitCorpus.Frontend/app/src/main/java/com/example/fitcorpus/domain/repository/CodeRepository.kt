package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.OnboardingCode

interface CodeRepository {
    suspend fun getCodes(): Result<List<OnboardingCode>>
    suspend fun purchaseCodes(count: Int): Result<PurchaseCodeResponse>
}

data class PurchaseCodeResponse(
    val codes: List<OnboardingCode>
)