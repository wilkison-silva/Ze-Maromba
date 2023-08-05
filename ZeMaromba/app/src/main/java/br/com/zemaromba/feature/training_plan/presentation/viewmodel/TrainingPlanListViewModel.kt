package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import br.com.zemaromba.common.extensions.toTrainingPlanView
import br.com.zemaromba.core_ui.viewmodel.BaseViewModel
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import br.com.zemaromba.feature.training_plan.presentation.model.TrainingPlanView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class TrainingPlanListViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(TrainingPlanState())
    val state = _state.asStateFlow()

    init {
        getTrainingPlans()
    }

    private fun getTrainingPlans() {
        trainingPlanRepository
            .getAllTrainingPlans()
            .onEach { trainingPlanList ->
                val trainingPlanViewList = trainingPlanList.map { it.toTrainingPlanView() }
                _state.update {
                    it.copy(trainingPlanList = trainingPlanViewList)
                }
            }.launchIn(scope)
    }
}

data class TrainingPlanState(
    val trainingPlanList: List<TrainingPlanView> = emptyList(),
) {
    val showMessage: Boolean = trainingPlanList.isEmpty()
}