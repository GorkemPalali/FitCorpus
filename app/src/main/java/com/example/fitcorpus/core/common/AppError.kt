package com.example.fitcorpus.core.common

sealed class AppError : Exception() {
    data class NetworkError(override val message: String, val code: Int? = null) : AppError()
    data class ServerError(override val message: String, val code: Int) : AppError()
    data class AuthenticationError(override val message: String) : AppError()
    data class ValidationError(override val message: String, val field: String? = null) : AppError()
    data class NotFoundError(override val message: String) : AppError()
    data class UnknownError(override val message: String, val throwable: Throwable? = null) : AppError()
}