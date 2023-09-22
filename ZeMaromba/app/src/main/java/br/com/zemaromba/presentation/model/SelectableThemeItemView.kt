package br.com.zemaromba.presentation.model

import androidx.annotation.StringRes

data class SelectableThemeItemView(
    @StringRes val name: Int,
    val isSelected: Boolean,
    val themeType: Theme
)