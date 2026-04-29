package com.example.backhome.presentation.missing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.backhome.presentation.components.PersonCard
import com.example.backhome.presentation.navigation.Route

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PersonCard(
            imageUrl ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsuoQ7vB5ArqFQUtjamvtRnM5bD9mHegSmJg&s",
            name = "Yalina",
            age = "45",
            fatherName ="unknown",
            description = "Nothing",
            place ="Lucknow",
            address = "skdjf",
            modifier = Modifier
        )
        PersonCard(
            imageUrl ="",
            name = "Yalina",
            age = "45",
            fatherName ="unknown",
            description = "Nothing",
            place ="Lucknow",
            address = "skdjf",
            modifier = Modifier
        )
    }
}