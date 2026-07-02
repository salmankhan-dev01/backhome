package com.example.backhome.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.backhome.presentation.form.PersonForm
import com.example.backhome.presentation.ProfileScreen.ProfileScreen
import com.example.backhome.presentation.auth.LoginScreen
import com.example.backhome.presentation.auth.RegisterScreen
import com.example.backhome.presentation.missing.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.ProfileScreen.route
    ) {
        composable(Route.RegisterScreen.route){
            RegisterScreen(navController)
        }
        composable(Route.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(Route.HomeScreen.route) {
            HomeScreen(navController)
        }

        composable(Route.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(Route.PersonForm.route){
            PersonForm(navController)
        }
    }
}