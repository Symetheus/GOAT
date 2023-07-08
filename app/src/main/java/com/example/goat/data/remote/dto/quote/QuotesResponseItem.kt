package com.example.goat.data.remote.dto.quote


import com.example.goat.domain.model.Quote
import com.squareup.moshi.Json

data class QuotesResponseItem(
    @Json(name = "character")
    val character: CharacterDto,
    @Json(name = "sentence")
    val sentence: String
)

fun QuotesResponseItem.toQuote() = Quote(
    quote = sentence,
    character = character.name,
    characterSlug = character.slug,
    house = character.house?.name ?: "",
    houseSlug = character.house?.slug ?: "",
)