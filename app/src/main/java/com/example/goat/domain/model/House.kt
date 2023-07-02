package com.example.goat.domain.model

data class House(
    val name: String,
    val slug: String,
    val members: List<HouseMember>,
)

data class HouseMember(
    val name: String,
    val slug: String,
)