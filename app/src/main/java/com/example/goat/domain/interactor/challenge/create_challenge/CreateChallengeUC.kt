package com.example.goat.domain.interactor.challenge.create_challenge

import android.net.Uri
import com.example.goat.common.Resource
import com.example.goat.domain.model.Challenge
import com.example.goat.domain.model.Player
import com.example.goat.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

class CreateChallengeUC @Inject constructor(private val challengeRepository: ChallengeRepository) {
    operator fun invoke(userId: String): Flow<Resource<Challenge>> = flow {

        val currentDateTime = Date()
        val challenge = Challenge(
            id = null,
            status = "created",
            players = listOf(Player(id = userId, score = 0, status = null, timer = null)),
            quotes = null,
            createdAt = currentDateTime,
            dynamicLink = null,
        )

        println(challenge)

        try {
            emit(Resource.Loading())
            val id = challengeRepository.create(challenge)

            if (id.isEmpty()) {
                emit(Resource.Error(message = "Error with {${id}}"))
            } else {
                val dynamicLink = challengeRepository.generateDynamicLink(id)
                emit(Resource.Success(challenge.copy(id = id, dynamicLink = dynamicLink.toString())))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}