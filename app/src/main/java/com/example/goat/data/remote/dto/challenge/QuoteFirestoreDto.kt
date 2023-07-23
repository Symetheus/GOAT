package com.example.goat.data.remote.dto.challenge

import com.example.goat.domain.model.Quote

data class QuoteFirestoreDto (
    val answers: List<AnswerDto>? = null,
    val character: String = "",
    val characterSlug: String = "",
    val house: String = "",
    val houseSlug: String = "",
    val quote: String = "",
)

fun QuoteFirestoreDto.toQuote(): Quote {
    return Quote(
        answers = answers?.map { it.toAnswer() },
        character = character,
        characterSlug = characterSlug,
        house = house,
        houseSlug = houseSlug,
        quote = quote,
    )
}
