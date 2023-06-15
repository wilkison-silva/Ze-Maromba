package br.com.zemaromba.feature.exercise.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExerciseManagementViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseManagementState())
    val state = _state.asStateFlow()

    fun onEvent(event: ExerciseManagementEvents) {
        when (event) {
            is ExerciseManagementEvents.OnEnterName -> {
                _state.update { it.copy(name = event.exerciseName) }
            }

            is ExerciseManagementEvents.OnSaveExercise -> {
                val name = state.value.name
                val muscleGroupList = state.value.muscleGroupCheckBox
                    .filter { it.isSelected }
                    .mapNotNull { muscleGroupCheckBox ->
                        MuscleGroup.values().find {
                            it.nameRes == muscleGroupCheckBox.nameRes
                        }
                    }
                viewModelScope.launch {
                    exercisesRepository.createExercise(
                        name = name,
                        muscleGroupList = muscleGroupList
                    )
                    Log.i("Testando", "Salvou no banco")
                }
            }

            is ExerciseManagementEvents.OnMuscleGroupSelection -> {
                val checkBoxesState = _state.value.muscleGroupCheckBox.toMutableList().apply {
                    this[event.id] = this[event.id].copy(isSelected = event.isSelected)
                }
                _state.update { it.copy(muscleGroupCheckBox = checkBoxesState) }
            }
        }
    }

}

data class MuscleGroupCheckBox(
    val nameRes: Int,
    var isSelected: Boolean
)

sealed class ExerciseManagementEvents() {
    object OnSaveExercise : ExerciseManagementEvents()
    data class OnEnterName(val exerciseName: String) : ExerciseManagementEvents()
    data class OnMuscleGroupSelection(val id: Int, val isSelected: Boolean) :
        ExerciseManagementEvents()
}

data class ExerciseManagementState(
    val name: String = "",
    val muscleGroupCheckBox: List<MuscleGroupCheckBox> = MuscleGroup.values().map {
        MuscleGroupCheckBox(
            nameRes = it.nameRes,
            isSelected = false
        )
    }
)