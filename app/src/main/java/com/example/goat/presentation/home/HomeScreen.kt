package com.example.goat.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.goat.presentation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userId: String,
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }


    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { navController.navigate(Screen.UserProfile.route) }
                        .size(48.dp)
                )

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
                    onClick = { navController.navigate(Screen.ContributionQuizScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Contribution Quiz",
                    )
                }
                Button(
                    onClick = { navController.navigate(Screen.AddContributionQuizScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Add Contribution Quiz",
                    )
                }

                Button(
                    onClick = {
                        // navController.navigate(Screen.ChallengeScreen.route)
                        openBottomSheet = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Create a challenge",
                    )
                }

                Button(
                    onClick = { navController.navigate(Screen.RankingWithBadgeScreen.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "See ranking of players with badge",
                    )
                }

                Button(
                    onClick = { navController.navigate(Screen.History.route) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "History",
                    )
                }
            }
        }
    )

    if (openBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = false,
            ),
            onDismissRequest = { openBottomSheet = false },
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
        ) {
            Scaffold(
                topBar = {
                    Column {
                        Text(
                            text = "Settings",
                            modifier = Modifier
                                .height(55.dp)
                                .padding(start = 32.dp, top = 16.dp),
                            fontSize = 24.sp
                        )
                        Divider(color = MaterialTheme.colorScheme.onSurface, thickness = 1.dp)
                    }
                },
            ) {
                SettingsForm(paddingValues = it, navController = navController)
            }
        }
    }

}

@Composable
fun SettingsForm(
    paddingValues: PaddingValues,
    navController: NavController
) {
    var playersNumber by remember { mutableStateOf("2") }
    var quotesNumber by remember { mutableStateOf("5") }

    Column(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .padding(16.dp)
    ) {
        Text(
            text = "Build your challenge",
            fontSize = 18.sp
        )
        OutlinedTextField(
            value = playersNumber,
            onValueChange = { playersNumber = it },
            label = { Text(text = "Players Number") },
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = quotesNumber,
            onValueChange = { quotesNumber = it },
            label = { Text(text = "Quotes Number") },
            modifier = Modifier.fillMaxWidth(),
        )
        Button(onClick = { navController.navigate(Screen.ChallengeScreen.route) }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Valider",
            )
        }
    }
}
