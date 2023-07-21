package com.example.goat.presentation.challenge

import com.example.goat.domain.model.Challenge
import com.example.goat.domain.model.User

data class UiState(
    val isLoading: Boolean = false,
    val isCreated: Boolean = false,
    val isUserAuth: Boolean = false,
    val isFilled: Boolean = false,
    val isWaitingRoom: Boolean = false,
    val hadUserJoined: Boolean = false,
    val user: User? = null,
    val challenge: Challenge? = null,
    val error: String = "",
    val dynamicLink: String = "",
)
