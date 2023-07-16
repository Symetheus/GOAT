package com.example.goat.domain.interactor.user

import com.example.goat.domain.interactor.user.get_all_user_firestore.GetAllUserFirestoreUC
import javax.inject.Inject

data class UserInteractor @Inject constructor(
    val getAllUserFirestoreUC: GetAllUserFirestoreUC,
)