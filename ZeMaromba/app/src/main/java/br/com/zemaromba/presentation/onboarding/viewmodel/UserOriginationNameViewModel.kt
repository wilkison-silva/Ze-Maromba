package br.com.zemaromba.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.onboarding.screen.event.UserOriginationNameEvents
import br.com.zemaromba.presentation.onboarding.screen.state.UserOriginationNameState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserOriginationNameViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserOriginationNameState())
    val state = _state.asStateFlow()

    fun onEvent(event: UserOriginationNameEvents) {
        when (event) {
            is UserOriginationNameEvents.OnSaveName -> {
                _state.update { it.copy(showLoadingOnButton = true) }
                viewModelScope.launch {
                    userRepository.saveName(name = event.name)
                    delay(1000)
                    _state.update {
                        it.copy(
                            name = event.name,
                            nameSaved = true
                        )
                    }
                }
            }

            is UserOriginationNameEvents.OnEnterNewName -> {
                _state.update { it.copy(name = event.name) }
            }
        }
    }
}