package com.example.goat.presentation.auth

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goat.common.Resource
import com.example.goat.domain.interactor.auth.AuthInteractor
import com.example.goat.utils.StoreUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val interactor: AuthInteractor
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    private val storeUser = StoreUser(application)

    private fun swapForm() {
        uiState.let {
            _uiState.update {
                it.copy(
                    isSignInFormVisible = !it.isSignInFormVisible,
                    isLoading = false,
                    user = null,
                    error = "",
                )
            }
        }
    }

    private fun onCheckUserAuth() {
        interactor.getCurrentUserUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        user = null,
                        error = "",
                    )
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = resource.data,
                        error = "",
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = null,
                        error = "", // do not display error here!
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    private fun signIn(email: String, password: String) {
        interactor.signInUC(email, password).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        user = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = resource.data,
                            error = "",
                        )
                    }
                    resource.data?.email?.let { userEmail ->
                        viewModelScope.launch(Dispatchers.IO) {
                            storeUser.saveEmail(userEmail)
                            Timber.tag("email").d(userEmail)
                        }
                    }
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    fun signOut() {
        interactor.signOutUC().onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        user = null,
                        error = "",
                    )
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = null,
                        error = "",
                    )
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    private fun signUp(email: String, password: String, passwordConfirmation: String) {
        interactor.signUpUC(email, password, passwordConfirmation).onEach { resource ->
            when (resource) {
                is Resource.Loading -> _uiState.update {
                    it.copy(
                        isLoading = true,
                        user = null,
                        error = "",
                    )
                }

                is Resource.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            user = resource.data,
                            error = "",
                        )
                    }
                    swapForm()
                }

                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        user = null,
                        error = resource.message ?: "Something happened",
                    )
                }
            }
        }.launchIn(viewModelScope.plus(Dispatchers.IO))
    }

    fun onEventChanged(event: AuthEvent) {
        when (event) {
            AuthEvent.OnSwapFormClicked -> swapForm()
            is AuthEvent.GetUser -> onCheckUserAuth()
            is AuthEvent.OnLoginClicked -> signIn(event.email, event.password)
            is AuthEvent.OnRegisterClicked -> signUp(
                event.email,
                event.password,
                event.passwordConfirmation
            )
        }
    }

}
