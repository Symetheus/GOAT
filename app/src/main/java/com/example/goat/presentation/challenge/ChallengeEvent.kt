package com.example.goat.presentation.challenge

import com.example.goat.domain.model.Challenge

sealed class ChallengeEvent {
    object GetUser : ChallengeEvent()
    object UserHasJoin : ChallengeEvent()
    data class GetChallenge(
        val id: String,
    ) : ChallengeEvent()
    data class CreateChallenge(
        val userId: String,
    ) : ChallengeEvent()
    data class FillChallenge(
        val challenge: Challenge,
        val userId: String,
    ) : ChallengeEvent()
    data class LeaveRoom(
        val challenge: Challenge,
        val userId: String,
    ) : ChallengeEvent()
}
