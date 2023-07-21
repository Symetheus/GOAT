package com.example.goat.presentation.contribution_quiz

import com.example.goat.domain.model.Quote

data class UiState (
    val isLoading: Boolean = false,
    val quotes: List<Quote>? = null,
    val error: String = "",
    val score: Int = 0,
    val isFinished: Boolean = false
)