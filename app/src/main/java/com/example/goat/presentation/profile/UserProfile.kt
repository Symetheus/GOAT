package com.example.goat.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.value.user?.photo?.isNotEmpty() == true) {
            Image(
                painter = rememberAsyncImagePainter(model = uiState.value.user!!.photo),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = uiState.value.user?.email ?: "", style = MaterialTheme.typography.labelSmall
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = uiState.value.user?.firstname ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = uiState.value.user?.lastname ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(26.dp))
        Button(
            onClick = { navController.navigate(Screen.UserModify.route) },
        ) {
            Text(
                text = "Modifier mon profil",
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                viewAuthModel.signOut()
                navController.navigate(Screen.MainScreen.route)
            },
        ) {
            Text(
                text = "Se déconnecter",
            )
        }
    }
}