package com.example.fitcorpus.domain.usecase.trainer

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Package
import com.example.fitcorpus.domain.repository.TrainerRepository
import javax.inject.Inject

class GetTrainerPackagesUseCase @Inject constructor(
    private val trainerRepository: TrainerRepository
) {
    suspend operator fun invoke(trainerId: String): Result<List<Package>> {
        return trainerRepository.getTrainerPackages(trainerId)
    }
}