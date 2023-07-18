package com.example.goat.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.goat.presentation.auth.AuthScreen
import com.example.goat.presentation.contribution_quiz.ContributionQuizScreen
import com.example.goat.presentation.home.HomeScreen
import com.example.goat.presentation.player.ListAllPlayer
import com.example.goat.presentation.player.RankingWithBadge
import com.example.goat.presentation.profile.UserModify
import com.example.goat.presentation.profile.UserProfile
import com.example.goat.presentation.quiz.ChallengeScreen
import com.example.goat.presentation.quiz.DailyQuizScreen
import com.example.goat.presentation.quiz.QuizScreen
import com.example.goat.presentation.ui.theme.GoatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            AuthScreen(navController = navController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.UserProfile.route) {
            UserProfile(navController = navController)
        }
        composable(route = Screen.UserModify.route) {
            UserModify(navController = navController)
        }
        composable(route = Screen.ListAllPlayer.route) {
            ListAllPlayer(navController = navController)
        }
        composable(route = Screen.DailyQuizScreen.route) {
            DailyQuizScreen(navController = navController)
        }
        composable(route = Screen.QuizScreen.route) {
            QuizScreen(navController = navController)
        }
        composable(route = Screen.ContributionQuizScreen.route) {
            ContributionQuizScreen(navController = navController)
        }
        composable(route = Screen.ChallengeScreen.route) {
            ChallengeScreen(navController = navController)
        }
        composable(route = Screen.RankingWithBadgeScreen.route) {
            RankingWithBadge(navController = navController)
        }
    }
}