package com.example.fitcorpus.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.fitcorpus.data.local.FitCorpusDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fitcorpus_prefs")

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FitCorpusDatabase {
        return Room.databaseBuilder(
            context,
            FitCorpusDatabase::class.java,
            "fitcorpus_database"
        )
            .fallbackToDestructiveMigration() // TODO: Add migrations for production
            .build()
    }
}