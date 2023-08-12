package br.com.zemaromba.presentation.training_plan.viewmodel

import androidx.lifecycle.ViewModel
import br.com.zemaromba.domain.repository.TrainingPlanRepository
import br.com.zemaromba.presentation.training_plan.screen.state.SetListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class SetListViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SetListState())
    val state = _state.asStateFlow()

    fun getTrainings(trainingPlanId: Long) {
//        trainingPlanRepository
//            .getTrainingsByTrainingPlanId(trainingPlanId = trainingPlanId)
//            .onEach { trainingList ->
//                val trainingSummaryViewList = trainingList.map { it.toTrainingSummaryView() }
//                _state.update {
//                    it.copy(trainingSummaryViewList = trainingSummaryViewList)
//                }
//            }.launchIn(viewModelScope)
    }

    fun retrieveSets(trainingId: Long) {
//        if (trainingId > 0) {
//            trainingPlanRepository
//                .getTrainingPlanById(id = trainingId)
//                .onEach { trainingPlan ->
//                    _state.update {
//                        it.copy(trainingPlanName = trainingPlan.name)
//                    }
//                }.launchIn(viewModelScope)
//        }
    }
}