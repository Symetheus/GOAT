package com.example.goat.presentation.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginClicked: (email: String, password: String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var isSignInFormVisible = true

    AuthContent(
        uiState = uiState.value,
        onLoginClicked = onLoginClicked,
        isSignInFormVisible = isSignInFormVisible,
        onSwapFormClicked = { isSignInFormVisible = !isSignInFormVisible },
    )

}

@Composable
fun AuthContent(
    uiState: UiState,
    onLoginClicked: (email: String, password: String) -> Unit,
    isSignInFormVisible: Boolean,
    onSwapFormClicked: () -> Unit,
) {
    if (isSignInFormVisible) {
        SignInForm(
            onLoginClicked = onLoginClicked,
        )
    } else {
        Text(text = "Sign Up")
        // SignUpForm(onLoginClicked = onLoginClicked,)
    }

    Button(onClick = { onSwapFormClicked() }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text =
            if (isSignInFormVisible) "Je n'ai pas encore de compte. S'inscrire !"
            else "J'ai déjà un compte. Se connecter !",
        )
    }
}

@Composable
fun SignInForm(
    onLoginClicked: (email: String, password: String) -> Unit,
) {
    Text(text = "Sign INNNNNN")
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() = AuthScreen(onLoginClicked = { _, _ -> })