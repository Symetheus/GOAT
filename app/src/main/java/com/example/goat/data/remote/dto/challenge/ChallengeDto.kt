package com.example.goat.data.remote.dto.challenge

import com.example.goat.data.remote.dto.quote.QuoteDto
import com.example.goat.domain.model.Challenge
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class ChallengeDto (
    val id: String,
    val status: String,
    val quotes: List<>, // TODO: QuoteDto??
    val players: List<PlayerDto>,
    val createdAt: Date = Date(),
) {
    constructor() : this("", "", emptyList(), emptyList(), Date())
    @Exclude
    fun toMap(): Map<String, Any?> {
        println("IN tomapo")
        return mapOf(
            "players" to players,
            "quotes" to quotes,
            "createdAt" to createdAt,
        )
    }
}

fun ChallengeDto.toChallenge(): Challenge {
    return Challenge(
        id = id,
        status = status,
        quotes = quotes.map { it.toQuote() },
        players = players.map { it.toPlayer() },
        createdAt = createdAt,
        dynamicLink = null,
    )
}
