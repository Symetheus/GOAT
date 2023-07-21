package com.example.goat.presentation.contribution_quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.goat.domain.model.Character
import com.example.goat.domain.model.ContributionQuiz
import com.example.goat.presentation.quiz.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContributionQuizScreen(
    navController: NavController,
    viewModel: ContributionQuizViewModel = hiltViewModel(),
    quizViewModel: QuizViewModel = hiltViewModel(),
) {
    val citationTextState = remember { mutableStateOf("") }
    var selectedCharacter by remember { mutableStateOf("") }
    var selectedSlug by remember { mutableStateOf("") }

    val quizAddedState by viewModel.quizAdded.collectAsState()
    var listCharacter by remember { mutableStateOf(emptyList<Character>()) }

    LaunchedEffect(Unit) {
        listCharacter = quizViewModel.generateCharacters()
    }

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
            text = "Write your quiz !",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color(0xFF304656),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = citationTextState.value,
            onValueChange = { citationTextState.value = it },
            label = { Text("Quote") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            var expanded by remember { mutableStateOf(false) }
            Text(
                text = "Character :\n$selectedCharacter",
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(vertical = 8.dp)
            )
            if (expanded) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    listCharacter.forEach { character ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCharacter = character.name
                                selectedSlug = character.slug
                                expanded = false
                            }
                        ) {
                            Text(text = character.name, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                val quiz = ContributionQuiz(
                    sentence = citationTextState.value,
                    character = selectedCharacter,
                    slug = selectedSlug,
                )
                viewModel.addQuizUC(quiz = quiz)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save my quiz")
        }
    }
}