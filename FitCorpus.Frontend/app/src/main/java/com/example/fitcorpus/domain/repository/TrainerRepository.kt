package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Package
import com.example.fitcorpus.domain.model.Trainer

interface TrainerRepository {
    suspend fun getTrainers(
        location: String? = null,
        priceMin: Int? = null,
        priceMax: Int? = null,
        gender: String? = null,
        mode: String? = null,
        sort: String? = null,
        page: Int = 0,
        size: Int = 20
    ): Result<List<Trainer>>
    
    suspend fun getTrainerById(id: String): Result<Trainer>
    suspend fun getTrainerPackages(trainerId: String): Result<List<Package>>
}