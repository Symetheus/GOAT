package com.example.goat.presentation.quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyQuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val questionIndex = remember { mutableStateOf(0) }

    // launch request to get quotes
    LaunchedEffect(Unit) {
        viewModel.onEventChanged(QuizEvent.GetQuote)
    }

    if (uiState.value.isFinished) {
        val score = uiState.value.score
        AlertDialog(
            title = {
                Text("Daily Quiz Complete")
            },
            text = {
                if (score == 1) {
                    Text(text = "Congratulations!")
                } else {
                    Text(text = "Wronng Answer!")
                }
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
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                modifier = Modifier.size(48.dp)
            )
        }
        uiState.value.error.isNotBlank() -> {
            Text(text = uiState.value.error, color = MaterialTheme.colorScheme.error)
        }

        uiState.value.quotes != null -> {
            val quotes = uiState.value.quotes!!
            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Text(text = "DailyQuiz")
                        Text(text = quotes[questionIndex.value].quote)

                        quotes[questionIndex.value].answers?.forEachIndexed { index, answer ->
                            Text(text = answer.name)
                            Button(onClick = {
                                viewModel.onEventChanged(
                                    QuizEvent.OnSelectAnswer(
                                        questionIndex,
                                        index
                                    )
                                )
                                // currentQuestionIndex.value = index + 1
                            }) {

                            }
                        }
                    }
                }
            )
        }
    }

}