package com.example.goat.presentation.quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val currentQuestionIndex = remember { mutableStateOf(0) }

    // launch request to get quotes
    LaunchedEffect(Unit) {
        viewModel.onEventChanged(QuizEvent.GetSeveralQuotes)
        viewModel.getInformationUserUC()
    }

    if (uiState.value.isFinished) {
        val score = uiState.value.score
        AlertDialog(
            title = {
                if (score >= 7) {
                    Text(text = "Congratulations!")
                } else {
                    Text(text = "You can do better!")
                }
            },
            text = {
                Text("Score: $score/10")
            },
            confirmButton = {
                Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Continue")
                }
            },
            onDismissRequest = {},
            dismissButton = null,
        )
    }

    when {
        uiState.value.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        uiState.value.error.isNotBlank() -> {
            Text(text = uiState.value.error, color = MaterialTheme.colorScheme.error)
        }

        uiState.value.quotes != null -> {
            val quotes = uiState.value.quotes!!
            val index = currentQuestionIndex.value

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Text(
                            text = "Question ${index + 1}/10",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(top = 16.dp, bottom = 15.dp)
                        )
                        Text(
                            text = quotes[index].quote, style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            quotes[index].answers?.forEachIndexed { index, answer ->
                                Button(
                                    onClick = {
                                        viewModel.onEventChanged(
                                            QuizEvent.OnSelectAnswer(
                                                currentQuestionIndex,
                                                index,
                                                uiState.value.user!!,
                                            )
                                        )
                                    }) {
                                    Text(text = answer.name)
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}