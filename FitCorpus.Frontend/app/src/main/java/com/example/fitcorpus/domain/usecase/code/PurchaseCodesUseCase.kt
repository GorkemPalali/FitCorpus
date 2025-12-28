package com.example.fitcorpus.domain.usecase.code

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.CodeRepository
import com.example.fitcorpus.domain.repository.PurchaseCodeResponse
import javax.inject.Inject

class PurchaseCodesUseCase @Inject constructor(
    private val codeRepository: CodeRepository
) {
    suspend operator fun invoke(count: Int): Result<PurchaseCodeResponse> {
        if (count !in listOf(1, 5, 10)) {
            return Result.Error(
                AppError.ValidationError(
                    "Number of codes must be 1, 5 or 10",
                    "count"
                )
            )
        }
        
        return codeRepository.purchaseCodes(count)
    }
}