package br.com.zemaromba.presentation.core_ui

import br.com.zemaromba.presentation.features.user_configurations.model.Theme

data class MainActivityState(
    val selectedTheme: Theme = Theme.LIGHT
)