package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.remote.api.CodeApi
import com.example.fitcorpus.data.remote.dto.PurchaseCodeRequest
import com.example.fitcorpus.domain.model.OnboardingCode
import com.example.fitcorpus.domain.repository.CodeRepository
import com.example.fitcorpus.domain.repository.PurchaseCodeResponse
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val codeApi: CodeApi
) : CodeRepository {
    
    override suspend fun getCodes(): Result<List<OnboardingCode>> {
        return try {
            val response = codeApi.getCodes()
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.map { it.toDomain() })
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to fetch codes",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun purchaseCodes(count: Int): Result<PurchaseCodeResponse> {
        // Validate count
        if (count !in listOf(1, 5, 10)) {
            return Result.Error(
                AppError.ValidationError(
                    "Code count must be 1, 5, or 10",
                    "count"
                )
            )
        }
        
        return try {
            val response = codeApi.purchaseCodes(
                PurchaseCodeRequest(count)
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to purchase codes",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
}