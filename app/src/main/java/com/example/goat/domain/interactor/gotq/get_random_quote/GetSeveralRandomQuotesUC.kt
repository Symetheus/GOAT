package com.example.goat.domain.interactor.gotq.get_random_quote

import com.example.goat.common.Resource
import com.example.goat.domain.model.Quote
import com.example.goat.domain.repository.GotqRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSeveralRandomQuotesUC @Inject constructor(private val gotqRepository: GotqRepository) {
    operator fun invoke(quotesNumber: String = "10"): Flow<Resource<List<Quote>?>> = flow {
        try {
            emit(Resource.Loading())
            val quotes = gotqRepository.getSeveralRandomQuotes(quotesNumber)
            emit(Resource.Success(quotes))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Une erreur est survenue lors de la récupération des questions"))
        }
    }
}
