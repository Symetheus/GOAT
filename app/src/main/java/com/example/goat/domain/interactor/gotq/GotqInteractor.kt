package com.example.goat.domain.interactor.gotq

import com.example.goat.domain.interactor.gotq.get_characters.GetCharactersUC
import javax.inject.Inject

data class GotqInteractor @Inject constructor(
    val getCharactersUC: GetCharactersUC,
)