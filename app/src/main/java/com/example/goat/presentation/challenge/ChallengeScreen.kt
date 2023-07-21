package com.example.goat.presentation.challenge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

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
            }

            else -> Unit
        }
    }

    // Text(text = "Challenge Screen", modifier = Modifier.padding(48.dp))

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

        uiState.value.hadUserJoined && uiState.value.challenge != null && uiState.value.challenge!!.players.size < 2 -> {
            Text(text = "User has joined")
            viewModel.onEventChanged(
                ChallengeEvent.FillChallenge(
                    uiState.value.challenge!!,
                    uiState.value.user?.id!!
                )
            )
        }

        uiState.value.challenge != null && uiState.value.challenge?.status == "started" -> {
            Column {
                Text(text = "Start the game!")
                Text(text = uiState.value.challenge.toString())
            }
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
                Text(modifier = Modifier.padding(16.dp), text = "Waiting for players to join!")
                Text(text = uiState.value.challenge.toString())
                Text(text = uiState.value.dynamicLink)
            }
        }
    }

}
