package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.mapper.toDto
import com.example.fitcorpus.data.remote.api.PlanApi
import com.example.fitcorpus.domain.model.Plan
import com.example.fitcorpus.domain.model.PlanType
import com.example.fitcorpus.domain.repository.PlanRepository
import javax.inject.Inject

class PlanRepositoryImpl @Inject constructor(
    private val planApi: PlanApi
) : PlanRepository {
    
    override suspend fun createPlan(plan: Plan): Result<Plan> {
        return try {
            val dto = plan.toDto()
            val response = planApi.createPlan(dto)
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to create plan",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun getPlans(type: PlanType?): Result<List<Plan>> {
        return try {
            val typeString = type?.name?.lowercase()
            val response = planApi.getPlans(typeString)
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.map { it.toDomain() })
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to fetch plans",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun getPlanById(id: String): Result<Plan> {
        return try {
            val response = planApi.getPlanById(id)
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(AppError.NotFoundError("Plan not found"))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun updatePlan(plan: Plan): Result<Plan> {
        return try {
            val dto = plan.toDto()
            val response = planApi.updatePlan(plan.id, dto)
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to update plan",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun deletePlan(id: String): Result<Unit> {
        return try {
            val response = planApi.deletePlan(id)
            
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to delete plan",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
}