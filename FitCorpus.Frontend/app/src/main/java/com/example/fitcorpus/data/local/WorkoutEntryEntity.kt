package com.example.fitcorpus.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "workout_entries")
data class WorkoutEntryEntity(
    @PrimaryKey val clientRequestId: String,
    val id: String? = null,
    val planId: String? = null,
    val date: LocalDate,
    val movement: String,
    val sets: Int,
    val reps: String,
    val weight: String,
    val rpe: Int? = null,
    val assignedBy: String? = null,
    val restSeconds: Int? = null,
    val synced: Boolean = false
)