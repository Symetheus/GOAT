package com.example.goat.presentation.profile

import com.example.goat.domain.model.Character
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.model.Quote
import com.example.goat.domain.model.User

data class UiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val downloadUrl: String? = "",
    val error: String = ""
)