package com.example.goat.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.goat.R
import com.example.goat.presentation.Screen
import com.example.goat.presentation.auth.AuthViewModel

@Composable
fun UserProfile(
    navController: NavController,
    viewModel: UserProfileViewModel = hiltViewModel(),
    viewAuthModel: AuthViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getInformationUserUC()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            if (uiState.value.user?.photo?.isNotEmpty() == true) {
                Image(
                    painter = rememberAsyncImagePainter(model = uiState.value.user!!.photo),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(shape = CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(130.dp)
                        .clip(shape = CircleShape)
                )
            }
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Modify User",
                modifier = Modifier
                    .clickable { navController.navigate(Screen.UserModify.route) }
                    .size(20.dp)
            )
        }


        Text(
            text = uiState.value.user?.email ?: "",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(
                top = 4.dp, bottom = 20.dp
            )
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.value.user?.firstname ?: "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = uiState.value.user?.lastname ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = ("Nombre de badges : " + (uiState.value.user?.badges?.toInt() ?: "")),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 5.dp)
        )

        Button(
            onClick = { navController.navigate(Screen.ListAllPlayer.route) },
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Text(
                text = "See the list of players",
            )
        }

        Button(
            onClick = {
                viewAuthModel.signOut()
                navController.navigate(Screen.MainScreen.route)
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(
                text = "Logout",
            )
        }
    }
}