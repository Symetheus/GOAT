package com.example.goat.domain.interactor.challenge.update_player

import com.example.goat.common.Resource
import com.example.goat.domain.model.Challenge
import com.example.goat.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdatePlayerStatusUC @Inject constructor(private val challengeRepository: ChallengeRepository) {
    operator fun invoke(challenge: Challenge, playerId: String): Flow<Resource<Challenge?>> = flow {
        try {
            emit(Resource.Loading())

            // update right player
            val player = challenge.players.find { it.id == playerId }
            if (player != null) {
                val newPlayer = player.copy(status = "finished")

                val newChallenge = challenge.copy(
                    players = challenge.players.map {
                        if (it.id == playerId) {
                            newPlayer
                        } else {
                            it
                        }
                    }
                )

                challengeRepository.update(challenge = newChallenge)
                emit(Resource.Success(newChallenge))
            } else {
                emit(Resource.Error(message = "An error has occurred while the update of your status."))
            }

        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }

    }
}