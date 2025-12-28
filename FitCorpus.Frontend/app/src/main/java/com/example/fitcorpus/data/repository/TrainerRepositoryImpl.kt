package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.remote.api.TrainerApi
import com.example.fitcorpus.domain.repository.TrainerRepository
import javax.inject.Inject

class TrainerRepositoryImpl @Inject constructor(
    private val trainerApi: TrainerApi
) : TrainerRepository {
    
    override suspend fun getTrainers(
        location: String?,
        priceMin: Int?,
        priceMax: Int?,
        gender: String?,
        mode: String?,
        sort: String?,
        page: Int,
        size: Int
    ): Result<List<com.example.fitcorpus.domain.model.Trainer>> {
        return try {
            val response = trainerApi.getTrainers(
                location, priceMin, priceMax, gender, mode, sort, page, size
            )
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.map { it.toDomain() })
            } else {
                Result.Error(AppError.ServerError(response.message() ?: "Failed to fetch trainers", response.code()))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun getTrainerById(id: String): Result<com.example.fitcorpus.domain.model.Trainer> {
        return try {
            val response = trainerApi.getTrainerById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(AppError.NotFoundError("Trainer not found"))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun getTrainerPackages(trainerId: String): Result<List<com.example.fitcorpus.domain.model.Package>> {
        return try {
            val response = trainerApi.getTrainerPackages(trainerId)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.map { it.toDomain() })
            } else {
                Result.Error(AppError.ServerError(response.message() ?: "Failed to fetch packages", response.code()))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
}