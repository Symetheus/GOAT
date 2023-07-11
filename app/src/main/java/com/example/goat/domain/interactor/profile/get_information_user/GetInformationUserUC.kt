package com.example.goat.domain.interactor.profile.get_information_user

import com.example.goat.common.Resource
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetInformationUserUC @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke(): Flow<Resource<User?>> = flow {

        try {
            emit(Resource.Loading())
            val user = profileRepository.getUserFirestore()
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}