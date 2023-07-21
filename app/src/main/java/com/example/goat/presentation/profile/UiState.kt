package com.example.goat.presentation.profile

import com.example.goat.domain.model.User

data class UiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val downloadUrl: String? = "",
    val error: String = ""
)