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

sealed class AthleteBottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : AthleteBottomNavItem(
        route = Screen.AthleteHome.route,
        title = "Ana Sayfa",
        icon = Icons.Default.Home
    )
    
    object Tracking : AthleteBottomNavItem(
        route = Screen.AthleteTracking.route,
        title = "Takip",
        icon = Icons.Default.FitnessCenter
    )
    
    object Programs : AthleteBottomNavItem(
        route = Screen.AthletePrograms.route,
        title = "Programlar",
        icon = Icons.Default.List
    )
    
    object Discover : AthleteBottomNavItem(
        route = Screen.AthleteDiscover.route,
        title = "PT Pazarı",
        icon = Icons.Default.Search
    )
    
    object Profile : AthleteBottomNavItem(
        route = Screen.AthleteProfile.route,
        title = "Profil",
        icon = Icons.Default.Person
    )
}

@Composable
fun AthleteBottomNavBar(
    navController: NavHostController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    
    val items = listOf(
        AthleteBottomNavItem.Home,
        AthleteBottomNavItem.Tracking,
        AthleteBottomNavItem.Programs,
        AthleteBottomNavItem.Discover,
        AthleteBottomNavItem.Profile
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
                        if (item.route == Screen.AthleteHome.route) {
                            // For Home, always pop back to it instead of navigating
                            // This avoids state restore issues that cause navigation loops
                            // Home is always in the stack as it's the start destination
                            navController.popBackStack(Screen.AthleteHome.route, inclusive = false)
                        } else {
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
                }
            )
        }
    }
}


