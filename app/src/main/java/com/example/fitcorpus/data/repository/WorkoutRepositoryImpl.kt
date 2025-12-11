package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.local.FitCorpusDatabase
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.mapper.toDto
import com.example.fitcorpus.data.mapper.toEntity
import com.example.fitcorpus.data.remote.api.WorkoutApi
import com.example.fitcorpus.domain.model.WorkoutEntry
import com.example.fitcorpus.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutApi: WorkoutApi,
    private val database: FitCorpusDatabase
) : WorkoutRepository {
    
    override suspend fun createWorkout(
        workout: WorkoutEntry,
        clientRequestId: String
    ): Result<WorkoutEntry> {
        val requestId = clientRequestId.ifEmpty { UUID.randomUUID().toString() }

        val entity = workout.toEntity(requestId)
        database.workoutDao().insertWorkout(entity)
        
        return try {
            val dto = workout.copy(clientRequestId = requestId).toDto()
            val response = workoutApi.createWorkout(dto)
            
            if (response.isSuccessful && response.body() != null) {
                val syncedWorkout = response.body()!!.toDomain()
                val updatedEntity = entity.copy(
                    id = syncedWorkout.id,
                    synced = true
                )
                database.workoutDao().updateWorkout(updatedEntity)
                Result.Success(syncedWorkout)
            } else {
                Result.Success(workout.copy(clientRequestId = requestId))
            }
        } catch (e: Exception) {
            Result.Success(workout.copy(clientRequestId = requestId))
        }
    }
    
    override suspend fun getWorkouts(
        from: LocalDate?,
        to: LocalDate?,
        updatedSince: String?
    ): Result<List<WorkoutEntry>> {
        return try {
            val response = workoutApi.getWorkouts(
                from = from?.toString(),
                to = to?.toString(),
                updatedSince = updatedSince
            )
            
            if (response.isSuccessful && response.body() != null) {
                val workouts = response.body()!!.map { it.toDomain() }
                workouts.forEach { workout ->
                    val entity = workout.toEntity()
                    database.workoutDao().insertWorkout(entity)
                }
                Result.Success(workouts)
            } else {
                getWorkoutsFromLocal(from, to)
            }
        } catch (e: Exception) {
            getWorkoutsFromLocal(from, to)
        }
    }
    
    private suspend fun getWorkoutsFromLocal(
        from: LocalDate?,
        to: LocalDate?
    ): Result<List<WorkoutEntry>> {
        return try {
            val fromDate = from ?: LocalDate.now().minusMonths(1)
            val toDate = to ?: LocalDate.now()

            val entities = database.workoutDao()
                .getWorkoutsBetween(fromDate, toDate)
                .first()
            
            val workouts = entities.map { it.toDomain() }
            Result.Success(workouts)
        } catch (e: Exception) {
            Result.Error(AppError.UnknownError("Failed to fetch workouts", e))
        }
    }
    
    override suspend fun getWorkoutById(id: String): Result<WorkoutEntry> {
        return try {
            val response = workoutApi.getWorkoutById(id)
            if (response.isSuccessful && response.body() != null) {
                Result.Success(response.body()!!.toDomain())
            } else {
                Result.Error(AppError.NotFoundError("Workout not found"))
            }
        } catch (e: Exception) {
            Result.Error(AppError.NetworkError(e.message ?: "Network error"))
        }
    }
    
    override suspend fun syncPendingWorkouts(): Result<Unit> {
        return try {
            val unsynced = database.workoutDao().getUnsyncedWorkouts()
            
            unsynced.forEach { entity ->
                try {
                    val workout = entity.toDomain()
                    val dto = workout.toDto()
                    val response = workoutApi.createWorkout(dto)
                    
                    if (response.isSuccessful && response.body() != null) {
                        val syncedWorkout = response.body()!!.toDomain()
                        // Update entity with server ID
                        val updatedEntity = entity.copy(
                            id = syncedWorkout.id,
                            synced = true
                        )
                        database.workoutDao().updateWorkout(updatedEntity)
                    }
                } catch (e: Exception) {
                }
            }
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(AppError.UnknownError("Sync failed", e))
        }
    }
}