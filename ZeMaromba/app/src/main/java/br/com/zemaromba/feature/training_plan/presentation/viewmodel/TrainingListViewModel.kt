package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.toTrainingSummaryView
import br.com.zemaromba.core_ui.viewmodel.BaseViewModel
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import br.com.zemaromba.feature.training_plan.presentation.model.TrainingSummaryView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(TrainingListState())
    val state = _state.asStateFlow()

    fun getTrainings(trainingPlanId: Long) {
        trainingPlanRepository
            .getTrainingsByTrainingPlanId(trainingPlanId = trainingPlanId)
            .onEach { trainingList ->
                val trainingSummaryViewList = trainingList.map { it.toTrainingSummaryView()}
                _state.update {
                    it.copy(trainingSummaryViewList = trainingSummaryViewList)
                }
            }.launchIn(scope)
    }

    fun retrieveTrainingPlan(trainingPlanId: Long) {
        if (trainingPlanId > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                trainingPlanRepository
                    .getTrainingPlanById(id = trainingPlanId)
                    .let { trainingPlan ->
                        _state.update {
                            it.copy(trainingPlanName = trainingPlan.name)
                        }
                    }
            }
        }
    }
}

data class TrainingListState(
    val trainingSummaryViewList: List<TrainingSummaryView> = emptyList(),
    val trainingPlanName: String = ""
) {
    val showMessage: Boolean = trainingSummaryViewList.isEmpty()
}