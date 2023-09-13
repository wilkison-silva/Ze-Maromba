package br.com.zemaromba.presentation.home.screen.state

data class UserManagementState(
    val name: String = "",
    val navigateBack: Boolean = false,
    val nameIsBlank: Boolean = false,
)
