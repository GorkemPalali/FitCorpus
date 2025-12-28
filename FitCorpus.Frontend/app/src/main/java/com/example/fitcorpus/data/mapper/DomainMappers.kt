package com.example.fitcorpus.data.mapper

import com.example.fitcorpus.data.remote.dto.*
import com.example.fitcorpus.domain.model.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Auth Mappers
fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        surname = surname,
        email = email,
        role = UserRole.valueOf(role.uppercase()),
        height = height,
        weight = weight,
        gender = gender,
        city = city,
        age = age,
        photoUrl = photoUrl
    )
}

fun TokenResponseDto.toDomain(): com.example.fitcorpus.domain.repository.TokenResponse {
    return com.example.fitcorpus.domain.repository.TokenResponse(
        accessToken = accessToken,
        refreshToken = refreshToken ?: "",
        role = role
    )
}

// Trainer Mappers
fun TrainerDto.toDomain(): Trainer {
    return Trainer(
        id = id,
        name = name,
        surname = surname,
        photoUrl = photoUrl,
        ratingAvg = ratingAvg,
        priceFrom = priceFrom,
        modes = modes.map { mode ->
            when (mode.uppercase()) {
                "ONLINE" -> TrainingMode.ONLINE
                "FACE_TO_FACE", "F2F" -> TrainingMode.FACE_TO_FACE
                "BOTH" -> TrainingMode.BOTH
                else -> TrainingMode.ONLINE
            }
        },
        bio = bio,
        achievements = achievements,
        gender = gender,
        location = location
    )
}

fun PackageDto.toDomain(): Package {
    return Package(
        id = id,
        trainerId = trainerId,
        title = title,
        description = description,
        price = price,
        durationWeeks = durationWeeks,
        mode = when (mode.uppercase()) {
            "ONLINE" -> TrainingMode.ONLINE
            "FACE_TO_FACE", "F2F" -> TrainingMode.FACE_TO_FACE
            "BOTH" -> TrainingMode.BOTH
            else -> TrainingMode.ONLINE
        },
        services = services
    )
}

// Workout Mappers
fun WorkoutEntryDto.toDomain(): WorkoutEntry {
    return WorkoutEntry(
        id = id,
        clientRequestId = clientRequestId,
        planId = planId,
        date = LocalDate.parse(date),
        movement = movement,
        sets = sets,
        reps = reps,
        weight = weight,
        rpe = rpe,
        assignedBy = assignedBy,
        restSeconds = restSeconds
    )
}

fun WorkoutEntry.toDto(): WorkoutEntryDto {
    return WorkoutEntryDto(
        id = id,
        clientRequestId = clientRequestId,
        planId = planId,
        date = date.toString(),
        movement = movement,
        sets = sets,
        reps = reps,
        weight = weight,
        rpe = rpe,
        assignedBy = assignedBy,
        restSeconds = restSeconds
    )
}

// Diet Mappers
fun DietEntryDto.toDomain(): DietEntry {
    return DietEntry(
        id = id,
        date = LocalDate.parse(date),
        calories = calories,
        meal = meal?.let { MealType.valueOf(it.uppercase()) },
        mealName = mealName,
        portion = portion,
        note = note
    )
}

fun DietEntry.toDto(): DietEntryDto {
    return DietEntryDto(
        id = id,
        date = date.toString(),
        calories = calories,
        meal = meal?.name,
        mealName = mealName,
        portion = portion,
        note = note
    )
}

// Match Mapper
fun MatchDto.toDomain(): Match {
    return Match(
        id = id,
        athleteId = athleteId,
        trainerId = trainerId,
        packageId = packageId,
        status = MatchStatus.valueOf(status.uppercase()),
        createdAt = createdAt
    )
}

// Plan Mappers
fun PlanDto.toDomain(): Plan {
    return Plan(
        id = id ?: "",
        trainerId = trainerId,
        name = name,
        type = PlanType.valueOf(type.uppercase()),
        exercises = exercises?.map { it.toDomain() },
        description = description
    )
}

fun Plan.toDto(): PlanDto {
    return PlanDto(
        id = id,
        trainerId = trainerId,
        name = name,
        type = type.name,
        exercises = exercises?.map { it.toDto() },
        description = description
    )
}

fun PlanExerciseDto.toDomain(): com.example.fitcorpus.domain.model.PlanExercise {
    return com.example.fitcorpus.domain.model.PlanExercise(
        exerciseName = exerciseName,
        sets = sets,
        reps = reps,
        weight = weight,
        restSeconds = restSeconds
    )
}

fun com.example.fitcorpus.domain.model.PlanExercise.toDto(): PlanExerciseDto {
    return PlanExerciseDto(
        exerciseName = exerciseName,
        sets = sets,
        reps = reps,
        weight = weight,
        restSeconds = restSeconds
    )
}

// Student Mappers
fun StudentDto.toDomain(): com.example.fitcorpus.domain.model.Student {
    return com.example.fitcorpus.domain.model.Student(
        id = id,
        name = name,
        surname = surname,
        photoUrl = photoUrl,
        matchId = matchId
    )
}

fun StudentLogsDto.toDomain(): com.example.fitcorpus.domain.repository.StudentLogs {
    return com.example.fitcorpus.domain.repository.StudentLogs(
        workouts = workouts.map { it.toDomain() },
        diets = diets.map { it.toDomain() }
    )
}

// Code Mappers
fun CodeDto.toDomain(): com.example.fitcorpus.domain.model.OnboardingCode {
    return com.example.fitcorpus.domain.model.OnboardingCode(
        code = code,
        trainerId = trainerId,
        status = com.example.fitcorpus.domain.model.CodeStatus.valueOf(status.uppercase()),
        expiresAt = expiresAt?.let { java.time.Instant.parse(it) },
        paidByTrainer = paidByTrainer
    )
}

fun PurchaseCodeResponseDto.toDomain(): com.example.fitcorpus.domain.repository.PurchaseCodeResponse {
    return com.example.fitcorpus.domain.repository.PurchaseCodeResponse(
        codes = codes.map { it.toDomain() }
    )
}