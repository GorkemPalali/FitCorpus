package com.example.fitcorpus.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DietDao {
    @Query("SELECT * FROM diet_entries WHERE date BETWEEN :from AND :to ORDER BY date DESC")
    fun getDietEntriesBetween(from: LocalDate, to: LocalDate): Flow<List<DietEntryEntity>>
    
    @Query("SELECT * FROM diet_entries WHERE date = :date")
    fun getDietEntriesByDate(date: LocalDate): Flow<List<DietEntryEntity>>
    
    @Query("SELECT * FROM diet_entries WHERE synced = 0")
    suspend fun getUnsyncedEntries(): List<DietEntryEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDietEntry(entry: DietEntryEntity)
    
    @Update
    suspend fun updateDietEntry(entry: DietEntryEntity)
    
    @Delete
    suspend fun deleteDietEntry(entry: DietEntryEntity)
}