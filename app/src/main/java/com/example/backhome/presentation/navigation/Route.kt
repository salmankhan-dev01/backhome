package com.example.backhome.presentation.navigation

sealed class Route(val route: String) {

    object RegisterScreen:Route("register")
    object LoginScreen: Route("login")
    object HomeScreen : Route("home")
    object ProfileScreen : Route("profile")
    object PersonForm : Route("form")

}