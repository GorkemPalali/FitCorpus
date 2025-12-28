package com.example.fitcorpus.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout_entries WHERE date BETWEEN :from AND :to ORDER BY date DESC")
    fun getWorkoutsBetween(from: LocalDate, to: LocalDate): Flow<List<WorkoutEntryEntity>>
    
    @Query("SELECT * FROM workout_entries WHERE date = :date")
    fun getWorkoutsByDate(date: LocalDate): Flow<List<WorkoutEntryEntity>>
    
    @Query("SELECT * FROM workout_entries WHERE synced = 0")
    suspend fun getUnsyncedWorkouts(): List<WorkoutEntryEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntryEntity)
    
    @Update
    suspend fun updateWorkout(workout: WorkoutEntryEntity)
    
    @Delete
    suspend fun deleteWorkout(workout: WorkoutEntryEntity)
}