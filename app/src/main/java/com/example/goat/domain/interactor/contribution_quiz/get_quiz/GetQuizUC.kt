package com.example.goat.domain.interactor.contribution_quiz.get_quiz

import com.example.goat.common.Resource
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.repository.ContributionQuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetQuizUC  @Inject constructor(private val contributionQuizRepository: ContributionQuizRepository) {
    operator fun invoke(): Flow<Resource<List<ContributionQuiz>>> = flow {
        try {
            emit(Resource.Loading())
            val listQuiz = contributionQuizRepository.getQuiz()
            emit(Resource.Success(listQuiz))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}