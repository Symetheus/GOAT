package com.example.goat.data.repository

import com.example.goat.domain.model.User
import com.example.goat.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
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
            /*
            val userFirestore = User(
                id = it.user!!.uid,
                email = it.user!!.email,
                name = "",
                photo = it.user!!.photoUrl.toString(),
                firstname = "",
                lastname = "",
            )
            firestore.collection("users")
                .document(it.user!!.uid)
                .set(userFirestore)
             */
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
        photo = photoUrl.toString(),
        firstname = null,
        lastname = null,
    )
}
