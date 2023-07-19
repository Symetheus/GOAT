package com.example.goat.domain.repository

import com.example.goat.domain.model.ContributionQuiz

interface ContributionQuizRepository {
    suspend fun addQuiz(quiz : ContributionQuiz)

    suspend fun getQuiz(): List<ContributionQuiz>
}