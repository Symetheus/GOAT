package com.example.goat.presentation.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.navigation.NavController
import com.example.goat.R
import com.example.goat.domain.model.User
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserModify(navController: NavController, viewModel: UserProfileViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    var textEmail by remember { mutableStateOf(TextFieldValue("")) }
    var textFirstname by remember { mutableStateOf(TextFieldValue("")) }
    var textLastname by remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(Unit) {
        viewModel.getInformationUserUC()
    }

    LaunchedEffect(uiState.value.user) {
        uiState.value.user?.let { user ->
            textEmail = TextFieldValue(user.email ?: "")
            textFirstname = TextFieldValue(user.firstname ?: "")
            textLastname = TextFieldValue(user.lastname ?: "")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(120.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(shape = CircleShape)
            )

            Icon(
                painter = painterResource(id = R.drawable.edit_pencil),
                contentDescription = "Edit",
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
                    .align(Alignment.TopEnd)
                    .clickable { /* Action à exécuter lors du clic sur l'icône de crayon */ }
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        TextField(
            value = textEmail,
            onValueChange = { textEmail = it },
            label = { Text("Enter email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = textFirstname,
            onValueChange = { textFirstname = it },
            label = { Text("Enter your firstname") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
            )
        )
        TextField(
            value = textLastname,
            onValueChange = { textLastname = it },
            label = { Text("Enter your lastname") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.LightGray,
            )
        )
        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                val UserUpdated = User(
                    id = uiState.value.user?.id ?: "",
                    email = textEmail.text,
                    firstname =  textFirstname.text,
                    lastname = textLastname.text,
                    name = "",
                    photo = "",
                )
                viewModel.modifyUserUC(UserUpdated)
                navController.popBackStack()
            },
        ) {
            Text(
                text = "Modifier",
            )
        }
    }
}