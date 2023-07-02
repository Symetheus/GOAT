package com.example.goat.data.remote.api

import com.example.goat.data.remote.dto.quote.QuotesResponse
import com.example.goat.data.remote.dto.quote.QuotesResponseItem
import retrofit2.http.GET
import retrofit2.http.Path

/// Game of Thrones Quotes API
/// https://gameofthronesquotes.xyz/
interface GotqApi {
    // QUOTES
    @GET("/random")
    suspend fun getRandomQuote(): QuotesResponseItem

    @GET("/random/{quotesNumber}")
    suspend fun getSeveralRandomQuotes(@Path("quotesNumber") quotesNumber: String): QuotesResponse

    @GET("/author/{character}/{quotesNumber}")
    suspend fun getQuotesByCharacter(
        @Path("character") character: String,
        @Path("quotesNumber") quotesNumber: String
    ): QuotesResponse

    // HOUSES

    // CHARACTERS
}