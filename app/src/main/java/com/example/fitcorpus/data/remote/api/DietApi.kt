package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.DietEntryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DietApi {
    @POST("v1/diets")
    suspend fun createDietEntry(@Body entry: DietEntryDto): Response<DietEntryDto>
    
    @GET("v1/diets")
    suspend fun getDietEntries(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("updatedSince") updatedSince: String? = null
    ): Response<List<DietEntryDto>>
}