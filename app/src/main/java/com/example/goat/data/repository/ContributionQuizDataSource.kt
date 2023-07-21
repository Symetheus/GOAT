package com.example.goat.data.repository

import com.example.goat.data.remote.dto.quote.ContributionQuoteDto
import com.example.goat.data.remote.dto.quote.QuoteDto
import com.example.goat.data.remote.dto.quote.toQuote
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.domain.model.Quote
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.ContributionQuizRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
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

    override suspend fun getQuiz(): List<Quote> {
        val listAllQuote: MutableList<Quote> = mutableListOf()
        val docRef =
            firestore.collection("quiz")
        val querySnapshot = docRef.get().await()
        querySnapshot.documents.map {
            val dto = it.toObject(ContributionQuoteDto::class.java)
            listAllQuote.add(dto!!.toQuote())
        }
        //SI FONCTIONNE PAS
        //querySnapshot.documents. foreach puis dedans it.map (avec dedans dto et tout)
        return listAllQuote
    }

}