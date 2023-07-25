package com.example.goat.presentation.challenge

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.goat.presentation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChallengeScreen(
    viewModel: ChallengeViewModel = hiltViewModel(),
    navController: NavController,
    userId: String? = null,
    challengeId: String? = null,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    var lifecycleEvent by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }

    val currentQuestionIndex = remember { mutableStateOf(0) }


    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            lifecycleEvent = event
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            // Unregister the observer when the composable is removed from the composition
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    LaunchedEffect(lifecycleEvent) {
        when (lifecycleEvent) {
            Lifecycle.Event.ON_STOP -> println("ON STOOOOOP")
            Lifecycle.Event.ON_DESTROY -> println("ON DESTROOOOY")
            Lifecycle.Event.ON_RESUME -> println("ON RESUME")
            Lifecycle.Event.ON_START -> {
                println("ON START — CHALLENGE SCREEN")
                viewModel.onEventChanged(ChallengeEvent.GetUser)

                if (uiState.value.user != null && uiState.value.isUserAuth) {
                    viewModel.onEventChanged(ChallengeEvent.CreateChallenge(uiState.value.user!!.id))
                }

                if (userId != null && challengeId != null) {
                    println("Coming from dynamic Link!")
                    viewModel.onEventChanged(ChallengeEvent.UserHasJoin)
                    viewModel.onEventChanged(ChallengeEvent.GetChallenge(challengeId))
                }
                viewModel.getInformationUserUC()
            }

            else -> Unit
        }
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

        // Player has done his challenge
        uiState.value.isPlayerFinished && uiState.value.challenge != null -> {
            val challenge = uiState.value.challenge!!
            val player = challenge.players.find { it.id == uiState.value.user?.id }

            AlertDialog(
                title = {
                    if (player!!.score >= 7) {
                        Text(text = "Congratulations!")
                    } else {
                        Text(text = "You can do better!")
                    }
                },
                text = {
                    Column {
                        Text("Score: ${player!!.score}/ ${challenge.quotes?.size}")

                        if (viewModel.isAllPlayersFinished(challenge.players)) {
                            Text("All players have finished the challenge!")
                            challenge.players.forEach {
                                Text("${it.id}: ${it.score}/${challenge.quotes?.size}")
                            }
                        } else {
                            Text("Waiting for other players to finish the challenge...")
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 4.dp,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        // navController.popBackStack(Screen.HomeScreen.route)
                        navController.navigate(Screen.HomeScreen.route)
                    }) {
                        Text("Continue")
                    }
                },
                onDismissRequest = {},
                dismissButton = null,
            )
        }


        // Complete challenge when a second player joins
        uiState.value.hadUserJoined && uiState.value.challenge != null && uiState.value.challenge!!.players.size < 2 -> {
            viewModel.onEventChanged(
                ChallengeEvent.FillChallenge(
                    uiState.value.challenge!!,
                    uiState.value.user?.id!!
                )
            )
        }

        // Start the game when challenge.status is "started"
        uiState.value.challenge != null && uiState.value.challenge?.status == "started" -> {
            val quotes = uiState.value.challenge?.quotes!!
            val index = currentQuestionIndex.value

            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Text(text = "Question ${index + 1}/${quotes.size}")
                        Text(text = quotes[index].quote)

                        quotes[index].answers?.forEachIndexed { answersIndex, answer ->
                            Button(onClick = {
                                viewModel.onEventChanged(
                                    ChallengeEvent.OnSelectAnswer(
                                        currentQuestionIndex,
                                        answersIndex,
                                        uiState.value.user!!
                                    )
                                )
                            }) {
                                Text(text = answer.name)
                            }
                        }
                    }
                }
            )
        }

        uiState.value.isCreated && uiState.value.challenge != null -> {
            Column {
                Text(text = "Challenge is created")
                Text(text = uiState.value.challenge.toString())
            }
            viewModel.onEventChanged(ChallengeEvent.GetChallenge(uiState.value.challenge!!.id!!))
        }

        uiState.value.isFilled && uiState.value.challenge != null -> {
            Column {
                Text(text = "Challenge is filled")
                Text(text = uiState.value.challenge?.players.toString())
            }
        }

        !uiState.value.isFilled && uiState.value.isWaitingRoom && uiState.value.dynamicLink.isNotEmpty() -> {
            Column {

                Text(text = uiState.value.challenge.toString())
                Text(text = uiState.value.dynamicLink)
            }

            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Waiting for players to join!"
                        )

                        ShareLink(url = uiState.value.dynamicLink)
                    }
                }
            )
        }
    }

}

@Composable
fun ShareLink(url: String) {
    val context = LocalContext.current

    Button(onClick = {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT, url)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        context.startActivity(Intent.createChooser(shareIntent, null))
    }) {
        Text("Share link")
    }
}
