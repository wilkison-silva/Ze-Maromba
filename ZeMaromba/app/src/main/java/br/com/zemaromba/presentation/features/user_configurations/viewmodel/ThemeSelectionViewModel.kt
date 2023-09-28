package br.com.zemaromba.presentation.features.user_configurations.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.R
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.features.user_configurations.model.SelectableThemeItemView
import br.com.zemaromba.presentation.features.user_configurations.screen.state.ThemeSelectionScreenState
import br.com.zemaromba.presentation.features.user_configurations.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ThemeSelectionViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ThemeSelectionScreenState())
    val state = _state.asStateFlow()

    init {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            val themesListWithDynamicTheme = listOf(
                SelectableThemeItemView(
                    name = R.string.light_theme_name,
                    isSelected = false,
                    themeType = Theme.LIGHT
                ),
                SelectableThemeItemView(
                    name = R.string.dark_theme_name,
                    isSelected = false,
                    themeType = Theme.DARK
                )
            )
            _state.update {
                it.copy(selectableThemeItems = themesListWithDynamicTheme)
            }
        }
        getSelectedTheme()
    }

    private fun getSelectedTheme() {
        userRepository
            .getTheme()
            .onEach { themeName ->
                val theme = Theme.valueOf(themeName)
                val newThemeList = _state.value.selectableThemeItems.map {
                    it.copy(isSelected = it.themeType == theme)
                }
                _state.update {
                    it.copy(selectableThemeItems = newThemeList)
                }
            }.launchIn(viewModelScope)
    }

    fun onSelectedTheme(theme: Theme) {
        viewModelScope.launch {
            userRepository.saveTheme(themeName = theme.name)
        }
    }
}