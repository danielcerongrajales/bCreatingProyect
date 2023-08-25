package com.example.bcreatingproyect.authentication.di

import com.example.bcreatingproyect.authentication.data.matcher.EmailMatcherImp
import com.example.bcreatingproyect.authentication.data.repository.AuthenticationRepositoryImp
import com.example.bcreatingproyect.authentication.domain.matcher.EmailMatcher
import com.example.bcreatingproyect.authentication.domain.repository.AuthenticationRepository
import com.example.bcreatingproyect.authentication.domain.useCase.GetUserIdUseCase
import com.example.bcreatingproyect.authentication.domain.useCase.LoginUseCases
import com.example.bcreatingproyect.authentication.domain.useCase.LoginWithEmailUseCase
import com.example.bcreatingproyect.authentication.domain.useCase.LogoutUseCase
import com.example.bcreatingproyect.authentication.domain.useCase.SignupUseCases
import com.example.bcreatingproyect.authentication.domain.useCase.SignupWithEmailUseCase
import com.example.bcreatingproyect.authentication.domain.useCase.ValidateEmailUseCase
import com.example.bcreatingproyect.authentication.domain.useCase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn (SingletonComponent::class)
object AuthenticationModule {
    @Provides
    @Singleton
    fun providesAuthenticationRepository():AuthenticationRepository=
        AuthenticationRepositoryImp()
    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImp()
    }

    @Provides
    @Singleton
    fun provideLoginUseCases(
        repository: AuthenticationRepository,
        emailMatcher: EmailMatcher
    ): LoginUseCases {
        return LoginUseCases(
            loginWithEmailUseCase = LoginWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideSignupUseCases(
        repository: AuthenticationRepository,
        emailMatcher: EmailMatcher
    ): SignupUseCases {
        return SignupUseCases(
            signupWithEmailUseCase = SignupWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase()
        )
    }
    @Provides
    @Singleton
    fun provideGetUserIdUseCase(
        repository: AuthenticationRepository
    ): GetUserIdUseCase {
        return GetUserIdUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthenticationRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }

}