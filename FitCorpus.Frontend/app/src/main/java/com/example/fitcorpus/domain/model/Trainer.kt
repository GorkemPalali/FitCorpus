package com.example.fitcorpus.domain.model

data class Trainer(
    val id: String,
    val name: String,
    val surname: String,
    val photoUrl: String?,
    val ratingAvg: Double,
    val priceFrom: Int,
    val modes: List<TrainingMode>,
    val bio: String?,
    val achievements: List<String>?,
    val gender: String?,
    val location: String? // TODO: şehir listeleri hazırlanıp buraya çekilecek
)

enum class TrainingMode {
    ONLINE,
    FACE_TO_FACE,
    BOTH
}