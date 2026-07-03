package com.example.backhome.presentation.ProfileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.backhome.R
import com.example.backhome.presentation.navigation.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    var email by remember { mutableStateOf("Loading...") }
    var userid by remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid != null) {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .collection("login")
                .document("user")
                .get()
                .addOnSuccessListener { document ->
                    email = document.getString("email") ?: "No Email"
                    userid = document.getString("uid") ?: "No User ID"

                    if (userid.length > 10) {
                        userid = userid.substring(0, 10)
                    }
                }
                .addOnFailureListener {
                    email = "Failed to load"
                    userid = "Failed to load"
                }
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Profile")
                },

                navigationIcon = {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                }

            )

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 20.dp)
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.outline_account_circle_24),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "Email: $email",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(22.dp))

                Text(
                    text = "User ID: $userid",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            ProfileItemRow(
                title = "Register new person",
                onClick = {
                    // Navigate to Person Form
                     navController.navigate(Route.PersonForm.route)
                }
            )

            ProfileItemRow(
                title = "My Registered Persons",
                onClick = {
                    // Navigate to My Registered Persons Screen

                    navController.navigate(Route.MyRegisterPerson.route)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                    FirebaseAuth.getInstance().signOut()

                    navController.navigate("login") {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {

                Text("Logout")

            }

        }

    }

}

@Composable
fun ProfileItemRow(
    title: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )

        }

    }

}