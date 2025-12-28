package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.WorkoutEntryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkoutApi {
    @POST("v1/workouts")
    suspend fun createWorkout(@Body workout: WorkoutEntryDto): Response<WorkoutEntryDto>
    
    @GET("v1/workouts")
    suspend fun getWorkouts(
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("updatedSince") updatedSince: String? = null
    ): Response<List<WorkoutEntryDto>>
    
    @GET("v1/workouts/{id}")
    suspend fun getWorkoutById(@Path("id") id: String): Response<WorkoutEntryDto>
}