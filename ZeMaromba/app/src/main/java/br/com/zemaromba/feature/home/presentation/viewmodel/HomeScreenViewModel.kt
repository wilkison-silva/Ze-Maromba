package br.com.zemaromba.feature.home.presentation.viewmodel

import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.core_ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getUserName()
    }

    private fun getUserName() {
        userDataStore
            .getName()
            .onEach { userName ->
                _state.update {
                    it.copy(
                        userName = userName
                    )
                }
            }.launchIn(scope)
    }
}

data class HomeState(
    val userName: String = ""
)