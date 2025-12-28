package com.example.fitcorpus

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.fitcorpus.core.sync.SyncManager
import com.example.fitcorpus.core.util.Logger
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class FitCorpusApplication : Application() {
    
    @Inject
    lateinit var syncManager: SyncManager
    
    @Inject
    lateinit var workManagerConfiguration: Configuration
    
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.DebugTree()) // TODO: Replace with production logging
        }

        WorkManager.initialize(this, workManagerConfiguration)
        
        Logger.i("FitCorpusApplication initialized")

        syncManager.startPeriodicSync()
    }
}