package com.example.goat.domain.interactor.challenge.get_challenge

import com.example.goat.common.Resource
import com.example.goat.domain.model.Challenge
import com.example.goat.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetChallengeByIdUC @Inject constructor(private val challengeRepository: ChallengeRepository) {
    operator fun invoke(id: String): Flow<Resource<Challenge?>> = flow {
        println("Getting UseCase")
        try {
            emit(Resource.Loading())
            val challengeFlow = challengeRepository.get(id)
            challengeFlow.collect { challenge ->
                emit(Resource.Success(challenge))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}
