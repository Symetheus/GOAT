package com.example.goat.domain.repository

import com.example.goat.domain.model.Quote

interface GotqRepository {
    //Quotes
    suspend fun getRandomQuote(): Quote

    suspend fun getSeveralRandomQuotes(quotesNumber: String): List<Quote>

    suspend fun getQuotesByCharacter(character: String, quotesNumber: String): List<Quote>

    // Houses

    // Characters
}