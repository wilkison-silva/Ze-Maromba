package br.com.zemaromba.presentation.features.onboarding.screen.state

data class GetStartedState(
    val userNameIsValid: Boolean = false,
    val loadingScreen: Boolean = true,
    val userName: String = ""
)
