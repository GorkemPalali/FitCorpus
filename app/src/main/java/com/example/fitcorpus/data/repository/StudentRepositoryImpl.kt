package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.remote.api.StudentApi
import com.example.fitcorpus.data.remote.dto.AssignPlanRequest
import com.example.fitcorpus.domain.model.Student
import com.example.fitcorpus.domain.repository.StudentLogs
import com.example.fitcorpus.domain.repository.StudentRepository
import java.time.LocalDate
import javax.inject.Inject

class StudentRepositoryImpl @Inject constructor(
    private val studentApi: StudentApi
) : StudentRepository {
    
    override suspend fun getStudents(): Result<List<Student>> {
        return try {
            val response = studentApi.getStudents()
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.map { it.toDomain() })
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to fetch students",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun getStudentLogs(
        athleteId: String,
        from: LocalDate?,
        to: LocalDate?
    ): Result<StudentLogs> {
        return try {
            val response = studentApi.getStudentLogs(
                athleteId = athleteId,
                from = from?.toString(),
                to = to?.toString()
            )
            
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to fetch student logs",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun assignPlan(
        athleteId: String,
        planId: String,
        date: LocalDate
    ): Result<Unit> {
        return try {
            val response = studentApi.assignPlan(
                athleteId = athleteId,
                request = AssignPlanRequest(planId, date.toString())
            )
            
            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(
                    AppError.ServerError(
                        response.message() ?: "Failed to assign plan",
                        response.code()
                    )
                )
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
}