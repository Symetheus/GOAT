package com.example.goat.domain.interactor.gotq

import com.example.goat.domain.interactor.gotq.genrate_quiz_answers.GenerateQuizAnswersUC
import com.example.goat.domain.interactor.gotq.get_characters.GetCharactersUC
import com.example.goat.domain.interactor.gotq.get_random_quote.GetRandomQuoteUC
import com.example.goat.domain.interactor.gotq.get_random_quote.GetSeveralRandomQuotesUC
import javax.inject.Inject

data class GotqInteractor @Inject constructor(
    val getRandomQuoteUC: GetRandomQuoteUC,
    val getSeveralRandomQuotesUC: GetSeveralRandomQuotesUC,
    val getCharactersUC: GetCharactersUC,
    val generateQuizAnswersUC: GenerateQuizAnswersUC,
)