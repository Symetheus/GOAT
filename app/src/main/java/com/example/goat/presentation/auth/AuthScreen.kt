package com.example.goat.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AuthContent(
        uiState = uiState.value,
        viewModel = viewModel,
        onSwapFormClicked = { viewModel.onEventChanged(AuthEvent.OnSwapFormClicked) },
        onLoginSuccess = onLoginSuccess,
    )

    // check if user is already logged in
    viewModel.onEventChanged(AuthEvent.GetUser)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthContent(
    uiState: UiState,
    viewModel: AuthViewModel,
    onSwapFormClicked: () -> Unit,
    onLoginSuccess: (String) -> Unit,
) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.LightGray)
                    .padding(16.dp),

                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Goat", style = MaterialTheme.typography.displayLarge)

                if (uiState.isSignInFormVisible) {
                    SignInForm(
                        uiState = uiState,
                        viewModel = viewModel,
                        onLoginSuccess = onLoginSuccess
                    )
                } else {
                    SignUpForm(uiState = uiState, viewModel = viewModel)
                }

                TextButton(
                    onClick = { onSwapFormClicked() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text =
                        if (uiState.isSignInFormVisible) "I don't have an account yet. Sign up now !"
                        else "I already have an account. Sign in now !",
                    )
                }
            }

        })
}

@ExperimentalMaterial3Api
@Composable
fun SignInForm(
    uiState: UiState,
    viewModel: AuthViewModel,
    onLoginSuccess: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Text(text = "Sign IN", modifier = Modifier.padding(top = 50.dp))

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
    )

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        visualTransformation = PasswordVisualTransformation(),
    )

    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )

        }

        uiState.error.isNotBlank() -> {
            Text(text = uiState.error, color = MaterialTheme.colorScheme.error)
        }

        uiState.user != null -> {
            Text(text = "Welcome ${uiState.user.email}")
            LaunchedEffect(Unit) {
                onLoginSuccess(uiState.user.id)
            }
        }
    }

    Button(onClick = {
        viewModel.onEventChanged(AuthEvent.OnLoginClicked(email, password))
    }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Login",
        )
    }
}

@Composable
fun SignUpForm(
    uiState: UiState,
    viewModel: AuthViewModel,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    Text(text = "Sign UP", modifier = Modifier.padding(top = 20.dp))

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp),
    )

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password") },
        modifier = Modifier
            .fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
    )

    OutlinedTextField(
        value = passwordConfirmation,
        onValueChange = { passwordConfirmation = it },
        label = { Text(text = "Confirm password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 32.dp),
        visualTransformation = PasswordVisualTransformation(),
    )

    when {
        uiState.isLoading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )

        }

        uiState.error.isNotBlank() -> {
            Text(text = uiState.error, color = MaterialTheme.colorScheme.error)
        }
    }

    Button(onClick = {
        viewModel.onEventChanged(AuthEvent.OnRegisterClicked(email, password, passwordConfirmation))
    }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Sign up",
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() = AuthScreen(onLoginSuccess = {})