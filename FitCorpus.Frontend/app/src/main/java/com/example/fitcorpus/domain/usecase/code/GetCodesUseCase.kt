package com.example.fitcorpus.domain.usecase.code

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.OnboardingCode
import com.example.fitcorpus.domain.repository.CodeRepository
import javax.inject.Inject

class GetCodesUseCase @Inject constructor(
    private val codeRepository: CodeRepository
) {
    suspend operator fun invoke(): Result<List<OnboardingCode>> {
        return codeRepository.getCodes()
    }
}