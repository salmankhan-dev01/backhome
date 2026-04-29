package com.example.backhome.presentation.navigation

sealed class Route(val route: String) {
    object HomeScreen : Route("home")
    object ProfileScreen : Route("profile")
}