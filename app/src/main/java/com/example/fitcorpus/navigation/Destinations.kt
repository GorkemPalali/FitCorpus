package com.example.fitcorpus.navigation

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Login : Screen("login")
    object Register : Screen("register/{role}") {
        fun createRoute(role: String) = "register/$role"
    }
    object Information : Screen("information")
    
    // Athlete screens
    object AthleteHome : Screen("athlete/home")
    object AthleteTracking : Screen("athlete/tracking")
    object AthleteWorkout : Screen("athlete/workout")
    object AthleteDiet : Screen("athlete/diet")
    object AthletePrograms : Screen("athlete/programs")
    object AthleteDiscover : Screen("athlete/discover")
    object AthleteProfile : Screen("athlete/profile")
    object ExerciseDetail : Screen("exercise/detail/{exerciseId}") {
        fun createRoute(exerciseId: String) = "exercise/detail/$exerciseId"
    }
    object AllExercises : Screen("exercises/all/{exerciseType}") {
        fun createRoute(exerciseType: String) = "exercises/all/$exerciseType"
    }
    object TrainerProfile : Screen("trainer/profile/{trainerId}") {
        fun createRoute(trainerId: String) = "trainer/profile/$trainerId"
    }
    
    // PT screens
    object PTProfile : Screen("pt/profile")
    object PTPackages : Screen("pt/packages")
    object PTPlans : Screen("pt/plans")
    object PTStudents : Screen("pt/students")
    object PTCodes : Screen("pt/codes")
    object PTSettings : Screen("pt/settings")
}












