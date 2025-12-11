package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.User
import com.example.fitcorpus.domain.model.UserRole

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<TokenResponse>
    suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        role: UserRole,
        city: String? = null,
        gender: String? = null
    ): Result<TokenResponse>
    suspend fun refreshToken(refreshToken: String): Result<TokenResponse>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): Result<User>
}

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val role: String
)