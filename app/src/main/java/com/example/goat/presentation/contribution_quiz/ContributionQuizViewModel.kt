package com.example.goat.presentation.contribution_quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.contribution_quiz.ContributionQuizInteractor
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.presentation.auth.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class ContributionQuizViewModel @Inject constructor(private val interactor: ContributionQuizInteractor) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    private val _quizAdded = MutableStateFlow(false)
    val quizAdded get() = _quizAdded

    fun addQuizUC(quiz: ContributionQuiz) {
        interactor.addQuizUC(quiz).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "",
                        )
                    }
                    _quizAdded.value = true
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    fun getQuiz() {
        interactor.getQuizUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        listQuiz = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listQuiz = resource.data,
                            error = "",
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        listQuiz = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }
}