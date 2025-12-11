package com.example.fitcorpus.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitcorpus.ui.component.TrainerBottomNavBar
import com.example.fitcorpus.feature.pt.packages.PackagesScreen
import com.example.fitcorpus.feature.pt.plans.PlansScreen
import com.example.fitcorpus.feature.pt.profile.PTProfileScreen
import com.example.fitcorpus.feature.pt.settings.PTSettingsScreen
import com.example.fitcorpus.feature.pt.students.StudentsScreen

@Composable
fun TrainerNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    Scaffold(
        bottomBar = {
            TrainerBottomNavBar(navController = navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.PTProfile.route
        ) {
            composable(Screen.PTProfile.route) {
                PTProfileScreen(
                    onNavigateToPackages = {
                        navController.navigate(Screen.PTPackages.route)
                    }
                )
            }
            
            composable(Screen.PTPackages.route) {
                PackagesScreen(
                    trainerId = "", // TODO: Get from user
                    onNavigateBack = { /* Navbar handles navigation */ }
                )
            }
            
            composable(Screen.PTPlans.route) {
                PlansScreen(
                    onNavigateBack = { /* Navbar handles navigation */ }
                )
            }
            
            composable(Screen.PTStudents.route) {
                StudentsScreen(
                    onNavigateBack = { /* Navbar handles navigation */ }
                )
            }
            
            composable(Screen.PTSettings.route) {
                PTSettingsScreen(
                    onNavigateBack = { /* Navbar handles navigation */ },
                    onLogout = onLogout
                )
            }
        }
    }
}
