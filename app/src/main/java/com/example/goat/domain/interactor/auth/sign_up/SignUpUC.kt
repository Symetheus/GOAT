package com.example.goat.domain.interactor.auth.sign_up

import com.example.goat.common.Resource
import com.example.goat.domain.model.User
import com.example.goat.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUC @Inject constructor(private val authRepository: AuthenticationRepository) {
    operator fun invoke(
        email: String,
        password: String,
        passwordConfirmation: String
    ): Flow<Resource<User?>> = flow {

        if (email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
            emit(Resource.Error(message = "Veuillez remplir tous les champs."))
            return@flow
        }

        if (password != passwordConfirmation) {
            emit(Resource.Error(message = "Les mots de passe ne correspondent pas."))
            return@flow
        }

        try {
            emit(Resource.Loading())
            val user = authRepository.signUp(email, password)
            emit(Resource.Success(user))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Error with {${e.localizedMessage}}"))
        }
    }
}