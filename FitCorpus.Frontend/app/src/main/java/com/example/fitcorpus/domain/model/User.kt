package com.example.fitcorpus.domain.model

data class User(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val role: UserRole,
    val height: Int? = null,
    val weight: Float? = null,
    val gender: String? = null,
    val city: String? = null,
    val age: Int? = null,
    val photoUrl: String? = null
)

enum class UserRole {
    ATHLETE,
    TRAINER,
    ADMIN
}