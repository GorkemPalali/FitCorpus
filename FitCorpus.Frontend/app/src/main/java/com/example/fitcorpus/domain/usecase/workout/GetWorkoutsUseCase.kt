package com.example.fitcorpus.domain.usecase.workout

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.WorkoutEntry
import com.example.fitcorpus.domain.repository.WorkoutRepository
import java.time.LocalDate
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(
        from: LocalDate? = null,
        to: LocalDate? = null
    ): Result<List<WorkoutEntry>> {
        return workoutRepository.getWorkouts(from, to, null)
    }
}