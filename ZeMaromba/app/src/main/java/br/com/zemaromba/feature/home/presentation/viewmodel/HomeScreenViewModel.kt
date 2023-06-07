package br.com.zemaromba.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.feature.training_origination.domain.model.TrainingPlan
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
}

data class HomeState(
    val userName: String = "",
    val trainingsPlan: List<TrainingPlan> = emptyList()
)