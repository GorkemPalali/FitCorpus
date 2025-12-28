package com.example.fitcorpus.domain.model

data class Package(
    val id: String,
    val trainerId: String,
    val title: String,
    val description: String?,
    val price: Int,
    val durationWeeks: Int,
    val mode: TrainingMode,
    val services: List<String>? = null
)