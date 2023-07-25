package com.example.goat.domain.interactor.user.add_badge_user

import com.example.goat.common.Resource
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class AddBadgeUserUC @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(user: User, incrementBadge: Int): Flow<Resource<User?>> = flow {

        try {
            emit(Resource.Loading())
            val userIncremented = userRepository.addBadgeForUser(user = user, badgesInc = incrementBadge)
            emit(Resource.Success(userIncremented))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}