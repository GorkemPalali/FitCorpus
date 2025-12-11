package com.example.fitcorpus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fitcorpus.domain.model.UserRole
import com.example.fitcorpus.feature.athlete.diet.DietScreen
import com.example.fitcorpus.feature.athlete.discover.DiscoverScreen
import com.example.fitcorpus.feature.athlete.home.AthleteHomeScreen
import com.example.fitcorpus.feature.athlete.profile.AthleteProfileScreen
import com.example.fitcorpus.feature.athlete.programs.ProgramsScreen
import com.example.fitcorpus.feature.athlete.workout.WorkoutScreen
import com.example.fitcorpus.feature.auth.LoginScreen
import com.example.fitcorpus.feature.auth.RegisterScreen
import com.example.fitcorpus.feature.information.InformationScreen
import com.example.fitcorpus.feature.pt.codes.CodesScreen
import com.example.fitcorpus.feature.pt.packages.PackagesScreen
import com.example.fitcorpus.feature.pt.plans.PlansScreen
import com.example.fitcorpus.feature.pt.profile.PTProfileScreen
import com.example.fitcorpus.feature.pt.settings.PTSettingsScreen
import com.example.fitcorpus.feature.pt.students.StudentsScreen
import com.example.fitcorpus.feature.start.StartScreen
import com.example.fitcorpus.feature.trainer.TrainerProfileScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Start.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Start.route) {
            StartScreen(
                onPTSelected = {
                    navController.navigate(Screen.Register.route)
                },
                onAthleteSelected = {
                    navController.navigate(Screen.Register.route)
                },
                onSignInClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // TODO: Navigate based on role - check user role and navigate accordingly
                    navController.navigate(Screen.AthleteHome.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onForgotPasswordClick = {
                    // TODO: Navigate to forgot password screen
                },
                onGoogleSignInClick = {
                    // TODO: Implement Google Sign In
                },
                onICloudSignInClick = {
                    // TODO: Implement iCloud Sign In
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Information.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Information.route) {
            InformationScreen(
                onContinue = {
                    // TODO: Navigate based on role
                    navController.navigate(Screen.AthleteHome.route) {
                        popUpTo(Screen.Information.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Athlete Screens
        composable(Screen.AthleteHome.route) {
            AthleteHomeScreen(
                onNavigateToWorkout = {
                    navController.navigate(Screen.AthleteWorkout.route)
                },
                onNavigateToDiet = {
                    navController.navigate(Screen.AthleteDiet.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.AthleteProfile.route)
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
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.AthleteDiscover.route) {
            DiscoverScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTrainerProfile = { trainerId ->
                    navController.navigate(Screen.TrainerProfile.createRoute(trainerId))
                }
            )
        }
        
        composable(Screen.AthleteProfile.route) {
            AthleteProfileScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
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
        
        // PT Screens
        composable(Screen.PTProfile.route) {
            PTProfileScreen(
                onNavigateToPackages = {
                    // TODO: Get trainer ID from user
                    navController.navigate(Screen.PTPackages.route)
                }
            )
        }
        
        composable(Screen.PTPackages.route) {
            PackagesScreen(
                trainerId = "", // TODO: Get from user
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.PTPlans.route) {
            PlansScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.PTStudents.route) {
            StudentsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.PTCodes.route) {
            CodesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable(Screen.PTSettings.route) {
            PTSettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}




