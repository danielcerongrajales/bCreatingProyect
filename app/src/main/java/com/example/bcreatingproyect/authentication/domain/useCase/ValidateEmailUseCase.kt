package com.example.bcreatingproyect.authentication.domain.useCase


import com.example.bcreatingproyect.authentication.domain.matcher.EmailMatcher
import javax.inject.Inject

class ValidateEmailUseCase (private val emailMatcher: EmailMatcher) {
    operator fun  invoke(email:String):Boolean{
        return emailMatcher.isValid(email)
    }
}