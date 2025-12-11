package com.example.fitcorpus.domain.repository

import com.example.fitcorpus.core.common.Result
import com.example.fitcorpus.domain.model.Student
import com.example.fitcorpus.domain.model.WorkoutEntry
import com.example.fitcorpus.domain.model.DietEntry
import java.time.LocalDate

interface StudentRepository {
    suspend fun getStudents(): Result<List<Student>>
    suspend fun getStudentLogs(
        athleteId: String,
        from: LocalDate? = null,
        to: LocalDate? = null
    ): Result<StudentLogs>
    suspend fun assignPlan(
        athleteId: String,
        planId: String,
        date: LocalDate
    ): Result<Unit>
}

data class StudentLogs(
    val workouts: List<WorkoutEntry>,
    val diets: List<DietEntry>
)