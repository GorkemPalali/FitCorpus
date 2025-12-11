package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.AssignPlanRequest
import com.example.fitcorpus.data.remote.dto.StudentDto
import com.example.fitcorpus.data.remote.dto.StudentLogsDto
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate

interface StudentApi {
    @GET("v1/trainer/students")
    suspend fun getStudents(): Response<List<StudentDto>>
    
    @GET("v1/students/{athleteId}/logs")
    suspend fun getStudentLogs(
        @Path("athleteId") athleteId: String,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null
    ): Response<StudentLogsDto>
    
    @POST("v1/students/{athleteId}/assign-plan")
    suspend fun assignPlan(
        @Path("athleteId") athleteId: String,
        @Body request: AssignPlanRequest
    ): Response<Unit>
}