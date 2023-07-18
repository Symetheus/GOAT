package com.example.goat.data.repository

import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.repository.ContributionQuizRepository
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class ContributionQuizDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) :
    ContributionQuizRepository {
    override suspend fun addQuiz(quiz: ContributionQuiz) {
        firestore.collection("quiz")
            .add(quiz)
    }

}