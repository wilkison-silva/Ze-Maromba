package br.com.zemaromba.presentation.training_plan.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.TrainingPlanRepository
import br.com.zemaromba.presentation.training_plan.screen.state.TrainingListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TrainingListState())
    val state = _state.asStateFlow()

    fun getTrainings(trainingPlanId: Long) {
        trainingPlanRepository
            .getTrainingsByTrainingPlanId(trainingPlanId = trainingPlanId)
            .onEach { trainingList ->
                val trainingSummaryViewList = trainingList.map { it.toTrainingSummaryView() }
                _state.update {
                    it.copy(
                        trainingSummaryViewList = trainingSummaryViewList,
                        isLoadingTrainingList = false
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun retrieveTrainingPlan(trainingPlanId: Long) {
        if (trainingPlanId > 0) {
            trainingPlanRepository
                .getTrainingPlanById(id = trainingPlanId)
                .onEach {
                        trainingPlan ->
                    _state.update {
                        it.copy(
                            trainingPlanName = trainingPlan.name,
                            isRetrievingTrainingPlan = false
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }
}