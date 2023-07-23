package com.example.goat.data.repository

import android.net.Uri
import com.example.goat.data.remote.dto.challenge.ChallengeDto
import com.example.goat.data.remote.dto.challenge.toChallenge
import com.example.goat.domain.model.Challenge
import com.example.goat.domain.repository.ChallengeRepository
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.google.firebase.dynamiclinks.ktx.component1
import com.google.firebase.dynamiclinks.ktx.component2

class ChallengeDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dynamicLinks: FirebaseDynamicLinks,
) :
    ChallengeRepository {
    private val challengesCollection = firestore.collection("challenges")

    override suspend fun create(challenge: Challenge): String {
        val challengeDocument =
            challengesCollection.add(challenge).addOnSuccessListener { documentReference ->
                println("UID: ${documentReference.id}")
            }.addOnFailureListener { exception ->
                println("Error adding document: $exception")
                throw exception
            }.await()

        return challengeDocument.id
    }

    override suspend fun get(challengeId: String): Flow<Challenge?> {
        return challengesCollection.document(challengeId).snapshots().map { snapshot ->
            val dto = snapshot.toObject(ChallengeDto::class.java)
            dto?.copy(id = snapshot.id)?.toChallenge()
        }
    }

    override suspend fun update(challenge: Challenge) {
        challengesCollection.document(challenge.id!!).update(
            mapOf(
                "status" to challenge.status,
                "players" to challenge.players,
                "quotes" to challenge.quotes,
            )
        ).await()
    }

    override suspend fun generateDynamicLink(challengeId: String): Uri? {

        val shortLinkTask = dynamicLinks.shortLinkAsync {
            link = Uri.parse("https://gotq.com/challenge/$challengeId")
            domainUriPrefix = "https://goatq.page.link"
            androidParameters("com.example.goat") {
                fallbackUrl = Uri.parse("https://gotq.com")
            }
        }.addOnSuccessListener { (shortLink, flowchartLink) ->
            println("short link: $shortLink and $flowchartLink")
        }.addOnFailureListener {
            println("Error creating short link: $it")
        }.await()

        return shortLinkTask.shortLink

        /*val dynamicLink = dynamicLinks.dynamicLink {
            link = Uri.parse("https://gotq.com/challenge/$challengeId")
            domainUriPrefix = "https://gotq.page.link"
            androidParameters("com.example.goat") {
                fallbackUrl = Uri.parse("https://gotq.com")
            }
        }

        return dynamicLink.uri*/
    }
}
