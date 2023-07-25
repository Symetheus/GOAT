package com.example.goat.presentation.history

data class QuoteChallenge(
    val name: String,
    val slug: String,
    val veracity: Boolean,
    val character: String,
    val characterSlug: String,
    val house: String,
    val houseSlug: String,
    val quote: String
)