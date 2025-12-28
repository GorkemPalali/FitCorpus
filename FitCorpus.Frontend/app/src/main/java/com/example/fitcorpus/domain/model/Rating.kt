package com.example.fitcorpus.domain.model

data class Rating(
    val id: String,
    val athleteId: String,
    val trainerId: String,
    val packageId: String,
    val stars: Int,
    val createdAt: String
)