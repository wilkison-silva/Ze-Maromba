package br.com.zemaromba.presentation.features.sets_creation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.domain.repository.SetRepository
import br.com.zemaromba.presentation.features.sets_creation.screen.state.CreateExerciseState
import br.com.zemaromba.presentation.model.ExerciseView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CreateSetFlowViewModel @Inject constructor(
    private val setRepository: SetRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateExerciseState())
    val state = _state.asStateFlow()

    fun retrieveSet(setId: Long) {
        if (setId > 0 && _state.value.mustRetrieveExercise) {
            viewModelScope.launch {
                val setView = setRepository
                    .getSetById(id = setId)
                    .toSetView()
                _state.update {
                    it.copy(
                        setId = setView.id,
                        selectedExercise = setView.exerciseView,
                        seriesValue = setView.quantity.toString(),
                        repetitionsValue = setView.repetitions.toString(),
                        weightValue = setView.weight.toString(),
                        restingTimeValue = setView.restingTime.toString(),
                        observation = setView.observation,
                        isExerciseRetrieved = true
                    )
                }
            }
        }
    }

    fun updateProgressBar(
        initialProgress: Float,
        targetProgress: Float
    ) {
        _state.update {
            it.copy(
                progressBarInitial = initialProgress,
                progressBarTarget = targetProgress
            )
        }
    }

    fun updateMustRetrieveExercise(value: Boolean) {
        _state.update {
            it.copy(
                mustRetrieveExercise = value
            )
        }
    }

    fun updateFlowData(
        selectedExercise: ExerciseView,
        trainingId: Long
    ) {
        _state.update {
            it.copy(
                selectedExercise = selectedExercise,
                trainingId = trainingId
            )
        }
    }

    fun updateFlowData(
        seriesValue: String,
        repetitionsValue: String,
        weightValue: String,
        restingTimeValue: String,
    ) {
        _state.update {
            it.copy(
                seriesValue = seriesValue,
                repetitionsValue = repetitionsValue,
                weightValue = weightValue,
                restingTimeValue = restingTimeValue
            )
        }
    }
}