package com.example.goat.domain.repository

import com.example.goat.domain.model.User

interface UserRepository {

    suspend fun getAllUserFirestore() : List<User>
}