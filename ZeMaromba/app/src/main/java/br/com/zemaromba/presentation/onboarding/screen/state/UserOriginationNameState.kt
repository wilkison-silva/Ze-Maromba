package br.com.zemaromba.presentation.onboarding.screen.state

data class UserOriginationNameState(
    val name: String = "",
    val showLoadingOnButton: Boolean = false,
    val nameSaved: Boolean = false
)