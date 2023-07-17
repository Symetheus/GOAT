package com.example.goat.domain.interactor.user.user_ranking_with_badge

import com.example.goat.common.Resource
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRankingWithBadgeUC  @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<Resource<List<User>>> = flow {

        try {
            emit(Resource.Loading())
            val listUser = userRepository.userRankingWithBadge()
            emit(Resource.Success(listUser))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}