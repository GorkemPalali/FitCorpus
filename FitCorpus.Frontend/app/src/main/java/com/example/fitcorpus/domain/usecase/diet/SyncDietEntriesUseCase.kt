package com.example.fitcorpus.domain.usecase.diet

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.repository.DietRepository
import javax.inject.Inject

class SyncDietEntriesUseCase @Inject constructor(
    private val dietRepository: DietRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return dietRepository.syncPendingEntries()
    }
}