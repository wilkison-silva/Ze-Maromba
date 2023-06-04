package br.com.zemaromba.feature.onboarding.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_domain.datastore.UserDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserOriginationNameViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(UserOriginationNameState())
    val state = _state.asStateFlow()

    fun onEvent(event: UserOriginationNameEvents) {
        when (event) {
            is UserOriginationNameEvents.OnSaveName -> {
                _state.update { it.copy(loadingScreen = true) }
                viewModelScope.launch {
                    userDataStore.saveName(name = event.name)
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

sealed class UserOriginationNameEvents {
    data class OnSaveName(val name: String) : UserOriginationNameEvents()
    data class OnEnterNewName(val name: String) : UserOriginationNameEvents()
}

data class UserOriginationNameState(
    val name: String = "",
    val loadingScreen: Boolean = false,
    val nameSaved: Boolean = false
)