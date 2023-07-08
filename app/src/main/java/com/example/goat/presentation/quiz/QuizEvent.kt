package com.example.goat.presentation.quiz

import androidx.compose.runtime.MutableState

sealed class QuizEvent {
    object GetQuote : QuizEvent()
    object GetSeveralQuotes : QuizEvent()
    data class OnSelectAnswer(val currentQuestionIndex: MutableState<Int>, val selectedAnswerIndex: Int) :
        QuizEvent()
}