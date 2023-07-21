package com.example.goat.domain.model

import java.util.Date
import java.util.Timer

data class Challenge(
    val id: String?,
    val status: String, // created, pending, started, finished
    val quotes: List<Quote>?,
    val players: List<Player>,
    val createdAt: Date,
    val dynamicLink: String?,
)

data class Player(
    val id: String,
    val score: Int,
    val status: String?, // inProgress, finished or null (if not started)
    val timer: Timer?,
)
