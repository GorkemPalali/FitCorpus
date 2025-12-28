package com.example.fitcorpus.domain.usecase.purchase

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.PurchaseIntentResponse
import com.example.fitcorpus.domain.repository.PurchaseRepository
import javax.inject.Inject

class CreatePurchaseIntentUseCase @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) {
    suspend operator fun invoke(packageId: String): Result<PurchaseIntentResponse> {
        return purchaseRepository.createPurchaseIntent(packageId)
    }
}