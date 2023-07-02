package com.example.goat.domain.model

data class Character(
    val name: String,
    val slug: String,
    val house: String,
    val houseSlug: String,
    val quotes: List<String>,
)