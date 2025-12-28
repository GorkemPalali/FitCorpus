package com.example.fitcorpus.domain.usecase.plan

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.PlanRepository
import javax.inject.Inject

class DeletePlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(planId: String): Result<Unit> {
        return planRepository.deletePlan(planId)
    }
}