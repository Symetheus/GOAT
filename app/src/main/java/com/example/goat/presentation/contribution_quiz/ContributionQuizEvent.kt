package com.example.goat.presentation.contribution_quiz

import androidx.compose.runtime.MutableState

sealed class ContributionQuizEvent {
    object GetQuiz : ContributionQuizEvent()
    data class OnSelectAnswer(val currentQuestionIndex: MutableState<Int>, val selectedAnswerIndex: Int) :
        ContributionQuizEvent()
}