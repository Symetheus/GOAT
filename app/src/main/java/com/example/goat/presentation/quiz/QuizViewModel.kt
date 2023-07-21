package com.example.goat.presentation.quiz

import androidx.lifecycle.ViewModel
import com.example.goat.domain.interactor.gotq.GotqInteractor
import com.example.goat.domain.model.Answer
import com.example.goat.presentation.auth.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.goat.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val interactor: GotqInteractor) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    suspend fun generateCharacters2(): List<Character> =
        interactor.getCharactersUC.invokeCharacters()

}