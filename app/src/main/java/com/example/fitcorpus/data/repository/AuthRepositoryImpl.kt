package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.auth.TokenManager
import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.remote.api.AuthApi
import com.example.fitcorpus.data.remote.dto.LoginRequest
import com.example.fitcorpus.data.remote.dto.RegisterRequest
import com.example.fitcorpus.data.remote.dto.RefreshTokenRequest
import com.example.fitcorpus.domain.model.UserRole
import com.example.fitcorpus.domain.repository.AuthRepository
import com.example.fitcorpus.domain.repository.TokenResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {
    
    override suspend fun login(email: String, password: String): Result<TokenResponse> {
        return try {
            val response = authApi.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val tokenResponse = response.body()!!.toDomain()
                tokenManager.saveAccessToken(tokenResponse.accessToken)
                tokenManager.saveRefreshToken(tokenResponse.refreshToken)
                tokenManager.saveRole(tokenResponse.role)
                Result.Success(tokenResponse)
            } else {
                when (response.code()) {
                    404 -> Result.Error(
                        AppError.NetworkError(
                            "Backend service is not available."
                        )
                    )
                    401 -> Result.Error(
                        AppError.AuthenticationError(
                            "Invalid e-mail or password."
                        )
                    )
                    else -> Result.Error(
                        AppError.AuthenticationError(
                            response.message() ?: "Login failed."
                        )
                    )
                }
            }
        } catch (e: java.net.UnknownHostException) {
            Result.Error(
                AppError.NetworkError(
                    "Unable to connect to the server. Check internet connection."
                )
            )
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(
                AppError.NetworkError(
                    "Connection timed out. Please try again."
                )
            )
        } catch (e: Exception) {
            Result.Error(
                AppError.NetworkError(
                    e.message ?: "A network error occured. Please try again."
                )
            )
        }
    }
    
    override suspend fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        role: UserRole,
        city: String?,
        gender: String?
    ): Result<TokenResponse> {
        return try {
            val response = authApi.register(
                RegisterRequest(name, surname, email, password, role.name, city, gender)
            )
            if (response.isSuccessful && response.body() != null) {
                val tokenResponse = response.body()!!.toDomain()
                tokenManager.saveAccessToken(tokenResponse.accessToken)
                tokenManager.saveRefreshToken(tokenResponse.refreshToken)
                tokenManager.saveRole(tokenResponse.role)
                Result.Success(tokenResponse)
            } else {
                // Handle HTTP status codes
                when (response.code()) {
                    403 -> Result.Error(
                        AppError.NetworkError(
                            "Access denied. The backend service may not be running on the correct port. Please check the port number of your backend."
                        )
                    )
                    404 -> Result.Error(
                        AppError.NetworkError(
                            "Backend service is currently unavailable. Please try again later."
                        )
                    )
                    400 -> Result.Error(
                        AppError.ValidationError(
                            response.message() ?: "Registration failed. Please check your information"
                        )
                    )
                    409 -> Result.Error(
                        AppError.ValidationError(
                            "This mail address is already in use"
                        )
                    )
                    else -> Result.Error(
                        AppError.ServerError(
                            response.message() ?: "Registration failed.",
                            response.code()
                        )
                    )
                }
            }
        } catch (e: java.net.UnknownHostException) {
            Result.Error(
                AppError.NetworkError(
                    "Unable to connect to the server. Check internet connection."
                )
            )
        } catch (e: java.net.SocketTimeoutException) {
            Result.Error(
                AppError.NetworkError(
                    "Connection timed out. Please try again."
                )
            )
        } catch (e: Exception) {
            Result.Error(
                AppError.NetworkError(
                    e.message ?: "A network error occured. Please try again."
                )
            )
        }
    }
    
    override suspend fun refreshToken(refreshToken: String): Result<TokenResponse> {
        return try {
            val response = authApi.refreshToken(RefreshTokenRequest(refreshToken))
            if (response.isSuccessful && response.body() != null) {
                val tokenResponse = response.body()!!.toDomain()
                tokenManager.saveAccessToken(tokenResponse.accessToken)
                if (tokenResponse.refreshToken.isNotEmpty()) {
                    tokenManager.saveRefreshToken(tokenResponse.refreshToken)
                }
                tokenManager.saveRole(tokenResponse.role)
                Result.Success(tokenResponse)
            } else {
                Result.Error(AppError.AuthenticationError("Token refresh failed"))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun logout(): Result<Unit> {
        return try {
            authApi.logout()
            tokenManager.clearTokens()
            Result.Success(Unit)
        } catch (e: Exception) {
            tokenManager.clearTokens()
            Result.Success(Unit)
        }
    }
    
    override suspend fun getCurrentUser(): Result<com.example.fitcorpus.domain.model.User> {
        return try {
            val response = authApi.getCurrentUser()
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(AppError.AuthenticationError("Failed to get user"))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
}