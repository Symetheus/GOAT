package com.example.goat.presentation.challenge

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.auth.AuthInteractor
import com.example.goat.domain.interactor.challenge.ChallengeInteractor
import com.example.goat.domain.model.Challenge
import com.google.api.ResourceProto.resource
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
class ChallengeViewModel @Inject constructor(
    private val challengeInteractor: ChallengeInteractor,
    private val authInteractor: AuthInteractor,
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private fun onCreate(userId: String) {
        createChallenge(userId)
    }

    private fun startChallenge() {
        // launch player timer!
    }

    private fun getChallengeById(id: String) {
        println("get challenge: $id !")
        challengeInteractor.getChallengeByIdUC(id).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        isCreated = false,
                        isUserAuth = false,
                        error = "",
                    )
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isCreated = false,
                        isUserAuth = false,
                        isWaitingRoom = true,
                        challenge = resource.data,
                        error = "",
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isCreated = false,
                        isUserAuth = false,
                        challenge = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    // when a second user joins the challenge, the challenge is filled
    private fun fillChallenge(challenge: Challenge, userId: String) {
        println("fill challenge!")
        challengeInteractor.fillChallengeUC(challenge, userId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        isCreated = false,
                        isUserAuth = false,
                        error = "",
                    )
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isCreated = false,
                        isUserAuth = false,
                        isWaitingRoom = false,
                        isFilled = true,
                        challenge = resource.data,
                        error = "",
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isCreated = false,
                        isUserAuth = false,
                        challenge = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))

    }

    private fun createChallenge(userId: String) {
        println("on create!")
        challengeInteractor.createChallengeUC(userId).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        isCreated = false,
                        isUserAuth = false,
                        error = "",
                    )
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isCreated = true,
                        challenge = resource.data,
                        isUserAuth = false,
                        error = "",
                        dynamicLink = resource.data?.dynamicLink ?: ""
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isCreated = false,
                        isUserAuth = false,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    private fun onGetUser() {
        authInteractor.getCurrentUserUC().onEach { resource ->
            when (resource) {
                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isUserAuth = true,
                        isCreated = false,
                        user = resource.data,
                        error = "",
                    )
                }

                else -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        isUserAuth = false,
                        isCreated = false,
                        user = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    private fun onUserJoined() {
        _uiState.update {
            it.copy(
                isLoading = false,
                hadUserJoined = true,
                error = "",
            )
        }
    }

    private fun leaveRoom(challenge: Challenge, userId: String) {
        _uiState.update {
            it.copy(
                isWaitingRoom = true
            )
        }
    }

    fun onEventChanged(event: ChallengeEvent) {
        when (event) {
            ChallengeEvent.GetUser -> onGetUser()
            ChallengeEvent.UserHasJoin -> onUserJoined()
            is ChallengeEvent.GetChallenge -> getChallengeById(event.id)
            is ChallengeEvent.CreateChallenge -> onCreate(event.userId)
            is ChallengeEvent.FillChallenge -> fillChallenge(event.challenge, event.userId)
            is ChallengeEvent.LeaveRoom -> leaveRoom(event.challenge, event.userId)
        }
    }
}
