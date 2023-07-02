package com.example.goat.domain.interactor.auth

import com.example.goat.domain.interactor.auth.get_current_user.GetCurrentUserUC
import com.example.goat.domain.interactor.auth.sign_up.SignUpUC
import com.example.goat.domain.interactor.auth.sign_in.SignInUC
import javax.inject.Inject

data class AuthInteractor @Inject constructor(
    val signInUC: SignInUC,
    val signUpUC: SignUpUC,
    val getCurrentUserUC: GetCurrentUserUC,
)