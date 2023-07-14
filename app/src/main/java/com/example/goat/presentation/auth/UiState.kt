package com.example.goat.presentation.auth

import com.example.goat.domain.model.User

data class UiState(
    val isSignInFormVisible: Boolean = true,
    val isLoading: Boolean = false,
    val user: User? = null,
    val downloadUrl: String? = "",
    val error: String = ""
)