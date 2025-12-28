package com.example.fitcorpus.data.repository

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.data.local.FitCorpusDatabase
import com.example.fitcorpus.data.mapper.toDomain
import com.example.fitcorpus.data.mapper.toDto
import com.example.fitcorpus.data.mapper.toEntity
import com.example.fitcorpus.data.remote.api.DietApi
import com.example.fitcorpus.domain.model.DietEntry
import com.example.fitcorpus.domain.repository.DietRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class DietRepositoryImpl @Inject constructor(
    private val dietApi: DietApi,
    private val database: FitCorpusDatabase
) : DietRepository {
    
    override suspend fun createDietEntry(entry: DietEntry): Result<DietEntry> {
        val entity = entry.toEntity()
        database.dietDao().insertDietEntry(entity)
        
        return try {
            val dto = entry.toDto()
            val response = dietApi.createDietEntry(dto)
            
            if (response.isSuccessful && response.body() != null) {
                val syncedEntry = response.body()!!.toDomain()
                val updatedEntity = entity.copy(
                    id = syncedEntry.id,
                    synced = true
                )
                database.dietDao().updateDietEntry(updatedEntity)
                Result.Success(syncedEntry)
            } else {
                Result.Success(entry)
            }
        } catch (e: Exception) {
            Result.Success(entry)
        }
    }
    
    override suspend fun getDietEntries(
        from: LocalDate?,
        to: LocalDate?,
        updatedSince: String?
    ): Result<List<DietEntry>> {
        return try {
            val response = dietApi.getDietEntries(
                from = from?.toString(),
                to = to?.toString(),
                updatedSince = updatedSince
            )
            
            if (response.isSuccessful && response.body() != null) {
                val entries = response.body()!!.map { it.toDomain() }
                entries.forEach { entry ->
                    val entity = entry.toEntity()
                    database.dietDao().insertDietEntry(entity)
                }
                Result.Success(entries)
            } else {
                getDietEntriesFromLocal(from, to)
            }
        } catch (e: Exception) {
            getDietEntriesFromLocal(from, to)
        }
    }
    
    private suspend fun getDietEntriesFromLocal(
        from: LocalDate?,
        to: LocalDate?
    ): Result<List<DietEntry>> {
        return try {
            val fromDate = from ?: LocalDate.now().minusMonths(1)
            val toDate = to ?: LocalDate.now()
            
            val entities = database.dietDao()
                .getDietEntriesBetween(fromDate, toDate)
                .first()
            
            val entries = entities.map { it.toDomain() }
            Result.Success(entries)
        } catch (e: Exception) {
            Result.Error(AppError.UnknownError("Failed to fetch diet entries", e))
        }
    }
    
    override suspend fun syncPendingEntries(): Result<Unit> {
        return try {
            val unsynced = database.dietDao().getUnsyncedEntries()
            
            unsynced.forEach { entity ->
                try {
                    val entry = entity.toDomain()
                    val dto = entry.toDto()
                    val response = dietApi.createDietEntry(dto)
                    
                    if (response.isSuccessful && response.body() != null) {
                        val syncedEntry = response.body()!!.toDomain()
                        val updatedEntity = entity.copy(
                            id = syncedEntry.id,
                            synced = true
                        )
                        database.dietDao().updateDietEntry(updatedEntity)
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