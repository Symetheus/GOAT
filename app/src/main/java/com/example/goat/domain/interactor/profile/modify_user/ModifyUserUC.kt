package com.example.goat.domain.interactor.profile.modify_user

import com.example.goat.common.Resource
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ModifyUserUC @Inject constructor(private val profileRepository: ProfileRepository) {
    operator fun invoke(userUpdated: User): Flow<Resource<User?>> = flow {

        try {
            emit(Resource.Loading())
            val user = profileRepository.modifyUser(user = userUpdated)
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}