package com.example.goat.domain.repository

import com.example.goat.domain.model.User

interface ProfileRepository {
    suspend fun modifyUser(user : User): User?

    suspend fun getUserFirestore() : User?
}