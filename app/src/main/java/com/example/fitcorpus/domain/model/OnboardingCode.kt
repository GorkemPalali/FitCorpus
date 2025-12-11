package com.example.fitcorpus.domain.model

import java.time.Instant

data class OnboardingCode(
    val code: String,
    val trainerId: String,
    val status: CodeStatus,
    val expiresAt: Instant?,
    val paidByTrainer: Boolean
)

enum class CodeStatus {
    ISSUED,
    REDEEMED
}