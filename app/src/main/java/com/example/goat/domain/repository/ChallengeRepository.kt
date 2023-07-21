package com.example.goat.domain.repository

import android.net.Uri
import com.example.goat.domain.model.Challenge
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    suspend fun create(challenge: Challenge): String
    suspend fun get(challengeId: String): Flow<Challenge?>
    suspend fun update(challenge: Challenge)
    suspend fun generateDynamicLink(challengeId: String): Uri?
}