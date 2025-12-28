package com.example.fitcorpus.domain.model

import java.time.LocalDate

data class DietEntry(
    val id: String? = null,
    val date: LocalDate,
    val calories: Int,
    val meal: MealType? = null,
    val mealName: String? = null,
    val portion: String? = null,
    val note: String? = null
)

enum class MealType {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}