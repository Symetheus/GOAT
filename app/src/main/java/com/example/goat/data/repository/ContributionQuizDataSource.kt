package com.example.goat.data.repository

import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.ContributionQuizRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ContributionQuizDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
) :
    ContributionQuizRepository {
    override suspend fun addQuiz(quiz: ContributionQuiz) {
        firestore.collection("quiz")
            .add(quiz)
    }

    override suspend fun getQuiz(): List<ContributionQuiz> {
        val listAllQuiz: MutableList<ContributionQuiz> = mutableListOf()
        val docRef =
            firestore.collection("quiz")
        val querySnapshot = docRef.get().await()

        for (document in querySnapshot.documents) {
            val citation = document.getString("citation")
            val character = document.getString("character")

            val contributionQuiz = ContributionQuiz(
                citation ?: "",
                character ?: "",
            )

            listAllQuiz.add(contributionQuiz)
        }
        return listAllQuiz
    }

}