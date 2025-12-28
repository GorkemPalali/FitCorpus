package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Plan
import com.example.fitcorpus.domain.model.PlanType

interface PlanRepository {
    suspend fun createPlan(plan: Plan): Result<Plan>
    suspend fun getPlans(type: PlanType? = null): Result<List<Plan>>
    suspend fun getPlanById(id: String): Result<Plan>
    suspend fun updatePlan(plan: Plan): Result<Plan>
    suspend fun deletePlan(id: String): Result<Unit>
}