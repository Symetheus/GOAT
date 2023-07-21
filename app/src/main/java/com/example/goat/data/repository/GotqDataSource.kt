package com.example.goat.data.repository

import com.example.goat.data.remote.api.GotqApi
import com.example.goat.data.remote.dto.character.toCharacter
import com.example.goat.data.remote.dto.quote.toQuote
import com.example.goat.domain.model.Character
import com.example.goat.domain.model.Quote
import com.example.goat.domain.repository.GotqRepository
import javax.inject.Inject

class GotqDataSource @Inject constructor(private val gotqApi: GotqApi) : GotqRepository {
    // QUOTES
    override suspend fun getRandomQuote(): Quote =
        gotqApi.getRandomQuote().toQuote()

    override suspend fun getSeveralRandomQuotes(quotesNumber: String): List<Quote> =
        gotqApi.getSeveralRandomQuotes(quotesNumber).map {
            it.toQuote()
        }

    override suspend fun getQuotesByCharacter(
        character: String,
        quotesNumber: String
    ): List<Quote> =
        gotqApi.getQuotesByCharacter(character, quotesNumber).map {
            it.toQuote()
        }

    // HOUSES

    // CHARACTERS
    override suspend fun getCharactersList(): List<Character> =
        gotqApi.getCharactersList().map {
            it.toCharacter()
        }
}