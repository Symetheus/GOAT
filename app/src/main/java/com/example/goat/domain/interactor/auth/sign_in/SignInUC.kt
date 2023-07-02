package com.example.goat.domain.interactor.auth.sign_in

import com.example.goat.common.Resource
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUC @Inject constructor(private val authRepository: AuthenticationRepository) {
    operator fun invoke(email: String, password: String): Flow<Resource<User?>> = flow {
        try {
            emit(Resource.Loading())
            val user = authRepository.signIn(email, password)
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}