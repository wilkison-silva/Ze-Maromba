package br.com.zemaromba.presentation.sets.viewmodel

import androidx.lifecycle.ViewModel
import br.com.zemaromba.presentation.model.ExerciseView
import br.com.zemaromba.presentation.sets.screen.state.CreateExerciseState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class CreateSetFlowViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CreateExerciseState())
    val state = _state.asStateFlow()

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