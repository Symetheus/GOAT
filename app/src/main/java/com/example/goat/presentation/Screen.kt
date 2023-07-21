package com.example.goat.presentation

sealed class Screen(val route: String) {
    object MainScreen: Screen("main")
    object HomeScreen: Screen("home")
    object UserProfile: Screen("profile")
    object UserModify: Screen("user_modify")
    object ListAllPlayer: Screen("list_all_player")
    object DailyQuizScreen: Screen("daly_quiz")
    object QuizScreen: Screen("quiz")
    object ContributionQuizScreen: Screen("contribution_quiz")
    object AddContributionQuizScreen: Screen("add_contribution_quiz")
    object ChallengeScreen: Screen("challenge")
    object RankingWithBadgeScreen: Screen("ranking_with_badge")
}
