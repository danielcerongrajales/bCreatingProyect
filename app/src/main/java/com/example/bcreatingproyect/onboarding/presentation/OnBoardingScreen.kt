package com.example.bcreatingproyect.onboarding.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bcreatingproyect.R
import com.example.bcreatingproyect.onboarding.presentation.components.OnBoardingPager

@Composable
fun OnBoardingScreen(viewmodel: OnboardingViewModel= hiltViewModel(), onFinish: () -> Unit) {
    LaunchedEffect(key1 = viewmodel.hasSeenOnboarding) {
        if (viewmodel.hasSeenOnboarding) {
            onFinish()
        }
    }
    val pages = listOf(
        OnboardingPagerInformation(
            title = "Welcome to Monumental Habits",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding1
        ),
        OnboardingPagerInformation(
            title = "Create new habits easily",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding2
        ),
        OnboardingPagerInformation(
            title = "Keep track of your progress",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding3
        ),
        OnboardingPagerInformation(
            title = "Join a supportive community",
            subtitle = "We can help you to be a better version of yourself.",
            image = R.drawable.onboarding4
        )
    )

    OnBoardingPager(pages = pages, onFinish = {
        viewmodel.completeOnboarding()
    })
}