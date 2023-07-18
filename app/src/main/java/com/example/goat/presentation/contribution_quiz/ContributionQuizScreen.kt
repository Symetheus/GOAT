package com.example.goat.presentation.contribution_quiz

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.goat.domain.model.ContributionQuiz

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContributionQuizScreen(
    navController: NavController,
    viewModel: ContributionQuizViewModel = hiltViewModel(),
) {
    val citationTextState = remember { mutableStateOf("") }
    val characterTextState = remember { mutableStateOf("") }
    val quizAddedState by viewModel.quizAdded.collectAsState()

    LaunchedEffect(quizAddedState) {
        if (quizAddedState) {
            navController.popBackStack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentHeight()
    ) {
        Text(
            text = "Ecrivez votre quiz !",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color(0xFF304656),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = citationTextState.value,
            onValueChange = { citationTextState.value = it },
            label = { Text("Citation") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = characterTextState.value,
            onValueChange = { characterTextState.value = it },
            label = { Text("Character") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val quiz = ContributionQuiz(
                    citation = citationTextState.value,
                    character = characterTextState.value,
                )
                viewModel.addQuizUC(quiz = quiz)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Enregistrer mon quiz")
        }
    }
}