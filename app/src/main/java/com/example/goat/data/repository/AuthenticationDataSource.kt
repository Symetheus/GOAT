package com.example.goat.data.repository

import com.example.goat.domain.model.User
import com.example.goat.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationDataSource @Inject constructor(private val auth: FirebaseAuth) :
    AuthenticationRepository {
    override suspend fun signIn(email: String, password: String): User? {
        auth.signInWithEmailAndPassword(email, password).await().let {
            return it.user?.toUser()
        }
    }

    override suspend fun signUp(email: String, password: String): User? {
        auth.createUserWithEmailAndPassword(email, password).await().let {
            return it.user?.toUser()
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        auth.currentUser.let {
            return it?.toUser()
        }
    }
}

fun FirebaseUser.toUser(): User {
    return User(
        id = uid,
        email = email,
        name = displayName,
        photo = photoUrl.toString()
    )
}
