package com.example.goat.domain.interactor.gotq.get_characters

import com.example.goat.common.Resource
import com.example.goat.domain.repository.GotqRepository
import com.example.goat.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersUC @Inject constructor(private val gotqRepository: GotqRepository) {
    operator fun invoke(): Flow<Resource<List<Character>?>> =
        flow {
            try {
                emit(Resource.Loading())
                val characters = gotqRepository.getCharactersList()
                emit(Resource.Success(characters))
            } catch (e: Exception) {
                emit(Resource.Error(message = "Une erreur est survenue lors de la récupération des personnages."))
            }
        }

    suspend fun invokeCharacters(): List<Character> = gotqRepository.getCharactersList()
}