package com.example.goat.presentation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main")
    object HomeScreen: Screen("home/{userId}") {
        fun createRoute(userId: String): String = "home/$userId"
    }
    object DailyQuizScreen: Screen("daly_quiz")
    object QuizScreen: Screen("quiz")
    object ChallengeScreen: Screen("challenge/{userId}") {
        fun createRoute(userId: String): String = "challenge/$userId"
    }

    object ContributionQuizScreen: Screen("contribution_quiz")
    object AddContributionQuizScreen: Screen("add_contribution_quiz")
    object RankingWithBadgeScreen: Screen("ranking_with_badge")
    object UserProfile: Screen("profile")
    object UserModify: Screen("user_modify")
    object ListAllPlayer: Screen("list_all_player")
    object History: Screen("history")
}
