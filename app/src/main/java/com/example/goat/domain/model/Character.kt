package com.example.goat.domain.model

data class Character(
    val name: String,
    val slug: String,
    val house: String,
    val houseSlug: String,
    val quotes: List<String>,
)

fun Character.toAnswer() = Answer(
    name = name,
    slug = slug,
    veracity = false,
)