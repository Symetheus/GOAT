package com.example.goat.presentation.history

data class Challenge(
    val createdAt: String,
    val players: List<PlayerChallenge>,
    val quotes: List<QuoteChallenge>
)
