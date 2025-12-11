package com.example.fitcorpus.domain.usecase.trainer

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Trainer
import com.example.fitcorpus.domain.repository.TrainerRepository
import javax.inject.Inject

class GetTrainersUseCase @Inject constructor(
    private val trainerRepository: TrainerRepository
) {
    suspend operator fun invoke(
        location: String? = null,
        priceMin: Int? = null,
        priceMax: Int? = null,
        gender: String? = null,
        mode: String? = null,
        sort: String? = null,
        page: Int = 0,
        size: Int = 20
    ): Result<List<Trainer>> {
        return trainerRepository.getTrainers(
            location, priceMin, priceMax, gender, mode, sort, page, size
        )
    }
}