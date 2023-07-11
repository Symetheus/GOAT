package com.example.goat.domain.interactor.profile

import com.example.goat.domain.interactor.profile.get_information_user.GetInformationUserUC
import com.example.goat.domain.interactor.profile.modify_user.ModifyUserUC
import javax.inject.Inject

data class ProfileInteractor @Inject constructor(
    val getInformationUserUC: GetInformationUserUC,
    val modifyUserUC: ModifyUserUC,
)