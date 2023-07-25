package com.example.goat.presentation

import HistoryScreen
import android.content.Intent
import android.net.Uri
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.goat.presentation.auth.AuthScreen
import com.example.goat.presentation.home.HomeScreen
import com.example.goat.presentation.challenge.ChallengeScreen
import com.example.goat.presentation.player.ListAllPlayer
import com.example.goat.presentation.player.RankingWithBadge
import com.example.goat.presentation.profile.UserModify
import com.example.goat.presentation.profile.UserProfile
import com.example.goat.presentation.quiz.DailyQuizScreen
import com.example.goat.presentation.quiz.QuizScreen
import com.example.goat.presentation.contribution_quiz.AddContributionQuizScreen
import com.example.goat.presentation.contribution_quiz.ContributionQuizScreen
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
                    MainScreen(intent)
                }
            }
        }
    }
}

@Composable
fun MainScreen(intent: Intent) {
    val navController = rememberNavController()
    val actions = remember(navController) { NavActions(navController) }

    val link: String? = deepLinksUI(intent)

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            if (link != null) {
                AuthScreen(onLoginSuccess = actions.navigateToChallengeFromDynamicLink)
            } else {
                AuthScreen(onLoginSuccess = actions.navigateToHome)
            }
        }
        composable(
            route = Screen.HomeScreen.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            HomeScreen(navController = navController, userId = userId)
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
        composable(
            route = Screen.ChallengeScreen.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ChallengeScreen(navController = navController, challengeId = link, userId = userId)
        }

        composable(route = Screen.ContributionQuizScreen.route) {
            ContributionQuizScreen(navController = navController)
        }
        composable(route = Screen.AddContributionQuizScreen.route) {
            AddContributionQuizScreen(navController = navController)
        }
        composable(route = Screen.RankingWithBadgeScreen.route) {
            RankingWithBadge(navController = navController)
        }
        composable(route = Screen.History.route) {
            HistoryScreen()
        }
    }
}

class NavActions(navController: NavHostController) {
    val navigateToHome: (String) -> Unit = { userId ->
        navController.navigate(Screen.HomeScreen.createRoute(userId))
    }

    val navigateToChallengeFromDynamicLink: (String) -> Unit = { userId ->
        navController.navigate(Screen.ChallengeScreen.createRoute(userId))
    }
}

@Composable
fun deepLinksUI(intent: Intent): String? {
    println("Je suis dans la deeplink function")
    val deepLinkMsg = remember {
        mutableStateOf("")
    }

    val uri: Uri? = intent.data

    if (uri != null) {
        val parameters: List<String> = uri.pathSegments

        val param = parameters[parameters.size - 1]

        deepLinkMsg.value = param
        println("Deep Link Message: ${deepLinkMsg.value}")
        return deepLinkMsg.value
    }
    return null
}