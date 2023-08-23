package br.com.zemaromba.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.home.screen.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getUserName()
    }

    private fun getUserName() {
        userRepository
            .getName()
            .onEach { userName ->
                _state.update {
                    it.copy(
                        userName = userName
                    )
                }
            }.launchIn(viewModelScope)
    }
}