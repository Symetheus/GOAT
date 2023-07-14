package com.example.goat.domain.repository

import android.net.Uri
import com.example.goat.domain.model.User

interface ProfileRepository {
    suspend fun modifyUser(user : User): User?

    suspend fun getUserFirestore() : User?

    suspend fun stockImageFirebaseStorage(imageUri : Uri) : String
}