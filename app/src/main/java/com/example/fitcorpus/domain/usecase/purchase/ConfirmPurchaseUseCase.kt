package com.example.fitcorpus.domain.usecase.purchase

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Match
import com.example.fitcorpus.domain.repository.PurchaseRepository
import javax.inject.Inject

class ConfirmPurchaseUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {
    suspend operator fun invoke(
        packageId: String,
        paymentResult: String
    ): Result<Match> {
        return purchaseRepository.confirmPurchase(packageId, paymentResult)
    }
}