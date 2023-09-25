package br.com.zemaromba.presentation.features.onboarding.screen.event

sealed class UserOriginationNameEvents {
    data class OnSaveName(val name: String) : UserOriginationNameEvents()
    data class OnEnterNewName(val name: String) : UserOriginationNameEvents()
}