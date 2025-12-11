package com.example.fitcorpus.domain.usecase.workout

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.WorkoutEntry
import com.example.fitcorpus.domain.repository.WorkoutRepository
import java.util.UUID
import javax.inject.Inject

class CreateWorkoutUseCase @Inject constructor(
    private val workoutRepository: WorkoutRepository
) {
    suspend operator fun invoke(workout: WorkoutEntry): Result<WorkoutEntry> {
        if (workout.movement.isBlank()) {
            return Result.Error(
                AppError.ValidationError("Workout movement name must be filled",
                    "movement")
            )
        }
        
        if (workout.sets <= 0) {
            return Result.Error(
                AppError.ValidationError("Workout sets must be greater than 0",
                    "sets")
            )
        }
        
        if (workout.reps.isEmpty() || workout.reps.any { it <= 0 }) {
            return Result.Error(
                AppError.ValidationError("İnvalid reps values",
                    "reps")
            )
        }
        
        if (workout.weight.isEmpty() || workout.weight.any { it < 0 }) {
            return Result.Error(
                AppError.ValidationError("Invalid weight values",
                    "weight")
            )
        }

        val clientRequestId = UUID.randomUUID().toString()
        
        return workoutRepository.createWorkout(workout, clientRequestId)
    }
}