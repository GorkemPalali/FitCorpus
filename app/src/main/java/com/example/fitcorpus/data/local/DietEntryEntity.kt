package com.example.fitcorpus.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "diet_entries")
data class DietEntryEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val id: String? = null,
    val date: LocalDate,
    val calories: Int,
    val meal: String? = null,
    val mealName: String? = null,
    val portion: String? = null,
    val note: String? = null,
    val synced: Boolean = false
)