package com.example.goat.presentation.history
import com.example.goat.presentation.history.Challenge
import com.example.goat.domain.model.Player
import com.example.goat.domain.model.Quote
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreChallengeRepository @Inject constructor() : ChallengeRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getChallengesWithPlayerId(playerId: String): List<Challenge> {
        val challenges: MutableList<Challenge> = mutableListOf()

        val challengesCollection = firestore.collection("challenges")

        try {
            val querySnapshot = challengesCollection.get().await()

            println("document querySnapshot ${querySnapshot.documents}")
            for (document in querySnapshot.documents) {
                println("document $document")
                val playersList = document.get("players") as? List<Map<String, Any>>

                println("playersList $playersList")
                playersList?.let { players ->
                    val matchingPlayer = players.find { playerMap ->
                        playerMap["id"] == "WSbgHEAMalOGsfYza5t3isfoMIB2"
                    }
                    println("matchingPlayer $matchingPlayer")

                    matchingPlayer?.let {
                        println("matchingPlayer let let $matchingPlayer")
                        val createdAt = document.get("createdAt") ?: ""
                        val quotesList = document.get("quotes") as? List<Map<String, Any>>
                        println("createdAt let $createdAt")
                        println("quotesList let let $quotesList")

                        val players: MutableList<PlayerChallenge> = mutableListOf()
                        val quotes: MutableList<QuoteChallenge> = mutableListOf()

                        println("createdAt 2 $createdAt")
                        println("quotesList 2 $quotesList")
                        println("players 2 $players")
                        println("quotes 2 $quotes")

                        println("playersList 2 $playersList")
                        playersList.forEach { playerMap ->
                            val id = playerMap["id"] as String
                            val score = (playerMap["score"] as Long).toInt()
                            val status = playerMap["status"] as String
                            players.add(PlayerChallenge(id, score, status))
                        }
                        println("playersList 3 $playersList")
                        println("quotesList 2 $quotesList")

                        quotesList?.forEach { quoteMap ->
                            val name = ""//quoteMap["name"] as String
                            val slug = "" //quoteMap["slug"] as String
                            val veracity = true//quoteMap["veracity"] as Boolean
                            val character = quoteMap["character"] as String
                            val characterSlug = quoteMap["characterSlug"] as String
                            val house = quoteMap["house"] as String
                            val houseSlug = quoteMap["houseSlug"] as String
                            val quote = quoteMap["quote"] as String
                            quotes.add(QuoteChallenge(name, slug, veracity, character, characterSlug, house, houseSlug, quote))
                        }
                        println("quotesList 3$quotesList")
                        println("createdAt 3 $createdAt")
                        println("players 3 $players")
                        println("quotes 3 $quotes")

                        challenges.add(Challenge(createdAt.toString(), players, quotes))
                    }
                }
            }
        } catch (exception: Exception) {
            // Gérer les erreurs ici, si nécessaire
        }

        return challenges
    }
}
