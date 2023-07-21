package com.example.goat.presentation.player

import com.example.goat.domain.model.User

data class UiState(
    val isLoading: Boolean = false,
    val listUser: List<User>? = null,
    val error: String = ""
)