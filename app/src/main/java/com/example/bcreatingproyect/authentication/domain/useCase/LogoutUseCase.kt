package com.example.bcreatingproyect.authentication.domain.useCase

import com.example.bcreatingproyect.authentication.domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}