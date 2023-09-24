package br.com.zemaromba.presentation.features.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.features.onboarding.screen.state.GetStartedState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GetStartedState())
    val state = _state.asStateFlow()

    init {
        userRepository
            .getName()
            .onStart {
                _state.update { it.copy(loadingScreen = true) }
            }
            .onEach { userName ->
                delay(1000)
                if (userName.isNotBlank()) {
                    _state.update {
                        it.copy(
                            userNameIsValid = true,
                            userName = userName
                        )
                    }
                } else {
                    _state.update {
                        it.copy(userNameIsValid = false, loadingScreen = false)
                    }
                }
            }.launchIn(viewModelScope)
    }
}