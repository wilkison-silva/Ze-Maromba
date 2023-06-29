package br.com.zemaromba.feature.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.toTrainingPlanView
import br.com.zemaromba.core_domain.datastore.UserDataStore
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.home.domain.repository.TrainingPlanRepository
import br.com.zemaromba.feature.home.presentation.model.TrainingPlanView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val trainingPlanRepository: TrainingPlanRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getUserName()
        getTrainingPlans()
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

    private fun getTrainingPlans() {
        viewModelScope.launch {
            trainingPlanRepository.getAllTrainingPlans().collectLatest { trainingPlanList ->
                val trainingPlanViewList =  trainingPlanList.map { it.toTrainingPlanView() }
                _state.update {
                    it.copy(trainingPlanList = trainingPlanViewList)
                }
            }
        }
    }
}

data class HomeState(
    val userName: String = "",
    val trainingPlanList: List<TrainingPlanView> = emptyList(),
) {
    val showMessage: Boolean = trainingPlanList.isEmpty()
}