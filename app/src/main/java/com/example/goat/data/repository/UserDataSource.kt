package com.example.goat.data.repository

import com.example.goat.domain.model.User
import com.example.goat.domain.repository.AuthenticationRepository
import com.example.goat.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authenticationRepository: AuthenticationRepository
) :
    UserRepository {
    override suspend fun getAllUserFirestore(): List<User> {
        val listAllUsers: MutableList<User> = mutableListOf()
        val docRef =
            firestore.collection("users")
        val querySnapshot = docRef.get().await()

        for (document in querySnapshot.documents) {
            val email = document.getString("email")
            val pseudo = document.getString("pseudo")
            val photo = document.getString("photo")
            val firstname = document.getString("firstname")
            val lastname = document.getString("lastname")

            val user = User(
                authenticationRepository.getCurrentUser()!!.id,
                email ?: "",
                pseudo ?: "",
                photo ?: "",
                firstname ?: "",
                lastname ?: ""
            )

            listAllUsers.add(user)
        }
        return listAllUsers
    }
}