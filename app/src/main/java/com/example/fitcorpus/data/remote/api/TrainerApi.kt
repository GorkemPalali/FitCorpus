package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.PackageDto
import com.example.fitcorpus.data.remote.dto.TrainerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrainerApi {
    @GET("v1/trainers")
    suspend fun getTrainers(
        @Query("loc") location: String? = null,
        @Query("priceMin") priceMin: Int? = null,
        @Query("priceMax") priceMax: Int? = null,
        @Query("gender") gender: String? = null,
        @Query("mode") mode: String? = null,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): Response<List<TrainerDto>>
    
    @GET("v1/trainers/{id}")
    suspend fun getTrainerById(@Path("id") id: String): Response<TrainerDto>
    
    @GET("v1/trainers/{id}/packages")
    suspend fun getTrainerPackages(@Path("id") trainerId: String): Response<List<PackageDto>>
}