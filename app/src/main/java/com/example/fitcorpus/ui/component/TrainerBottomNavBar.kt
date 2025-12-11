package com.example.fitcorpus.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fitcorpus.navigation.Screen

sealed class TrainerBottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Profile : TrainerBottomNavItem(
        route = Screen.PTProfile.route,
        title = "Profil",
        icon = Icons.Default.Person
    )
    
    object Students : TrainerBottomNavItem(
        route = Screen.PTStudents.route,
        title = "Öğrencilerim",
        icon = Icons.Default.People
    )
    
    object Packages : TrainerBottomNavItem(
        route = Screen.PTPackages.route,
        title = "Paketlerim",
        icon = Icons.Default.ShoppingBag
    )
    
    object Plans : TrainerBottomNavItem(
        route = Screen.PTPlans.route,
        title = "Planlarım",
        icon = Icons.Default.List
    )
    
    object Settings : TrainerBottomNavItem(
        route = Screen.PTSettings.route,
        title = "Ayarlar",
        icon = Icons.Default.Settings
    )
}

@Composable
fun TrainerBottomNavBar(
    navController: NavHostController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    
    val items = listOf(
        TrainerBottomNavItem.Profile,
        TrainerBottomNavItem.Students,
        TrainerBottomNavItem.Packages,
        TrainerBottomNavItem.Plans,
        TrainerBottomNavItem.Settings
    )
    
    NavigationBar {
        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}








