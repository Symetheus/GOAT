package com.example.goat.domain.interactor.gotq.get_random_quote

import com.example.goat.common.Resource
import com.example.goat.domain.model.Quote
import com.example.goat.domain.repository.GotqRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomQuoteUC @Inject constructor(private val gotqRepository: GotqRepository) {
    operator fun invoke(): Flow<Resource<Quote>> = flow {
        try {
            emit(Resource.Loading())
            val quote = gotqRepository.getRandomQuote()
            emit(Resource.Success(quote))
        } catch (e: Exception) {
            // emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
            emit(Resource.Error(message = "Une erreur est survenur lors de la récupération de la question"))
        }
    }
}
