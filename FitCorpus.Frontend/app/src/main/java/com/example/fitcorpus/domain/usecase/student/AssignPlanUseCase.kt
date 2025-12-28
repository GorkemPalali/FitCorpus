package com.example.fitcorpus.domain.usecase.student

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.StudentRepository
import java.time.LocalDate
import javax.inject.Inject

class AssignPlanUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    suspend operator fun invoke(
        athleteId: String,
        planId: String,
        date: LocalDate
    ): Result<Unit> {
        return studentRepository.assignPlan(athleteId, planId, date)
    }
}