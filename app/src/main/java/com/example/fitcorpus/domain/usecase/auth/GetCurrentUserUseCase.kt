package com.example.fitcorpus.domain.usecase.auth

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.User
import com.example.fitcorpus.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getCurrentUser()
    }
}