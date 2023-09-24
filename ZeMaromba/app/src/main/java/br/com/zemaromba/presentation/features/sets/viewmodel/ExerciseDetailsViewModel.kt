package br.com.zemaromba.presentation.features.sets.viewmodel

import androidx.lifecycle.ViewModel
import br.com.zemaromba.presentation.features.sets.screen.state.ExerciseDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ExerciseDetailsScreenState())
    val state = _state.asStateFlow()

    fun updateSeriesValue(value: String) {
        _state.update {
            it.copy(
                seriesValue = value
            )
        }
    }

    fun updateRepetitionsValue(value: String) {
        _state.update {
            it.copy(
                repetitionsValue = value
            )
        }
    }

    fun updateWeightValue(value: String) {
        _state.update {
            it.copy(
                weightValue = value
            )
        }
    }

    fun updateRestingTimeValue(value: String) {
        _state.update {
            it.copy(
                restingTimeValue = value
            )
        }
    }

    fun updateFillDetailsLater(value: Boolean) {
        _state.update {
            it.copy(
                fillDetailsLater = value,
                seriesValue = if (value) "0" else it.seriesValue,
                repetitionsValue = if (value) "0" else it.repetitionsValue,
                weightValue = if (value) "0" else it.weightValue,
                restingTimeValue = if (value) "0" else it.restingTimeValue
            )
        }
    }
}