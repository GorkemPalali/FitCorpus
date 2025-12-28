package com.example.fitcorpus.domain.usecase.auth

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}