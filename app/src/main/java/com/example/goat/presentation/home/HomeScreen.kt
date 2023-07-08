package com.example.goat.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.goat.presentation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Goat", style = MaterialTheme.typography.displayLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate(Screen.DailyQuizScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Daily Quiz",
                    )
                }

                Button(
                    onClick = { navController.navigate(Screen.QuizScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Quiz",
                    )
                }

                Button(
                    onClick = { navController.navigate(Screen.ChallengeScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Create a challenge",
                    )
                }
            }

        }
    )
}
