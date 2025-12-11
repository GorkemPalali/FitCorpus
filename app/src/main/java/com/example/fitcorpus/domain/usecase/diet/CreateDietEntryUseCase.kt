package com.example.fitcorpus.domain.usecase.diet

import com.example.fitcorpus.core.common.AppError
import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.DietEntry
import com.example.fitcorpus.domain.repository.DietRepository
import javax.inject.Inject

class CreateDietEntryUseCase @Inject constructor(
    private val dietRepository: DietRepository
) {
    suspend operator fun invoke(entry: DietEntry): Result<DietEntry> {
        if (entry.calories <= 0) {
            return Result.Error(
                AppError.ValidationError("Calories value must be a positive number",
                    "calories")
            )
        }
        
        return dietRepository.createDietEntry(entry)
    }
}