package com.example.goat.presentation.auth

sealed class AuthEvent {
    object OnLoginClicked : AuthEvent()

    object OnRegisterClicked : AuthEvent()

    object OnForgotPasswordClicked : AuthEvent()
}