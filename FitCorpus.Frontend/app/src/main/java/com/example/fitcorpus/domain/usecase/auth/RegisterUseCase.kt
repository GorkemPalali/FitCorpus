package com.example.fitcorpus.domain.usecase.auth

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.core.common.validators.Validators
import com.example.fitcorpus.domain.model.UserRole
import com.example.fitcorpus.domain.repository.AuthRepository
import com.example.fitcorpus.domain.repository.TokenResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        surname: String,
        email: String,
        password: String,
        role: UserRole,
        city: String? = null,
        gender: String? = null
    ): Result<TokenResponse> {
        if (!Validators.isValidName(name)) {
            return Result.Error(
                AppError.ValidationError(
                    "Name must be at least 2 characters long and can only contain letters.",
                    "name"
                )
            )
        }

        if (!Validators.isValidName(surname)) {
            return Result.Error(
                AppError.ValidationError(
                    "Surname must be at least 2 characters long and can only contain letters.",
                    "surname"
                )
            )
        }

        if (!Validators.isValidEmail(email)) {
            return Result.Error(
                AppError.ValidationError(
                    "Enter a valid email.",
                    "email"
                )
            )
        }

        if (!Validators.isValidPassword(password)) {
            return Result.Error(
                AppError.ValidationError(
                    "Password must be at least 8 characters long and can only contain letters and numbers.",
                    "password"
                )
            )
        }
        
        return authRepository.register(name, surname, email, password, role, city, gender)
    }
}