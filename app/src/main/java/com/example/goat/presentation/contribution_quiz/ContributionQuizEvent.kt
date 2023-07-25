package com.example.goat.presentation.contribution_quiz

import androidx.compose.runtime.MutableState
import com.example.goat.domain.model.User

sealed class ContributionQuizEvent {
    object GetQuiz : ContributionQuizEvent()
    data class OnSelectAnswer(val currentQuestionIndex: MutableState<Int>, val selectedAnswerIndex: Int, val user: User) :
        ContributionQuizEvent()
}