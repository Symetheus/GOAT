package com.example.goat.domain.interactor.gotq

import com.example.goat.domain.interactor.gotq.generate_quiz_answers.GenerateQuizAnswersUC
import com.example.goat.domain.interactor.gotq.get_characters.GetCharactersUC
import javax.inject.Inject

data class GotqInteractor @Inject constructor(
    val getCharactersUC: GetCharactersUC,
    val generateQuizAnswersUC: GenerateQuizAnswersUC,
)