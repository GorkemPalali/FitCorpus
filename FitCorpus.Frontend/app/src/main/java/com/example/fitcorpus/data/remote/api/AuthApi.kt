package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.LoginRequest
import com.example.fitcorpus.data.remote.dto.RefreshTokenRequest
import com.example.fitcorpus.data.remote.dto.RegisterRequest
import com.example.fitcorpus.data.remote.dto.TokenResponseDto
import com.example.fitcorpus.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<TokenResponseDto>
    
    @POST("v1/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<TokenResponseDto>
    
    @POST("v1/auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<TokenResponseDto>
    
    @POST("v1/auth/logout")
    suspend fun logout(): Response<Unit>
    
    @GET("v1/auth/me")
    suspend fun getCurrentUser(): Response<UserDto>
}