package com.example.fitcorpus.domain.usecase.auth

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.core.common.validators.Validators
import com.example.fitcorpus.domain.repository.AuthRepository
import com.example.fitcorpus.domain.repository.TokenResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<TokenResponse> {
        if (!Validators.isValidEmail(email)) {
            return Result.Error(
                com.example.fitcorpus.core.common.AppError.ValidationError(
                    "Enter a valid email",
                    "email"
                )
            )
        }
        
        if (password.isBlank()) {
            return Result.Error(
                com.example.fitcorpus.core.common.AppError.ValidationError(
                    "Password must be filled",
                    "password"
                )
            )
        }
        
        return authRepository.login(email, password)
    }
}