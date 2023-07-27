package com.example.goat.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.presentation.history.Challenge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    // MutableStateFlow pour stocker la liste des challenges où ton ID est présent dans les joueurs
    private val _challenges = MutableStateFlow(emptyList<Challenge>())
    val challenges: StateFlow<List<Challenge>> = _challenges

    init {
        // Récupérer les challenges depuis le Repository et mettre à jour le MutableStateFlow
        fetchChallengesWithPlayerId("WSbgHEAMalOGsfYza5t3isfoMIB2")
    }

    public fun fetchChallengesWithPlayerId(playerId: String) {
        viewModelScope.launch {
            val challengesList = challengeRepository.getChallengesWithPlayerId(playerId)
            _challenges.value = challengesList
            println(challengesList)
        }
    }
}

interface ChallengeRepository {
    suspend fun getChallengesWithPlayerId(playerId: String): List<com.example.goat.presentation.history.Challenge>
}
