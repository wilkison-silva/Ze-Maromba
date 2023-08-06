package br.com.zemaromba.feature.training_plan.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.feature.training_plan.domain.repository.TrainingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TrainingManagementViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TrainingManagementState())
    val state = _state.asStateFlow()

    fun onEvent(event: TrainingManagementEvents) {
        when (event) {
            is TrainingManagementEvents.OnEnterName -> {
                _state.update {
                    it.copy(
                        name = event.trainingName,
                        nameIsBlank = false
                    )
                }
            }

            is TrainingManagementEvents.OnSaveTraining -> {
                val id = state.value.trainingId


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
                    trainingRepository.createTraining(
                        id = id,
                        name = name,
                        trainingPlanId = _state.value.trainingPlanId.orZero()
                    )
                    _state.update { it.copy(navigateBack = true) }
                }
            }

            is TrainingManagementEvents.OnDeleteTraining -> {
                _state.update { it.copy(showDialog = false) }
                viewModelScope.launch {
                    state.value.trainingId?.let { trainingId ->
                        val deleteResult =
                            trainingRepository.deleteTraining(id = trainingId)
                        if (deleteResult) {
                            _state.update { it.copy(onDeleteFinished = true) }
                        }
                    }
                }
            }

            is TrainingManagementEvents.OnShowWarningAboutRemoving -> {
                _state.update { it.copy(showDialog = event.showDialog) }
            }
        }
    }

    fun retrieveTraining(trainingId: Long, trainingPlanId: Long) {
        if (trainingId > 0) {
            viewModelScope.launch(Dispatchers.IO) {
                trainingRepository
                    .getTrainingById(id = trainingId)
                    .let { training ->
                        _state.update {
                            it.copy(
                                trainingId = training.id,
                                trainingPlanId = training.trainingPlanId,
                                name = training.name,
                            )
                        }
                    }
            }
        } else {
            _state.update {
                it.copy(trainingPlanId = trainingPlanId)
            }
        }
    }
}

sealed class TrainingManagementEvents {
    object OnSaveTraining : TrainingManagementEvents()
    data class OnShowWarningAboutRemoving(val showDialog: Boolean) : TrainingManagementEvents()
    object OnDeleteTraining : TrainingManagementEvents()
    data class OnEnterName(val trainingName: String) : TrainingManagementEvents()
}

data class TrainingManagementState(
    val trainingId: Long? = null,
    val trainingPlanId: Long? = null,
    val name: String = "",
    val navigateBack: Boolean = false,
    val onDeleteFinished: Boolean = false,
    val nameIsBlank: Boolean = false,
    val showDialog: Boolean = false
)