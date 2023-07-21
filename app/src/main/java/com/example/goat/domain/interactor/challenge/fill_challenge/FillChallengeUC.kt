package com.example.goat.domain.interactor.challenge.fill_challenge

import com.example.goat.common.Resource
import com.example.goat.domain.interactor.gotq.genrate_quiz_answers.GenerateQuizAnswersUC
import com.example.goat.domain.model.Challenge
import com.example.goat.domain.model.Player
import com.example.goat.domain.model.toAnswer
import com.example.goat.domain.repository.ChallengeRepository
import com.example.goat.domain.repository.GotqRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FillChallengeUC @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val gotqRepository: GotqRepository,
    private val generateQuizAnswersUC: GenerateQuizAnswersUC
) {
    private val quotesNumber = "10"
    operator fun invoke(challenge: Challenge, userId: String): Flow<Resource<Challenge?>> = flow {
        try {
            emit(Resource.Loading())
            val quotes = gotqRepository.getSeveralRandomQuotes(quotesNumber)
            val characters = gotqRepository.getCharactersList()

            // Generate answers for each quote
            quotes.forEach {
                it.answers = generateQuizAnswersUC(characters, it.toAnswer().copy(veracity = true))
            }

            // Add the second player to the challenge AND the quotes
            val newChallenge = challenge.copy(
                status = "started",
                quotes = quotes,
                players = challenge.players.plus(Player(id = userId, score = 0, status = null, timer = null))
            )

            challengeRepository.update(challenge = newChallenge)
            emit(Resource.Success(newChallenge))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}
