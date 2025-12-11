package com.example.fitcorpus.domain.usecase.workout

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.WorkoutRepository
import javax.inject.Inject

class SyncWorkoutsUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return workoutRepository.syncPendingWorkouts()
    }
}