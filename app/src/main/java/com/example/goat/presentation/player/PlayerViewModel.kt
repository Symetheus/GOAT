package com.example.goat.presentation.player

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.user.UserInteractor
import com.example.goat.presentation.player.UiState
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
class PlayerViewModel @Inject constructor(private val interactor: UserInteractor) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    val searchTerm = mutableStateOf("")

    fun getAllUserFirestoreUC() {
        interactor.getAllUserFirestoreUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        listUser = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listUser = resource.data,
                            error = "",
                        )
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        listUser = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    fun userRankingWithBadgeUC() {
        interactor.userRankingWithBadgeUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        listUser = null,
                        error = ""
                    )
                }

                is Resource.Success -> {
                    val filteredList = resource.data?.filter { user ->
                        val fullName = "${user.lastname} ${user.firstname}"
                        fullName.contains(searchTerm.value, ignoreCase = true)
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listUser = filteredList,
                            error = ""
                        )
                    }
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            listUser = null,
                            error = resource.message ?: "Something happened"
                        )
                    }
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }


}