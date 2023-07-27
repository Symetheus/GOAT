package com.example.goat

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.auth.AuthInteractor
import com.example.goat.domain.interactor.auth.get_current_user.GetCurrentUserUC
import com.example.goat.domain.interactor.auth.sign_in.SignInUC
import com.example.goat.domain.interactor.auth.sign_out.SignOutUC
import com.example.goat.domain.interactor.auth.sign_up.SignUpUC
import com.example.goat.domain.interactor.gotq.GotqInteractor
import com.example.goat.domain.interactor.gotq.genrate_quiz_answers.GenerateQuizAnswersUC
import com.example.goat.domain.interactor.gotq.get_characters.GetCharactersUC
import com.example.goat.domain.interactor.gotq.get_random_quote.GetRandomQuoteUC
import com.example.goat.domain.interactor.gotq.get_random_quote.GetSeveralRandomQuotesUC
import com.example.goat.domain.model.Challenge
import com.example.goat.domain.model.User
import com.example.goat.presentation.auth.AuthEvent
import com.example.goat.presentation.auth.AuthViewModel
import com.example.goat.presentation.challenge.ChallengeEvent
import com.example.goat.presentation.quiz.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val getCurrentUserUC = mock<GetCurrentUserUC>()
    private val signOutUC = mock<SignOutUC>()
    private val signInUC = mock<SignInUC>()
    private val signUpUC = mock<SignUpUC>()

    private val interactor by lazy {
        AuthInteractor(signInUC, signUpUC, signOutUC, getCurrentUserUC)
    }

    private val viewModel by lazy { AuthViewModel(application = Application(), interactor = interactor) }

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
    fun `swap Form`() = runBlockingTest {
        viewModel.onEventChanged(AuthEvent.OnSwapFormClicked)

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertTrue(uiState.isSignInFormVisible)
    }

    @Test
    fun `sign in with loading`() = runBlockingTest {
        val email = "jean@oui"
        val password = "Test1234"
        whenever(signInUC(email, password)).thenReturn( flowOf(Resource.Loading()) )

        viewModel.onEventChanged(AuthEvent.OnLoginClicked(email, password))

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertEquals(true, uiState.isLoading)
    }

    @Test
    fun `sign in with success`() = runBlockingTest {
        val email = "jean@oui"
        val password = "Test1234"
        val user = User(id = "123", email = email, name = "", photo = "", firstname = "", lastname = "", badges = 0)
        whenever(signInUC(email, password)).thenReturn( flowOf(Resource.Success(user)) )

        viewModel.onEventChanged(AuthEvent.OnLoginClicked(email, password))

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertEquals(user, uiState.user)
    }

    @Test
    fun `sign in with error`() = runBlockingTest {
        val email = "jean@oui"
        val password = "Test1234"
        whenever(signInUC(email, password)).thenReturn( flowOf(Resource.Error(message = "Erreuuur")) )

        viewModel.onEventChanged(AuthEvent.OnLoginClicked(email, password))

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        print(uiState)
        assertTrue(uiState.error.isNotEmpty())
    }
}