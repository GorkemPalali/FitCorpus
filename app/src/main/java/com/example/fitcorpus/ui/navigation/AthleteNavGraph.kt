package com.example.fitcorpus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fitcorpus.ui.component.AthleteBottomNavBar
import com.example.fitcorpus.ui.screens.athlete.diet.DietScreen
import com.example.fitcorpus.ui.screens.athlete.discover.DiscoverScreen
import com.example.fitcorpus.ui.screens.athlete.home.AthleteHomeScreen
import com.example.fitcorpus.ui.screens.athlete.profile.AthleteProfileScreen
import com.example.fitcorpus.ui.screens.athlete.programs.ProgramsScreen
import com.example.fitcorpus.ui.screens.athlete.tracking.TrackingScreen
import com.example.fitcorpus.ui.screens.athlete.workout.WorkoutScreen
import com.example.fitcorpus.ui.screens.trainer.TrainerProfileScreen
import com.example.fitcorpus.domain.model.ExerciseType

@Composable
fun AthleteNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    Scaffold(
        bottomBar = {
            AthleteBottomNavBar(navController = navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.AthleteHome.route
        ) {
            composable(Screen.AthleteHome.route) {
                AthleteHomeScreen(
                    onNavigateToWorkout = {
                        navController.navigate(Screen.AthleteTracking.route)
                    },
                    onNavigateToDiet = {
                        navController.navigate(Screen.AthleteTracking.route)
                    },
                    onNavigateToProfile = {
                        // Navigate to profile without popUpTo to avoid navigation loop
                        // When user clicks profile from home, we don't want to pop home from stack
                        // Bottom nav bar will handle the selection state correctly
                        navController.navigate(Screen.AthleteProfile.route) {
                            launchSingleTop = true
                            // Don't use popUpTo here to avoid state conflicts
                        }
                    },
                    onNavigateToExerciseDetail = { exerciseId ->
                        navController.navigate(Screen.ExerciseDetail.createRoute(exerciseId))
                    },
                    onNavigateToAllExercises = { exerciseType ->
                        navController.navigate(Screen.AllExercises.createRoute(exerciseType.name))
                    }
                )
            }
            
            composable(Screen.AthleteTracking.route) {
                TrackingScreen(
                    onNavigateToWorkout = {
                        navController.navigate(Screen.AthleteWorkout.route)
                    },
                    onNavigateToDiet = {
                        navController.navigate(Screen.AthleteDiet.route)
                    }
                )
            }
            
            composable(Screen.AthleteWorkout.route) {
                WorkoutScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(Screen.AthleteDiet.route) {
                DietScreen(
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(Screen.AthletePrograms.route) {
                ProgramsScreen(
                    onNavigateBack = { /* Navbar handles navigation */ }
                )
            }
            
            composable(Screen.AthleteDiscover.route) {
                DiscoverScreen(
                    onNavigateBack = { /* Navbar handles navigation */ },
                    onNavigateToTrainerProfile = { trainerId ->
                        navController.navigate(Screen.TrainerProfile.createRoute(trainerId))
                    }
                )
            }
            
            composable(Screen.AthleteProfile.route) {
                AthleteProfileScreen(
                    onNavigateBack = { /* Navbar handles navigation */ },
                    onLogout = onLogout
                )
            }
            
            composable(
                route = Screen.TrainerProfile.route,
                arguments = listOf(navArgument("trainerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val trainerId = backStackEntry.arguments?.getString("trainerId") ?: ""
                TrainerProfileScreen(
                    trainerId = trainerId,
                    onNavigateBack = { navController.popBackStack() },
                    onPackageClick = { packageId ->
                        // TODO: Navigate to package detail or purchase
                    }
                )
            }
            
            composable(
                route = Screen.ExerciseDetail.route,
                arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getString("exerciseId") ?: ""
                // TODO: Create ExerciseDetailScreen
                ExerciseDetailScreen(
                    exerciseId = exerciseId,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable(
                route = Screen.AllExercises.route,
                arguments = listOf(navArgument("exerciseType") { type = NavType.StringType })
            ) { backStackEntry ->
                val exerciseTypeStr = backStackEntry.arguments?.getString("exerciseType") ?: ""
                val exerciseType = try {
                    ExerciseType.valueOf(exerciseTypeStr)
                } catch (e: IllegalArgumentException) {
                    ExerciseType.WORKOUT
                }
                // TODO: Create AllExercisesScreen
                AllExercisesScreen(
                    exerciseType = exerciseType,
                    onNavigateBack = { navController.popBackStack() },
                    onExerciseClick = { exerciseId ->
                        navController.navigate(Screen.ExerciseDetail.createRoute(exerciseId))
                    }
                )
            }
        }
    }
}

// Placeholder screens - TODO: Implement properly
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseDetailScreen(
    exerciseId: String,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exercise Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("Exercise Detail: $exerciseId")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllExercisesScreen(
    exerciseType: ExerciseType,
    onNavigateBack: () -> Unit,
    onExerciseClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All ${exerciseType.name} Exercises") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("All ${exerciseType.name} Exercises")
        }
    }
}
