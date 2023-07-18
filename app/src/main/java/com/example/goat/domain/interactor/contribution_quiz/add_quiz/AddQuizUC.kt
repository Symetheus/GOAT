package com.example.goat.domain.interactor.contribution_quiz.add_quiz

import com.example.goat.common.Resource
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.repository.ContributionQuizRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddQuizUC @Inject constructor(private val contributionQuizRepository: ContributionQuizRepository) {
    operator fun invoke(quiz: ContributionQuiz) = flow<Resource<Unit>> {
        try {
            emit(Resource.Loading())
            contributionQuizRepository.addQuiz(quiz)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Une erreur est survenue lors de la création du Quiz"))
        }
    }
}