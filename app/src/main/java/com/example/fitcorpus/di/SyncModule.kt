package com.example.fitcorpus.di

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.fitcorpus.core.sync.SyncManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SyncModule {
    
    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(
        workerFactory: HiltWorkerFactory
    ): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideSyncManager(
        @ApplicationContext context: Context
    ): SyncManager {
        return SyncManager(context)
    }
}