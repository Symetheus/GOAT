package com.example.goat.presentation.contribution_quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContributionQuizScreen(
    navController: NavController,
    viewModel: ContributionQuizViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val currentQuestionIndex = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.onEventChanged(ContributionQuizEvent.GetQuiz)
    }

    if (uiState.value.isFinished) {
        val score = uiState.value.score
        AlertDialog(
            title = {
                val result = (uiState.value.quotes!!.size * 0.75).toInt()
                if (score >= result) {
                    Text(text = "Congratulations!")
                } else {
                    Text(text = "You can do better!")
                }
            },
            text = {
                Text("Score: $score/${uiState.value.quotes?.size}")
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
                modifier = Modifier.fillMaxSize(),
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

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Text(text = "Question ${index + 1}/${quotes.size}", style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(text = quotes[index].quote, style = MaterialTheme.typography.titleLarge)

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                quotes[index].answers?.forEachIndexed { index, answer ->
                                    Button(
                                        onClick = {
                                            viewModel.onEventChanged(
                                                ContributionQuizEvent.OnSelectAnswer(
                                                    currentQuestionIndex,
                                                    index
                                                )
                                            )
                                        }
                                    ) {
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
}