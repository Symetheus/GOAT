package com.example.goat.data.repository

import com.example.goat.domain.model.User
import com.example.goat.domain.repository.AuthenticationRepository
import com.example.goat.domain.repository.UserRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import timber.log.Timber
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
            val badges = document.getDouble("badges")

            val user = User(
                authenticationRepository.getCurrentUser()!!.id,
                email ?: "",
                pseudo ?: "",
                photo ?: "",
                firstname ?: "",
                lastname ?: "",
                badges ?: 0,
            )

            listAllUsers.add(user)
        }
        return listAllUsers
    }

    override suspend fun userRankingWithBadge(): List<User> {
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
            val badges = document.getDouble("badges")

            val user = User(
                authenticationRepository.getCurrentUser()!!.id,
                email ?: "",
                pseudo ?: "",
                photo ?: "",
                firstname ?: "",
                lastname ?: "",
                badges ?: 0,
            )

            listAllUsers.add(user)
        }
        listAllUsers.sortByDescending { it.badges?.toDouble() ?: 0.0 }
        return listAllUsers
    }

    override suspend fun addBadgeForUser(user: User, badgesInc: Int): User? {
        val docRef =
            firestore.collection("users").document(user.id)
        val documentSnapshot = docRef.get().await()
        if (documentSnapshot.exists()) {
            val currentBadges = documentSnapshot.getLong("badges") ?: 0
            val newBadges = currentBadges + badgesInc

            docRef.update("badges", newBadges)
            val updatedUser = user.copy(badges = newBadges)

            return updatedUser
        }
        return null
    }
}