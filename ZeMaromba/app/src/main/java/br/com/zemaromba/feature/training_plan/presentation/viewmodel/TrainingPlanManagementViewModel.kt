package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingPlanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TrainingPlanManagementViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrainingPlanManagementState())
    val state = _state.asStateFlow()

    fun onEvent(event: TrainingPlanManagementEvents) {
        when (event) {
            is TrainingPlanManagementEvents.OnEnterName -> {
                _state.update {
                    it.copy(
                        name = event.trainingPlanName,
                        nameIsBlank = false
                    )
                }
            }

            is TrainingPlanManagementEvents.OnSaveTrainingPlan -> {
                val id = state.value.trainingPlanId


                if (state.value.name.isBlank()) {
                    _state.update { it.copy(nameIsBlank = true) }
                    return
                }
                val name = state.value.name.replaceFirstChar {
                    if (it.isLowerCase())
                        it.titlecase(Locale.ROOT)
                    else
                        it.toString()
                }

                viewModelScope.launch {
                    trainingPlanRepository.createTrainingPlan(
                        id = id,
                        name = name
                    )
                    _state.update { it.copy(navigateBack = true) }
                }
            }

            is TrainingPlanManagementEvents.OnDeleteTrainingPlan -> {
                _state.update { it.copy(showDialog = false) }
                viewModelScope.launch {
                    state.value.trainingPlanId?.let { trainingPlanId ->
                        val deleteResult =
                            trainingPlanRepository.deleteTrainingPlan(id = trainingPlanId)
                        if (deleteResult) {
                            _state.update { it.copy(onDeleteFinished = true) }
                        }
                    }
                }
            }

            is TrainingPlanManagementEvents.OnShowWarningAboutRemoving -> {
                _state.update { it.copy(showDialog = event.showDialog) }
            }
        }
    }

    fun retrieveTrainingPlan(trainingPlanId: Long) {
        if (trainingPlanId > 0) {
            trainingPlanRepository
                .getTrainingPlanById(id = trainingPlanId)
                .onEach { trainingPlan ->
                    _state.update {
                        it.copy(
                            trainingPlanId = trainingPlan.id,
                            name = trainingPlan.name,
                        )
                    }
                }.launchIn(viewModelScope)
        }
    }
}

sealed class TrainingPlanManagementEvents {
    object OnSaveTrainingPlan : TrainingPlanManagementEvents()
    data class OnShowWarningAboutRemoving(val showDialog: Boolean) : TrainingPlanManagementEvents()
    object OnDeleteTrainingPlan : TrainingPlanManagementEvents()
    data class OnEnterName(val trainingPlanName: String) : TrainingPlanManagementEvents()
}

data class TrainingPlanManagementState(
    val trainingPlanId: Long? = null,
    val name: String = "",
    val navigateBack: Boolean = false,
    val onDeleteFinished: Boolean = false,
    val nameIsBlank: Boolean = false,
    val showDialog: Boolean = false
)