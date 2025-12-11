package com.example.fitcorpus.core.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.fitcorpus.domain.usecase.workout.SyncWorkoutsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class WorkoutSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncWorkoutsUseCase: SyncWorkoutsUseCase
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            when (val result = syncWorkoutsUseCase()) {
                is com.example.fitcorpus.core.common.Result.Success -> {
                    Result.success()
                }
                is com.example.fitcorpus.core.common.Result.Error -> {
                    // Retry with exponential backoff
                    Result.retry()
                }
                else -> Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}





