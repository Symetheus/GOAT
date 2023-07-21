package com.example.goat.domain.model

data class User(
    val id: String,
    val email: String?,
    val name: String?,
    val photo: String?,
    val firstname: String?,
    val lastname: String?,
    val badges: Number?,
)
