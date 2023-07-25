package com.example.goat.domain.repository

import com.example.goat.domain.model.User

interface UserRepository {

    suspend fun getAllUserFirestore() : List<User>

    suspend fun userRankingWithBadge() : List<User>

    suspend fun addBadgeForUser(user : User, badgesInc: Int): User?
}