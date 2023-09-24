package br.com.zemaromba.presentation.features.user_configurations.screen.state

import br.com.zemaromba.R
import br.com.zemaromba.presentation.model.SelectableThemeItemView
import br.com.zemaromba.presentation.model.Theme

data class ThemeSelectionScreenState(
    val selectableThemeItems: List<SelectableThemeItemView> = listOf(
        SelectableThemeItemView(
            name = R.string.light_theme_name,
            isSelected = false,
            themeType = Theme.LIGHT
        ),
        SelectableThemeItemView(
            name = R.string.dark_theme_name,
            isSelected = false,
            themeType = Theme.DARK
        ),
        SelectableThemeItemView(
            name = R.string.dynamic_theme_name,
            isSelected = false,
            themeType = Theme.DYNAMIC
        )
    )
)