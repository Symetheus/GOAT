package com.example.goat

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.auth.AuthInteractor
import com.example.goat.domain.interactor.auth.sign_in.SignInUC
import com.example.goat.domain.interactor.auth.sign_out.SignOutUC
import com.example.goat.domain.interactor.auth.sign_up.SignUpUC
import com.example.goat.domain.interactor.challenge.ChallengeInteractor
import com.example.goat.domain.interactor.challenge.create_challenge.CreateChallengeUC
import com.example.goat.domain.interactor.challenge.fill_challenge.FillChallengeUC
import com.example.goat.domain.interactor.challenge.get_challenge.GetChallengeByIdUC
import com.example.goat.domain.interactor.challenge.update_player.UpdatePlayerStatusUC
import com.example.goat.domain.interactor.challenge.update_player.UpdatePlayerUC
import com.example.goat.domain.interactor.profile.ProfileInteractor
import com.example.goat.domain.interactor.user.UserInteractor
import com.example.goat.domain.model.Challenge
import com.example.goat.presentation.challenge.ChallengeEvent
import com.example.goat.presentation.challenge.ChallengeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

class ChallengeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val getChallengeByIdUC = mock<GetChallengeByIdUC>()
    private val createChallengeUC = mock<CreateChallengeUC>()
    private val fillChallengeUC = mock<FillChallengeUC>()
    private val updatePlayerUC = mock<UpdatePlayerUC>()
    private val updatePlayerStatusUC = mock<UpdatePlayerStatusUC>()

    private val challengeInteractor by lazy {
        ChallengeInteractor(
            getChallengeByIdUC,
            createChallengeUC,
            fillChallengeUC,
            updatePlayerUC,
            updatePlayerStatusUC
        )
    }

    private val authInteractor by lazy {
        AuthInteractor(
            getCurrentUserUC = mock(),
            signOutUC = mock<SignOutUC>(),
            signInUC = mock<SignInUC>(),
            signUpUC = mock<SignUpUC>()
        )
    }

    private val userInteractor by lazy {
        UserInteractor(
            addBadgeUserUC = mock(),
            getAllUserFirestoreUC = mock(),
            userRankingWithBadgeUC = mock(),
        )
    }

    private val profileInteractor by lazy {
        ProfileInteractor(
            getInformationUserUC = mock(),
            modifyUserUC = mock(),
            stockImageFirebaseStorageUC = mock()
        )
    }

    private val viewModel by lazy {
        ChallengeViewModel(
            challengeInteractor,
            authInteractor,
            userInteractor,
            profileInteractor
        )
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `get challenge by id with loading`() = runBlockingTest {
        val id = "123"
        whenever(getChallengeByIdUC(id)).thenReturn(flowOf(Resource.Loading()))

        viewModel.onEventChanged(ChallengeEvent.GetChallenge(id))

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertEquals(true, uiState.isLoading)
    }

    @Test
    fun `get challenge by id with success`() = runBlockingTest {
        val id = "123"
        val challenge =
            Challenge(id = id, "status", emptyList(), emptyList(), Date(), "dynamicLink")
        whenever(getChallengeByIdUC(id)).thenReturn(flowOf(Resource.Success(challenge)))

        viewModel.onEventChanged(ChallengeEvent.GetChallenge(id))

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertEquals(challenge, uiState.challenge)
    }

    @Test
    fun `get challenge by id with error`() = runBlockingTest {
        val id = "123"
        whenever(getChallengeByIdUC(id)).thenReturn(flowOf(Resource.Error(message = "Erreur")))

        viewModel.onEventChanged(ChallengeEvent.GetChallenge(id))

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertTrue(uiState.error.isNotEmpty())
    }
}