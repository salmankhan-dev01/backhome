package com.example.backhome.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.backhome.util.Result

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {}
) {
    val state by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is Result.Success) {
            navController.navigate("profile") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                if (it.length <= 10) {
                    password = it
                    passwordError =
                        if (it.isNotEmpty() && it.length < 6)
                            "Password must be 6 to 10 characters"
                        else ""
                }
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError.isNotEmpty()
        )

        if (passwordError.isNotEmpty()) {
            Text(passwordError, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                if (it.length <= 10) {
                    confirmPassword = it
                    confirmPasswordError =
                        if (password != it)
                            "Passwords do not match"
                        else ""
                }
            },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = confirmPasswordError.isNotEmpty()
        )

        if (confirmPasswordError.isNotEmpty()) {
            Text(confirmPasswordError, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    password.length !in 6..10 -> {
                        passwordError = "Password must be 6 to 10 characters"
                    }

                    password != confirmPassword -> {
                        confirmPasswordError = "Passwords do not match"
                    }

                    else -> {
                        viewModel.register(email, password)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Login")
        }

        when (val currentState = state) {
            is Result.Idle -> {}

            is Result.Loading -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is Result.Success -> Text("Registration Successful")

            is Result.Failure -> Text(
                text = currentState.message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}