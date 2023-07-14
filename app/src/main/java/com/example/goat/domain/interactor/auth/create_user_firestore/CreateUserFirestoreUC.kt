package com.example.goat.domain.interactor.auth.create_user_firestore

import com.example.goat.common.Resource
import com.example.goat.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateUserFirestoreUC @Inject constructor(private val authRepository: AuthenticationRepository) {
    operator fun invoke() = flow<Resource<Unit>> {
        try {
            emit(Resource.Loading())
            authRepository.createUserFirestore()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Une erreur est survenue lors de la creéation du User dans Firestore"))
        }
    }
}