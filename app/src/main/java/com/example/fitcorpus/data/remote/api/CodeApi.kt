package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.CodeDto
import com.example.fitcorpus.data.remote.dto.PurchaseCodeRequest
import com.example.fitcorpus.data.remote.dto.PurchaseCodeResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CodeApi {
    @GET("v1/trainer/codes")
    suspend fun getCodes(): Response<List<CodeDto>>
    
    @POST("v1/trainer/codes/purchase")
    suspend fun purchaseCodes(@Body request: PurchaseCodeRequest): Response<PurchaseCodeResponseDto>
}