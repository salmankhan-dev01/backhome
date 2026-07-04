package com.example.backhome.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.backhome.presentation.navigation.Route
import com.example.backhome.util.Result

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel(),
) {

    val state by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }


    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

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
                text = "Create Account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Create your account to get started",
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
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
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
                            if (it.isNotEmpty() && it.length < 6)
                                "Password must be 6 to 10 characters"
                            else
                                ""

                        if (confirmPassword.isNotEmpty()) {
                            confirmPasswordError =
                                if (confirmPassword != password)
                                    "Passwords do not match"
                                else
                                    ""
                        }

                    }

                },
                label = {
                    Text("Password")
                },
                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {

                    val image =
                        if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff

                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription =
                                if (passwordVisible)
                                    "Hide Password"
                                else
                                    "Show Password"
                        )
                    }
                },
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

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {

                    if (it.length <= 10) {

                        confirmPassword = it

                        confirmPasswordError =
                            if (it.isNotEmpty() && it != password)
                                "Passwords do not match"
                            else
                                ""

                    }

                },
                label = {
                    Text("Confirm Password")
                },
                visualTransformation =
                    if (confirmPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                trailingIcon = {

                    val image =
                        if (confirmPasswordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff

                    IconButton(
                        onClick = {
                            confirmPasswordVisible = !confirmPasswordVisible
                        }
                    ) {
                        Icon(
                            imageVector = image,
                            contentDescription =
                                if (confirmPasswordVisible)
                                    "Hide Password"
                                else
                                    "Show Password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPasswordError.isNotEmpty(),
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            if (confirmPasswordError.isNotEmpty()) {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = confirmPasswordError,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Start)
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {

                    when {

                        password.length !in 6..10 -> {
                            passwordError =
                                "Password must be 6 to 10 characters"
                        }

                        password != confirmPassword -> {
                            confirmPasswordError =
                                "Passwords do not match"
                        }

                        else -> {
                            viewModel.register(email, password)
                        }

                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {

                Text(
                    text = "Register",
                    fontSize = 18.sp
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
                        text = "Registration Successful",
                        color = MaterialTheme.colorScheme.primary
                    )

                }

                is Result.Failure -> {

                    Text(
                        text = currentState.message,
                        color = MaterialTheme.colorScheme.error
                    )

                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text("Already have an account?")

                TextButton(
                    onClick = {
                        navController.navigate(Route.LoginScreen.route)  {
                            popUpTo(Route.LoginScreen.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                ) {

                    Text("Login")

                }

            }

        }

    }

}