import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.goat.presentation.history.Challenge
import com.example.goat.presentation.history.HistoryViewModel
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*


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
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            items(challenges) { challenge ->
                ChallengeSection(challenge)
            }
        }
    }
}

@Composable
fun ChallengeSection(challenge: Challenge) {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        val formattedDate = formatTimestamp(challenge.createdAt)
        Text(
            text = "Date: $formattedDate",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        ChallengeTile(challenge)
    }
}

@Composable
fun ChallengeTile(challenge: Challenge) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            for (player in challenge.players) {
                Text(
                    text = withBoldStyle {
                        "Joueur: " + player.id + "\n" +
                                "Score: " + player.score + "\n" +
                                "Status: " + player.status + "\n"
                    },
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            for (quote in challenge.quotes!!) {
                Text(
                    text = withBoldStyle {
                        "Citation: " + quote.quote + "\n" +
                                "Auteur: " + quote.character + "\n"
                    },
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
private fun withBoldStyle(content: @Composable () -> String): AnnotatedString {
    return AnnotatedString.Builder().apply {
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.None,
                fontWeight = FontWeight.Medium
            )
        ) {
            append(content())
        }
    }.toAnnotatedString()
}

private fun formatTimestamp(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy à HH:mm:ss", Locale.getDefault())
    return try {
        val dateInMillis = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val date = Date(dateInMillis)
        sdf.format(date)
    } catch (e: Exception) {
        "Date inconnue"
    }
}