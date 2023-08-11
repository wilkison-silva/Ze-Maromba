package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.toTrainingSummaryView
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import br.com.zemaromba.feature.training_plan.presentation.model.SetView
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

data class SetListState(
    val setListView: List<SetView> = emptyList(),
    val trainingName: String = ""
) {
    val showMessage: Boolean = setListView.isEmpty()
}