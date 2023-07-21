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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun ListAllPlayer(
    navController: NavController,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val itemsPerPage = 8
    var endIndex by remember { mutableStateOf(0) }
    var currentPage by remember { mutableStateOf(0) }
    var userListSize by remember { mutableStateOf(uiState.value.listUser?.size ?: 0) }
    var showPreviousButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllUserFirestoreUC()
    }
    LaunchedEffect(uiState.value.listUser) {
        userListSize = uiState.value.listUser?.size ?: 0
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
                text = "Liste des joueurs", style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(25.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                val startIndex = currentPage * itemsPerPage
                endIndex = minOf(startIndex + itemsPerPage, userListSize)
                items(endIndex - startIndex) { index ->
                    uiState.value.listUser?.get(startIndex + index).let { user ->
                        if (user != null) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                    .padding(all = 5.dp)
                            ) {

                                Column(
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                ) {
                                    Text(
                                        text = "${user.lastname} ${user.firstname}",
                                        color = Color.Black,
                                        fontSize = 17.sp,
                                    )
                                    Text(
                                        text = "${user.email}",
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            if (showPreviousButton) {
                Button(
                    onClick = {
                        currentPage--
                        showPreviousButton = currentPage > 0
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp, end = 8.dp)
                ) {
                    Text(text = "Page précédente")
                }
            }
            if (userListSize > itemsPerPage && endIndex < userListSize) {
                Button(
                    onClick = {
                        currentPage++
                        showPreviousButton = true
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    enabled = currentPage < userListSize.toDouble() / itemsPerPage
                ) {
                    Text(text = "Page suivante")
                }
            }
        }
    }

}