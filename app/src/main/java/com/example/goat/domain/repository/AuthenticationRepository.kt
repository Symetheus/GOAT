package com.example.goat.domain.repository

import com.example.goat.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser

interface AuthenticationRepository {
    suspend fun signIn(email: String, password: String): User?

    suspend fun signUp(email: String, password: String): User?

    suspend fun createUserFirestore()

    suspend fun signOut()

    suspend fun getCurrentUser(): User?
}