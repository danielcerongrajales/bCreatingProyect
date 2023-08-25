package com.example.bcreatingproyect.onboarding.domain.usecase

import com.example.bcreatingproyect.onboarding.domain.repository.OnboardingRepository
import javax.inject.Inject

class CompleteOnboardingUseCase (private val repository: OnboardingRepository) {
    operator fun invoke() {
        repository.completeOnboarding()
    }
}