package com.example.bcreatingproyect.onboarding.domain.usecase

import com.example.bcreatingproyect.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class HasSeenOnboardingUseCase (private val repository: OnboardingRepository){
    operator fun invoke(): Boolean {
        return repository.hasSeenOnboarding()
    }
}