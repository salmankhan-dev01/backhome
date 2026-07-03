package com.example.backhome.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.backhome.presentation.ProfileScreen.MyRegisterPerson
import com.example.backhome.presentation.form.PersonForm
import com.example.backhome.presentation.ProfileScreen.ProfileScreen
import com.example.backhome.presentation.auth.LoginScreen
import com.example.backhome.presentation.auth.RegisterScreen
import com.example.backhome.presentation.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val currentUser = FirebaseAuth.getInstance().currentUser

    val startDestination = if (currentUser != null) {
        Route.HomeScreen.route
    } else {
        Route.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
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
        composable(Route.MyRegisterPerson.route){
            MyRegisterPerson(navController)
        }
    }
}