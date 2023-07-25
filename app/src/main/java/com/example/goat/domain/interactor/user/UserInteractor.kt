package com.example.goat.domain.interactor.user

import com.example.goat.domain.interactor.user.add_badge_user.AddBadgeUserUC
import com.example.goat.domain.interactor.user.get_all_user_firestore.GetAllUserFirestoreUC
import com.example.goat.domain.interactor.user.user_ranking_with_badge.UserRankingWithBadgeUC
import javax.inject.Inject

data class UserInteractor @Inject constructor(
    val getAllUserFirestoreUC: GetAllUserFirestoreUC,
    val userRankingWithBadgeUC: UserRankingWithBadgeUC,
    val addBadgeUserUC: AddBadgeUserUC,
)