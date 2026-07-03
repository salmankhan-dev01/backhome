package com.example.backhome.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.backhome.presentation.navigation.Route
import com.example.backhome.util.Result

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {

    val context = LocalContext.current

    val state by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is Result.Success) {
            navController.navigate(Route.HomeScreen.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome Back", fontSize = 30.sp, fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Login to continue your journey",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text("Email")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {

                    if (it.length <= 10) {

                        password = it

                        passwordError =
                            if (it.isNotEmpty() && it.length < 6) "Password must be 6 to 10 characters"
                            else ""

                    }

                },
                label = {
                    Text("Password")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = passwordError.isNotEmpty(),
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            if (passwordError.isNotEmpty()) {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = passwordError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Start)
                )

            }

            Spacer(modifier = Modifier.height(6.dp))

            TextButton(
                onClick = {
                    Toast.makeText(
                        context,
                        "Forgot Password Not Working",
                        Toast.LENGTH_SHORT
                    ).show()
                }, enabled = true, modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot Password?")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (password.length in 6..10) {

                        viewModel.login(email, password)

                    } else {

                        passwordError = "Password must be 6 to 10 characters"

                    }

                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Text(
                    text = "Login", fontSize = 18.sp
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            when (val currentState = state) {

                is Result.Idle -> {}

                is Result.Loading -> {

                    CircularProgressIndicator()

                }

                is Result.Success -> {

                    Text(
                        text = "Login Successful", color = MaterialTheme.colorScheme.primary
                    )

                }

                is Result.Failure -> {

                    Text(
                        text = currentState.message, color = MaterialTheme.colorScheme.error
                    )

                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("New User?")

                TextButton(
                    onClick = {
                        navController.navigate(Route.RegisterScreen.route) {
                            popUpTo(Route.RegisterScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }

                    }, enabled = true
                ) {

                    Text("Create Account")

                }

            }

        }

    }

}