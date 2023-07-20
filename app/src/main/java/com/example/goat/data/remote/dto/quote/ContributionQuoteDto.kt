package com.example.goat.data.remote.dto.quote

import com.example.goat.domain.model.Quote
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class ContributionQuoteDto (
    val character: String,
    val slug: String,
    val sentence: String,
) {
    constructor() : this("","", "")
    @Exclude
    fun toMap(): Map<String, String?> {
        return mapOf(
            "character" to character,
            "slug" to slug,
            "citation" to sentence,
        )
    }
}

fun ContributionQuoteDto.toQuote(): Quote {
    return Quote(
        quote = sentence,
        character = character,
        characterSlug = slug,
        house = "",
        houseSlug = "",
    )
}