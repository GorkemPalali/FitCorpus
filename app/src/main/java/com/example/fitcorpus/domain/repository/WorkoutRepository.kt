package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.WorkoutEntry
import java.time.LocalDate

interface WorkoutRepository {
    suspend fun createWorkout(workout: WorkoutEntry, clientRequestId: String): Result<WorkoutEntry>
    suspend fun getWorkouts(
        from: LocalDate? = null,
        to: LocalDate? = null,
        updatedSince: String? = null
    ): Result<List<WorkoutEntry>>
    suspend fun getWorkoutById(id: String): Result<WorkoutEntry>
    suspend fun syncPendingWorkouts(): Result<Unit>
}