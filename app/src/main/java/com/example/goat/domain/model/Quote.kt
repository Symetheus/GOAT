package com.example.goat.domain.model

data class Quote(
    val quote: String,
    val character: String,
    val characterSlug: String,
    val house: String,
    val houseSlug: String,
    var answers: List<Answer>? = null,
)

fun Quote.toAnswer() = Answer(
    name = character,
    slug = characterSlug,
    veracity = false,
)
