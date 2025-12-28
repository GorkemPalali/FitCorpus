package com.example.fitcorpus.core.sync

import android.content.Context
import androidx.work.*
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val workManager: WorkManager by lazy {
        WorkManager.getInstance(context)
    }
    
    companion object {
        private const val WORKOUT_SYNC_TAG = "workout_sync"
        private const val DIET_SYNC_TAG = "diet_sync"
        private const val PERIODIC_SYNC_TAG = "periodic_sync"
        
        // Sync intervals
        private const val SYNC_INTERVAL_HOURS = 1L
        private const val FLEX_INTERVAL_MINUTES = 15L
    }
    
    /**
     * Start periodic sync for workouts and diet entries
     */
    fun startPeriodicSync() {
        // Workout sync
        val workoutSyncRequest = PeriodicWorkRequestBuilder<WorkoutSyncWorker>(
            SYNC_INTERVAL_HOURS, TimeUnit.HOURS,
            FLEX_INTERVAL_MINUTES, TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(WORKOUT_SYNC_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        // Diet sync
        val dietSyncRequest = PeriodicWorkRequestBuilder<DietSyncWorker>(
            SYNC_INTERVAL_HOURS, TimeUnit.HOURS,
            FLEX_INTERVAL_MINUTES, TimeUnit.MINUTES
        )
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .addTag(DIET_SYNC_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        // Enqueue unique periodic work
        workManager.enqueueUniquePeriodicWork(
            WORKOUT_SYNC_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workoutSyncRequest
        )
        
        workManager.enqueueUniquePeriodicWork(
            DIET_SYNC_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            dietSyncRequest
        )
    }
    
    /**
     * Trigger immediate sync for workouts
     */
    fun syncWorkoutsNow() {
        val request = OneTimeWorkRequestBuilder<WorkoutSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(WORKOUT_SYNC_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        workManager.enqueue(request)
    }
    
    /**
     * Trigger immediate sync for diet entries
     */
    fun syncDietEntriesNow() {
        val request = OneTimeWorkRequestBuilder<DietSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(DIET_SYNC_TAG)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()
        
        workManager.enqueue(request)
    }
    
    /**
     * Cancel all sync work
     */
    fun cancelAllSync() {
        workManager.cancelAllWorkByTag(WORKOUT_SYNC_TAG)
        workManager.cancelAllWorkByTag(DIET_SYNC_TAG)
    }
}

