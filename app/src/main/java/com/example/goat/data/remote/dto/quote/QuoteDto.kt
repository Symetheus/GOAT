package com.example.goat.data.remote.dto.quote


import com.squareup.moshi.Json

data class QuoteDto(
    @Json(name = "character")
    val character: CharacterDto,
    @Json(name = "sentence")
    val sentence: String
)