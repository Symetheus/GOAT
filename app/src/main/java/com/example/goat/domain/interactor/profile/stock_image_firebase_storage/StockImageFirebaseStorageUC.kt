package com.example.goat.domain.interactor.profile.stock_image_firebase_storage

import android.net.Uri
import com.example.goat.common.Resource
import com.example.goat.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StockImageFirebaseStorageUC @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke(imageUri: Uri): Flow<Resource<String>> = flow {

        try {
            emit(Resource.Loading())
            val downloadUrl = profileRepository.stockImageFirebaseStorage(imageUri)
            emit(Resource.Success(downloadUrl))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}