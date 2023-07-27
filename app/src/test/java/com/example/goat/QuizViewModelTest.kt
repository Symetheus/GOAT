package com.example.goat

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.gotq.GotqInteractor
import com.example.goat.domain.interactor.gotq.genrate_quiz_answers.GenerateQuizAnswersUC
import com.example.goat.domain.interactor.gotq.get_characters.GetCharactersUC
import com.example.goat.domain.interactor.gotq.get_random_quote.GetRandomQuoteUC
import com.example.goat.domain.interactor.gotq.get_random_quote.GetSeveralRandomQuotesUC
import com.example.goat.domain.interactor.profile.ProfileInteractor
import com.example.goat.domain.interactor.user.UserInteractor
import com.example.goat.presentation.quiz.QuizEvent
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private val getRandomQuoteUC = mock<GetRandomQuoteUC>()
    private val getSeveralRandomQuotesUC = mock<GetSeveralRandomQuotesUC>()
    private val getCharactersUC = mock<GetCharactersUC>()
    private val generateQuizAnswersUC = mock<GenerateQuizAnswersUC>()

    private val interactor by lazy {
        GotqInteractor(
            getRandomQuoteUC,
            getSeveralRandomQuotesUC,
            getCharactersUC,
            generateQuizAnswersUC
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
        QuizViewModel(
            interactor = interactor,
            interactorProfil = profileInteractor,
            interactorUser = userInteractor
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
    fun `get random quote with loading`() = runBlockingTest {
        whenever(getRandomQuoteUC()).thenReturn(flowOf(Resource.Loading()))
        // whenever(getRandomQuoteUC.invoke()).thenReturn(flow { emit(Resource.Loading()) } )

        viewModel.onEventChanged(QuizEvent.GetQuote)
        // viewModel.getRandomQuote()

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
    }

    @Test
    fun `get random quote with error`() = runBlockingTest {
        whenever(getRandomQuoteUC()).thenReturn(flowOf(Resource.Error(message = "une erreur est survenue")))
        // whenever(getRandomQuoteUC.invoke()).thenReturn(flow { emit(Resource.Loading()) } )

        viewModel.onEventChanged(QuizEvent.GetQuote)
        // viewModel.getRandomQuote()

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertTrue(uiState.error.isNotEmpty())
    }

    @Test
    fun `get several random quote with loading`() = runBlockingTest {
        whenever(getSeveralRandomQuotesUC()).thenReturn(flowOf(Resource.Loading()))
        // whenever(getRandomQuoteUC.invoke()).thenReturn(flow { emit(Resource.Loading()) } )

        viewModel.onEventChanged(QuizEvent.GetSeveralQuotes)
        // viewModel.getRandomQuote()

        // Wait for the StateFlow to emit the value
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(true, uiState.isLoading)
    }
}
