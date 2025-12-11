package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.DietEntry
import java.time.LocalDate

interface DietRepository {
    suspend fun createDietEntry(entry: DietEntry): Result<DietEntry>
    suspend fun getDietEntries(
        from: LocalDate? = null,
        to: LocalDate? = null,
        updatedSince: String? = null
    ): Result<List<DietEntry>>
    suspend fun syncPendingEntries(): Result<Unit>
}