package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.PlanDto
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate

interface PlanApi {
    @POST("v1/plans")
    suspend fun createPlan(@Body plan: PlanDto): Response<PlanDto>
    
    @GET("v1/plans")
    suspend fun getPlans(@Query("type") type: String? = null): Response<List<PlanDto>>
    
    @GET("v1/plans/{id}")
    suspend fun getPlanById(@Path("id") id: String): Response<PlanDto>
    
    @PUT("v1/plans/{id}")
    suspend fun updatePlan(@Path("id") id: String, @Body plan: PlanDto): Response<PlanDto>
    
    @DELETE("v1/plans/{id}")
    suspend fun deletePlan(@Path("id") id: String): Response<Unit>
}