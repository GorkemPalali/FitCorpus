package com.example.fitcorpus.domain.usecase.purchase

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Match
import com.example.fitcorpus.domain.repository.PurchaseRepository
import javax.inject.Inject

class RedeemCodeUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {
    suspend operator fun invoke(code: String): Result<Match> {
        if (code.isBlank()) {
            return Result.Error(
                AppError.ValidationError("Code must be filled",
                    "code")
            )
        }
        
        return purchaseRepository.redeemCode(code.trim())
    }
}