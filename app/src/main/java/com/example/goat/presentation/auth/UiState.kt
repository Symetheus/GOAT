package com.example.goat.presentation.auth

import com.example.goat.domain.model.Character
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.model.Quote
import com.example.goat.domain.model.User

data class UiState(
    val isSignInFormVisible: Boolean = true,
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)