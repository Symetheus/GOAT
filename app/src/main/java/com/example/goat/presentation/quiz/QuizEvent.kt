package com.example.goat.presentation.quiz

import androidx.compose.runtime.MutableState
import com.example.goat.domain.model.User

sealed class QuizEvent {
    object GetQuote : QuizEvent()
    object GetSeveralQuotes : QuizEvent()
    data class OnSelectAnswer(val currentQuestionIndex: MutableState<Int>, val selectedAnswerIndex: Int, val user: User) :
        QuizEvent()
}