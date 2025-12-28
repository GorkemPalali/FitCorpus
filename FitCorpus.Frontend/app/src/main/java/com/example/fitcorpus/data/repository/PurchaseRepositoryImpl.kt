package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.remote.api.PurchaseApi
import com.example.fitcorpus.data.remote.dto.ConfirmPurchaseRequest
import com.example.fitcorpus.data.remote.dto.PurchaseIntentRequest
import com.example.fitcorpus.data.remote.dto.RedeemCodeRequest
import com.example.fitcorpus.domain.model.Match
import com.example.fitcorpus.domain.repository.PurchaseIntentResponse
import com.example.fitcorpus.domain.repository.PurchaseRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val purchaseApi: PurchaseApi
) : PurchaseRepository {
    
    override suspend fun createPurchaseIntent(packageId: String): Result<PurchaseIntentResponse> {
        return try {
            val response = purchaseApi.createPurchaseIntent(
                PurchaseIntentRequest(packageId)
            )
            
            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                Result.Success(
                    PurchaseIntentResponse(
                        clientSecret = dto.clientSecret
                    )
                )
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to create purchase intent",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun confirmPurchase(
        packageId: String,
        paymentResult: String
    ): Result<Match> {
        return try {
            val response = purchaseApi.confirmPurchase(
                ConfirmPurchaseRequest(packageId, paymentResult)
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Purchase confirmation failed",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun redeemCode(code: String): Result<Match> {
        return try {
            val response = purchaseApi.redeemCode(
                RedeemCodeRequest(code)
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(
                    AppError.ValidationError(
                        response.message() ?: "Invalid or expired code"
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
}