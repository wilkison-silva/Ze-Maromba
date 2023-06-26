package br.com.zemaromba.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.core_domain.model.TrainingPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getUserName()
    }

    private fun getUserName() {
        viewModelScope.launch {
            userDataStore.getName().collectLatest { userName ->
                _state.update {
                    it.copy(
                        userName = userName
                    )
                }
            }
        }
    }
}

data class HomeState(
    val userName: String = "",
    val trainingsPlan: List<TrainingPlan> = emptyList()
)