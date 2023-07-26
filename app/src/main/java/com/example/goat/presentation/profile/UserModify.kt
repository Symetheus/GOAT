package com.example.goat.presentation.profile

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.goat.R
import com.example.goat.domain.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserModify(navController: NavController, viewModel: UserProfileViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val downloadUrlState by viewModel.downloadUrlState.collectAsState()
    var textEmail by remember { mutableStateOf(TextFieldValue("")) }
    var textFirstname by remember { mutableStateOf(TextFieldValue("")) }
    var textLastname by remember { mutableStateOf(TextFieldValue("")) }
    var imageUrl by remember { mutableStateOf("") }
    var imageTrigger by remember { mutableStateOf(0) }
    var showSnackbar by remember { mutableStateOf(false) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher: ManagedActivityResultLauncher<String, Uri?> =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                imageUri = result
                viewModel.viewModelScope.launch {
                    viewModel.stockImageFirebaseStorage(imageUri!!)
                    imageTrigger++
                    if (imageTrigger == 1) {
                        showSnackbar = true
                    }
                }
            }
        }

    LaunchedEffect(Unit) {
        viewModel.getInformationUserUC()
    }
    LaunchedEffect(downloadUrlState) {
        imageUrl = downloadUrlState ?: ""
    }
    LaunchedEffect(imageTrigger) {
        if (imageTrigger > 0 && imageUrl.isBlank()) {
            imageUrl = uiState.value.downloadUrl ?: ""
        }
        if (imageTrigger > 1) {
            showSnackbar = true
        }
    }

    LaunchedEffect(imageUrl) {
        if (imageUrl.isBlank()) {
            imageUrl = uiState.value.downloadUrl ?: ""
        }
    }

    LaunchedEffect(uiState.value.user) {
        uiState.value.user?.let { user ->
            textEmail = TextFieldValue(user.email ?: "")
            textFirstname = TextFieldValue(user.firstname ?: "")
            textLastname = TextFieldValue(user.lastname ?: "")
            imageUrl = user.photo ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(120.dp)) {
            if (imageUrl != "") {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
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
                painter = painterResource(id = R.drawable.edit_pencil),
                contentDescription = "Edit",
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
        }

        TextField(
            value = textEmail,
            onValueChange = { textEmail = it },
            label = { Text("Enter email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
            )
        )
        TextField(
            value = textFirstname,
            onValueChange = { textFirstname = it },
            label = { Text("Enter your firstname") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
            ),
        )
        TextField(
            value = textLastname,
            onValueChange = { textLastname = it },
            label = { Text("Enter your lastname") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
            )
        )

        Button(
            onClick = {
                val UserUpdated = User(
                    id = uiState.value.user?.id ?: "",
                    email = textEmail.text,
                    firstname = textFirstname.text,
                    lastname = textLastname.text,
                    name = "",
                    photo = imageUrl,
                    badges = uiState.value.user?.badges
                )
                viewModel.modifyUserUC(UserUpdated)
                navController.popBackStack()
            },
            modifier = Modifier.padding(top = 26.dp)
        ) {
            Text(
                text = "Modify",
            )
        }

        if (showSnackbar) {
            LaunchedEffect(showSnackbar) {
                delay(2000)
                showSnackbar = false
            }
            Snackbar(
                modifier = Modifier.padding(16.dp),
                content = { Text("Well-imported image") },
                contentColor = Color.Black,
                containerColor = Color.Green,
            )
        }

    }
}