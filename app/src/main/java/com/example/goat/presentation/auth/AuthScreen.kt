package com.example.goat.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.goat.presentation.Screen

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    AuthContent(
        uiState = uiState.value,
        viewModel = viewModel,
        onSwapFormClicked = { viewModel.onEventChanged(AuthEvent.OnSwapFormClicked) },
        navController = navController,
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
    navController: NavController,
) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Goat", style = MaterialTheme.typography.displayLarge)

                if (uiState.isSignInFormVisible) {
                    SignInForm(
                        uiState = uiState,
                        viewModel = viewModel,
                        navController = navController
                    )
                } else {
                    SignUpForm(uiState = uiState, viewModel = viewModel)
                }


                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { onSwapFormClicked() }) {
                    Text(
                        text =
                        if (uiState.isSignInFormVisible) "Je n'ai pas encore de compte. S'inscrire !"
                        else "J'ai déjà un compte. Se connecter !",
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
    navController: NavController,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Text(text = "Sign INNNNNN")

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
    )

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Mot de passe") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
    )

    when {
        uiState.isLoading -> {
            // Text(text = "Connexion en cours...")
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
            Text(text = "Bienvenue ${uiState.user.email}")
            print(Screen.HomeScreen.route)
            LaunchedEffect(Unit) {
                navController.navigate(Screen.HomeScreen.route)
            }
        }
    }

    Button(onClick = {
        viewModel.onEventChanged(AuthEvent.OnLoginClicked(email, password))
    }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Me connecter",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpForm(
    uiState: UiState,
    viewModel: AuthViewModel,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }

    Text(text = "Sign Up")

    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = "Email") },
        modifier = Modifier.fillMaxWidth(),
    )

    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Mot de passe") },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
    )

    OutlinedTextField(
        value = passwordConfirmation,
        onValueChange = { passwordConfirmation = it },
        label = { Text(text = "Confirmer le mot de passe") },
        modifier = Modifier.fillMaxWidth(),
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
        viewModel.createUserFirestore()
    }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "M'inscrire",
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() = AuthScreen(navController = NavController(LocalContext.current))