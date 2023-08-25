package com.example.bcreatingproyect.authentication.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bcreatingproyect.authentication.domain.repository.AuthenticationRepository
import com.example.bcreatingproyect.authentication.domain.useCase.LoginUseCases
import com.example.bcreatingproyect.authentication.domain.useCase.PasswordResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCases: LoginUseCases):ViewModel(){
    var state by mutableStateOf(LoginState())
    private set
    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.EmailChange -> {
                state=state.copy(email = event.email)
            }
            LoginEvent.Login -> {
                login()
            }
            is LoginEvent.PasswordChange -> {
                state=state.copy(password = event.password)
            }

        }
    }

    private fun login() {
        state = state.copy(
            emailError = null,
            paswordError = null
        )
        if (!loginUseCases.validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = "El email no es valido"
            )
        }
        val passwordResult = loginUseCases.validatePasswordUseCase(state.password)
        if (passwordResult is PasswordResult.Invalid) {
            state = state.copy(
                paswordError = passwordResult.errorMessage
            )
        }
        if (state.emailError == null && state.paswordError == null) {
            state = state.copy(
                isLoading = true
            )
            viewModelScope.launch {
                loginUseCases.loginWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(
                        isLoggedIn = true
                    )
                }.onFailure {
                    state = state.copy(
                        emailError = it.message
                    )
                }
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }
}