package com.example.goat.presentation.auth

sealed class AuthEvent {
    data class OnLoginClicked(val email: String = "", val password: String = "") : AuthEvent()

    data class OnRegisterClicked(
        val email: String,
        val password: String,
        val passwordConfirmation: String
    ) : AuthEvent()

    object OnSwapFormClicked : AuthEvent()
}