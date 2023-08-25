package com.example.bcreatingproyect.authentication.domain.useCase

import com.example.bcreatingproyect.authentication.domain.repository.AuthenticationRepository

class GetUserIdUseCase(private val repository: AuthenticationRepository) {

 operator fun invoke(): String ?{
    return repository.getUserId()
}
}