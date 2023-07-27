package com.example.goat.presentation.history

import com.google.firebase.Timestamp

data class Challenge(
    val createdAt: Timestamp,
    val players: List<PlayerChallenge>,
    val quotes: List<QuoteChallenge>
)
