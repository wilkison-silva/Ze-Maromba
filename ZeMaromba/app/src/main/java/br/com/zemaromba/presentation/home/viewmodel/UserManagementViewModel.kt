package br.com.zemaromba.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.UserRepository
import br.com.zemaromba.presentation.home.screen.state.UserManagementState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(UserManagementState())
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
                        name = userName
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun onSaveName() {
        if (state.value.name.isBlank()) {
            _state.update { it.copy(nameIsBlank = true) }
            return
        }
        val name = state.value.name.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.ROOT)
            else
                it.toString()
        }
        viewModelScope.launch {
            userRepository.saveName(name = name)
            _state.update { it.copy(navigateBack = true) }
        }
    }

    fun onEnterNewName(name: String) {
        _state.update {
            it.copy(
                name = name,
                nameIsBlank = false
            )
        }
    }

}