package com.example.fitcorpus.domain.model

data class Match(
    val id: String,
    val athleteId: String,
    val trainerId: String,
    val packageId: String,
    val status: MatchStatus,
    val createdAt: String
)

enum class MatchStatus {
    ACTIVE,
    ENDED
}