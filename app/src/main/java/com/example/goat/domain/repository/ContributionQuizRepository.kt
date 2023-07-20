package com.example.goat.domain.repository

import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.model.Quote

interface ContributionQuizRepository {
    suspend fun addQuiz(quiz : ContributionQuiz)

    suspend fun getQuiz(): List<Quote>
}