package com.example.backhome.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.backhome.presentation.ProfileScreen.ProfileScreen
import com.example.backhome.presentation.missing.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.route
    ) {
        composable(Route.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(Route.ProfileScreen.route) {
            ProfileScreen(navController)
        }
    }
}