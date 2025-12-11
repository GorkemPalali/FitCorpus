package com.example.fitcorpus.domain.usecase.plan

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Plan
import com.example.fitcorpus.domain.model.PlanType
import com.example.fitcorpus.domain.repository.PlanRepository
import javax.inject.Inject

class GetPlansUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(type: PlanType? = null): Result<List<Plan>> {
        return planRepository.getPlans(type)
    }
}