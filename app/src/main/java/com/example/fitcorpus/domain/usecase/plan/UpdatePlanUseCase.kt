package com.example.fitcorpus.domain.usecase.plan

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Plan
import com.example.fitcorpus.domain.repository.PlanRepository
import javax.inject.Inject

class UpdatePlanUseCase @Inject constructor(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(plan: Plan): Result<Plan> {
        if (plan.name.isBlank()) {
            return Result.Error(
                AppError.ValidationError("Plan Name must be filled",
                    "name")
            )
        }
        
        return planRepository.updatePlan(plan)
    }
}