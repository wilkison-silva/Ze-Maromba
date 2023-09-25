package br.com.zemaromba.presentation.features.user_configurations.model

import androidx.annotation.StringRes
import br.com.zemaromba.presentation.features.user_configurations.model.Theme

data class SelectableThemeItemView(
    @StringRes val name: Int,
    val isSelected: Boolean,
    val themeType: Theme
)