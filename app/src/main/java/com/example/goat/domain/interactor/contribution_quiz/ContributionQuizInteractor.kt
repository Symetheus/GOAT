package com.example.goat.domain.interactor.contribution_quiz

import com.example.goat.domain.interactor.contribution_quiz.add_quiz.AddQuizUC
import com.example.goat.domain.interactor.contribution_quiz.get_quiz.GetQuizUC
import com.example.goat.domain.interactor.contribution_quiz.generate_quiz_answers.GenerateQuizAnswersUC
import com.example.goat.domain.interactor.contribution_quiz.get_characters.GetCharactersUC
import javax.inject.Inject

data class ContributionQuizInteractor @Inject constructor(
    val addQuizUC: AddQuizUC,
    val getQuizUC: GetQuizUC,
    val generateQuizAnswersUC: GenerateQuizAnswersUC,
    val getCharactersUC: GetCharactersUC,
)