package com.example.goat.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.goat.utils.StoreUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RankingWithBadge(
    navController: NavController, viewModel: PlayerViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    val storeUser = StoreUser(LocalContext.current)
    val loggedUserEmailState =
        rememberUpdatedState(storeUser.getEmail.collectAsState(initial = "").value)
    val loggedUserEmail = loggedUserEmailState.value ?: ""

    LaunchedEffect(Unit) {
        viewModel.userRankingWithBadgeUC()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ranking of players", style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = viewModel.searchTerm.value,
                onValueChange = { viewModel.searchTerm.value = it },
                label = { Text("Search a player") },
                modifier = Modifier
                    .height(70.dp)
                    .padding(bottom = 10.dp)
            )
            if (uiState.value.isLoading) {
                LoadingSpinner()
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    uiState.value.listUser?.let { userList ->
                        val filteredList = if (viewModel.searchTerm.value.isNotEmpty()) {
                            userList.filter { user ->
                                val fullName = "${user.lastname} ${user.firstname}"
                                fullName.contains(viewModel.searchTerm.value, ignoreCase = true)
                            }
                        } else {
                            userList
                        }
                        items(filteredList.size) { index ->
                            val user = filteredList[index]
                            val backgroundColor =
                                if (user.email == loggedUserEmail) Color(0xFF304656) else Color.LightGray
                            val textColor =
                                if (user.email == loggedUserEmail) Color.White else Color.Black
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(backgroundColor, shape = RoundedCornerShape(8.dp))
                                    .padding(all = 5.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 5.dp),
                                ) {
                                    if (userList == filteredList) {
                                        Text(
                                            text = "${index + 1}",
                                            color = textColor,
                                            fontSize = 15.sp,
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 20.dp)
                                        )
                                    }
                                    Text(
                                        text = "${user.lastname} ${user.firstname}",
                                        color = textColor,
                                        fontSize = 15.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(3f)
                                    )
                                    Text(
                                        text = "${user.badges?.toInt()}",
                                        color = textColor,
                                        fontSize = 17.sp,
                                        modifier = Modifier.weight(1f)  //Utilise 1/4 de l'espace disponible
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun LoadingSpinner() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}