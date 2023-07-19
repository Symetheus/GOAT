package com.example.goat.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.gotq.GotqInteractor
import com.example.goat.presentation.auth.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import com.example.goat.domain.model.Character
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

    suspend fun generateCharacters() {
        interactor.getCharactersUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        listCharacter = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listCharacter = resource.data,
                            error = "",
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        listCharacter = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    suspend fun generateCharacters2(): List<Character> =
        interactor.getCharactersUC.invokeCharacters()

}