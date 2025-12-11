package com.example.fitcorpus.domain.usecase.diet

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.DietEntry
import com.example.fitcorpus.domain.repository.DietRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDietEntriesUseCase @Inject constructor(
    private val dietRepository: DietRepository
) {
    suspend operator fun invoke(
        from: LocalDate? = null,
        to: LocalDate? = null
    ): Result<List<DietEntry>> {
        return dietRepository.getDietEntries(from, to, null)
    }
}