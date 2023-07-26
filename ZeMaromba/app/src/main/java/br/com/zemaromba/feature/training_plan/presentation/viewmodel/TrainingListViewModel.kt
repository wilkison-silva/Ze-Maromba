package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.toTrainingView
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import br.com.zemaromba.feature.training_plan.presentation.model.TrainingView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository
) : ViewModel() {
    private val _state = MutableStateFlow(TrainingListState())
    val state = _state.asStateFlow()

    fun getTrainings(trainingPlanId: Long) {
        viewModelScope.launch {
            trainingPlanRepository
                .getTrainingsByTrainingPlanId(trainingPlanId = trainingPlanId)
                .collectLatest { trainingList ->
                    val trainingViewList = trainingList.map { it.toTrainingView()}
                    _state.update {
                        it.copy(trainingViewList = trainingViewList)
                    }
                }
        }
    }

    fun setTrainingPlanName(name: String) {
        _state.update { it.copy(trainingPlanName = name) }
    }
}

data class TrainingListState(
    val trainingViewList: List<TrainingView> = emptyList(),
    val trainingPlanName: String = ""
) {
    val showMessage: Boolean = trainingViewList.isEmpty()
}