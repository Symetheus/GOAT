package com.example.goat.presentation.quiz

import com.example.goat.domain.model.Quote
import com.example.goat.domain.model.User

data class UiState(
    val isLoading: Boolean = false,
    val quotes: List<Quote>? = null,
    val error: String = "",
    val score: Int = 0,
    val isFinished: Boolean = false,
    val user: User? = null
)