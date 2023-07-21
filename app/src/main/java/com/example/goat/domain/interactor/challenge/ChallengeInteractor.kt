package com.example.goat.domain.interactor.challenge

import com.example.goat.domain.interactor.challenge.create_challenge.CreateChallengeUC
import com.example.goat.domain.interactor.challenge.fill_challenge.FillChallengeUC
import com.example.goat.domain.interactor.challenge.get_challenge.GetChallengeByIdUC
import javax.inject.Inject

data class ChallengeInteractor @Inject constructor(
    val getChallengeByIdUC: GetChallengeByIdUC,
    val createChallengeUC: CreateChallengeUC,
    val fillChallengeUC: FillChallengeUC,
)
