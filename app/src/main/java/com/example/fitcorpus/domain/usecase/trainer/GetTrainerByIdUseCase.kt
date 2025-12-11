package com.example.fitcorpus.domain.usecase.trainer

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Trainer
import com.example.fitcorpus.domain.repository.TrainerRepository
import javax.inject.Inject

class GetTrainerByIdUseCase @Inject constructor(
    private val trainerRepository: TrainerRepository
) {
    suspend operator fun invoke(trainerId: String): Result<Trainer> {
        return trainerRepository.getTrainerById(trainerId)
    }
}