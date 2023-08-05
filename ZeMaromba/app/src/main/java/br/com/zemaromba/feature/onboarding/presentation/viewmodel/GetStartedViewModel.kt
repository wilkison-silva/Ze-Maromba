package br.com.zemaromba.feature.onboarding.presentation.viewmodel

import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.core_ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@HiltViewModel
class GetStartedViewModel @Inject constructor(
    userDataStore: UserDataStore
) : BaseViewModel() {

    private val _state = MutableStateFlow(GetStartedState())
    val state = _state.asStateFlow()

    init {
        userDataStore
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
            }.launchIn(scope)
    }
}

data class GetStartedState(
    val userNameIsValid: Boolean = false,
    val loadingScreen: Boolean = true,
    val userName: String = ""
)