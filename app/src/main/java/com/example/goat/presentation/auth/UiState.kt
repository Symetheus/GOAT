package com.example.goat.presentation.auth

import com.example.goat.domain.model.User

data class UiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)