package br.com.zemaromba.presentation.core_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.features.user_configurations.model.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    init {
        getSelectedTheme()
    }

    private fun getSelectedTheme() {
        userRepository
            .getTheme()
            .onEach { themeName ->
                _state.update {
                    it.copy(
                        selectedTheme = Theme.valueOf(themeName)
                    )
                }
            }.launchIn(viewModelScope)
    }
}