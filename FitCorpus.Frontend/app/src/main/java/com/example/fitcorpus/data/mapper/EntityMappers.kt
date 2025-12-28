package com.example.fitcorpus.data.mapper

import com.example.fitcorpus.data.local.DietEntryEntity
import com.example.fitcorpus.data.local.WorkoutEntryEntity
import com.example.fitcorpus.domain.model.DietEntry
import com.example.fitcorpus.domain.model.MealType
import com.example.fitcorpus.domain.model.WorkoutEntry
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val listIntType: Type = Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
private val listFloatType: Type = Types.newParameterizedType(List::class.java, Float::class.javaObjectType)

private val listIntAdapter = moshi.adapter<List<Int>>(listIntType)
private val listFloatAdapter = moshi.adapter<List<Float>>(listFloatType)


fun WorkoutEntryEntity.toDomain(): WorkoutEntry {
    return WorkoutEntry(
        id = id,
        clientRequestId = clientRequestId,
        planId = planId,
        date = date,
        movement = movement,
        sets = sets,
        reps = parseJsonList(reps, listIntAdapter) ?: emptyList(),
        weight = parseJsonList(weight, listFloatAdapter) ?: emptyList(),
        rpe = rpe,
        assignedBy = assignedBy,
        restSeconds = restSeconds
    )
}

fun WorkoutEntry.toEntity(clientRequestId: String? = null): WorkoutEntryEntity {
    val requestId = clientRequestId ?: this.clientRequestId ?: java.util.UUID.randomUUID().toString()
    return WorkoutEntryEntity(
        clientRequestId = requestId,
        id = id,
        planId = planId,
        date = date,
        movement = movement,
        sets = sets,
        reps = listIntAdapter.toJson(reps),
        weight = listFloatAdapter.toJson(weight),
        rpe = rpe,
        assignedBy = assignedBy,
        restSeconds = restSeconds,
        synced = id != null
    )
}


fun DietEntryEntity.toDomain(): DietEntry {
    return DietEntry(
        id = id,
        date = date,
        calories = calories,
        meal = meal?.let { MealType.valueOf(it.uppercase()) },
        mealName = mealName,
        portion = portion,
        note = note
    )
}

fun DietEntry.toEntity(): DietEntryEntity {
    return DietEntryEntity(
        id = id,
        date = date,
        calories = calories,
        meal = meal?.name,
        mealName = mealName,
        portion = portion,
        note = note,
        synced = id != null
    )
}


private inline fun <reified T> parseJsonList(json: String, adapter: com.squareup.moshi.JsonAdapter<T>): T? {
    return try {
        adapter.fromJson(json)
    } catch (e: Exception) {
        null
    }
}