package com.example.goat.domain.interactor.auth.sign_out

import com.example.goat.common.Resource
import com.example.goat.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignOutUC @Inject constructor(private val authRepository: AuthenticationRepository) {
    operator fun invoke() = flow<Resource<Unit>> {
        try {
            emit(Resource.Loading())
            authRepository.signOut()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Une erreur est survenue lors de la déconnexion."))
        }
    }
}
