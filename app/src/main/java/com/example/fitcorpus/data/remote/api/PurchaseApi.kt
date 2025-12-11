package com.example.fitcorpus.data.remote.api

import com.example.fitcorpus.data.remote.dto.ConfirmPurchaseRequest
import com.example.fitcorpus.data.remote.dto.MatchDto
import com.example.fitcorpus.data.remote.dto.PurchaseIntentRequest
import com.example.fitcorpus.data.remote.dto.PurchaseIntentResponseDto
import com.example.fitcorpus.data.remote.dto.RedeemCodeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PurchaseApi {
    @POST("v1/purchase/intent")
    suspend fun createPurchaseIntent(@Body request: PurchaseIntentRequest): Response<PurchaseIntentResponseDto>
    
    @POST("v1/purchase/confirm")
    suspend fun confirmPurchase(@Body request: ConfirmPurchaseRequest): Response<MatchDto>
    
    @POST("v1/match/redeem")
    suspend fun redeemCode(@Body request: RedeemCodeRequest): Response<MatchDto>
}