import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goat.presentation.history.HistoryViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel



@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    // Observer les données des challenges depuis le ViewModel
    val challenges by viewModel.challenges.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchChallengesWithPlayerId("fsf");
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = "History",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(challenges) { challenge ->
                Text("createdAt: ${challenge.createdAt}")
                for (player in challenge.players) {
                    Text("Player ID: ${player.id}, Score: ${player.score}, Status: ${player.status}")
                }
                for (quote in challenge.quotes!!) {
                    Text("Quote: ${quote.quote}, Character: ${quote.character}")
                }
            }
        }
    }
}




