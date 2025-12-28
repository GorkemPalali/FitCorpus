package com.example.fitcorpus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fitcorpus.domain.model.UserRole
import com.example.fitcorpus.ui.screens.auth.LoginScreen
import com.example.fitcorpus.ui.screens.auth.RegisterScreen
import com.example.fitcorpus.ui.screens.information.InformationScreen
import com.example.fitcorpus.ui.screens.start.StartScreen

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {
        composable(Screen.Start.route) {
            StartScreen(
                onPTSelected = {
                    navController.navigate(Screen.Register.createRoute("TRAINER"))
                },
                onAthleteSelected = {
                    navController.navigate(Screen.Register.createRoute("ATHLETE"))
                },
                onSignInClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.createRoute("ATHLETE"))
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
        
        composable(
            route = Screen.Register.route,
            arguments = listOf(
                navArgument("role") { defaultValue = "ATHLETE" }
            )
        ) { backStackEntry ->
            val roleString = backStackEntry.arguments?.getString("role") ?: "ATHLETE"
            val role = try {
                UserRole.valueOf(roleString)
            } catch (e: IllegalArgumentException) {
                UserRole.ATHLETE
            }
            
            RegisterScreen(
                selectedRole = role,
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
                },
                onGoogleSignInClick = {
                    // TODO: Implement Google Sign In
                },
                onICloudSignInClick = {
                    // TODO: Implement iCloud Sign In
                }
            )
        }
        
        composable(Screen.Information.route) {
            InformationScreen(
                onContinue = onRegisterSuccess
            )
        }
    }
}
