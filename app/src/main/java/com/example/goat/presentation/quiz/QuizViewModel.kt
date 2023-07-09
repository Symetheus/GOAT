package com.example.goat.presentation.quiz

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.gotq.GotqInteractor
import com.example.goat.domain.model.Answer
import com.example.goat.domain.model.Character
import com.example.goat.domain.model.toAnswer
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
class QuizViewModel @Inject constructor(private val interactor: GotqInteractor) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private fun getSeveralRandomQuotes() {
        interactor.getSeveralRandomQuotesUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        quotes = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    val data = resource.data
                    val characters = generateCharacters()

                    data?.forEach {
                        it.answers =
                            generatesAnswers(it.toAnswer().copy(veracity = true), characters)
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quotes = data,
                            error = "",
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        quotes = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    private fun getRandomQuote() {
        interactor.getRandomQuoteUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        quotes = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    val data = resource.data
                    if (data != null) {
                        val characters = generateCharacters()
                        data.answers =
                            generatesAnswers(data.toAnswer().copy(veracity = true), characters)
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            quotes = if (data != null) listOf(data) else null,
                            error = "",
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        quotes = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    private fun generatesAnswers(
        trueAnswer: Answer,
        characters: List<Character>
    ): List<Answer> =
        interactor.generateQuizAnswersUC(characters, trueAnswer)


    private suspend fun generateCharacters(): List<Character> =
        interactor.getCharactersUC.invokeCharacters()


    private fun handleAnswerSelection(
        currentQuestionIndex: MutableState<Int>,
        selectedAnswerIndex: Int
    ) {
        val quizQuestions = uiState.value.quotes!!

        // Check if the selected answer is correct
        val isCorrectAnswer =
            selectedAnswerIndex == quizQuestions[currentQuestionIndex.value].answers?.indexOfFirst { it.veracity }

        // update UI accordingly the answer
        if (isCorrectAnswer) {
            println("correct answer")
            uiState.let {
                _uiState.update {
                    it.copy(
                        score = it.score + 1,
                    )
                }
            }
        } else {
            println("wrong answer")
        }

        // Increment the question index
        if (currentQuestionIndex.value < quizQuestions.size - 1) {
            currentQuestionIndex.value++
        } else {
            println("finished !!")
            uiState.let {
                _uiState.update {
                    it.copy(
                        isFinished = true,
                    )
                }
            }
        }
    }


    fun onEventChanged(event: QuizEvent) {
        when (event) {
            QuizEvent.GetQuote -> getRandomQuote()
            QuizEvent.GetSeveralQuotes -> getSeveralRandomQuotes()
            is QuizEvent.OnSelectAnswer -> handleAnswerSelection(
                event.currentQuestionIndex,
                event.selectedAnswerIndex
            )
        }
    }
}