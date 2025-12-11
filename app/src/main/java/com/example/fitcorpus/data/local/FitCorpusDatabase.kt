package com.example.fitcorpus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        WorkoutEntryEntity::class,
        DietEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class FitCorpusDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun dietDao(): DietDao
}