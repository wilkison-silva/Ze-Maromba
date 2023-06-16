package br.com.zemaromba.feature.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
                val id = state.value.exerciseId
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
                        id = id,
                        name = name,
                        muscleGroupList = muscleGroupList
                    )
                    _state.update { it.copy(navigateBack = true)}
                }
            }

            is ExerciseManagementEvents.OnMuscleGroupSelection -> {
                val checkBoxesState = _state.value.muscleGroupCheckBox.toMutableList().apply {
                    this[event.id] = this[event.id].copy(isSelected = event.isSelected)
                }
                _state.update { it.copy(muscleGroupCheckBox = checkBoxesState) }
            }

            is ExerciseManagementEvents.OnDeleteExercise -> {
                viewModelScope.launch {
                    state.value.exerciseId?.let { exerciseId ->
                        val deleteResult =
                            exercisesRepository.deleteExercise(exerciseId = exerciseId)
                        if (deleteResult) {
                            _state.update { it.copy(navigateBack = true) }
                        }
                    }
                }
            }
        }
    }

    fun retrieveExercise(exerciseId: Long) {
        if (exerciseId > 0) {
            CoroutineScope(Dispatchers.IO).launch {
                exercisesRepository
                    .getExerciseWithMuscles(exerciseId = exerciseId)
                    .let { exercise ->
                        _state.update {
                            it.copy(
                                exerciseId = exercise.id,
                                name = exercise.name,
                                muscleGroupCheckBox = it.muscleGroupCheckBox.toMutableList().apply {
                                    this.forEachIndexed { index, muscleGroupCheckBox ->
                                        exercise.muscleGroupList.find { muscleGroup ->
                                            muscleGroup.nameRes == muscleGroupCheckBox.nameRes
                                        }?.let {
                                            this[index].isSelected = true
                                        }
                                    }
                                }
                            )
                        }
                    }
            }
        }
    }
}

data class MuscleGroupCheckBox(
    val nameRes: Int,
    var isSelected: Boolean
)

sealed class ExerciseManagementEvents {
    object OnSaveExercise : ExerciseManagementEvents()
    object OnDeleteExercise : ExerciseManagementEvents()
    data class OnEnterName(val exerciseName: String) : ExerciseManagementEvents()
    data class OnMuscleGroupSelection(val id: Int, val isSelected: Boolean) :
        ExerciseManagementEvents()
}

data class ExerciseManagementState(
    val exerciseId: Long? = null,
    val name: String = "",
    val muscleGroupCheckBox: List<MuscleGroupCheckBox> = MuscleGroup.values().map {
        MuscleGroupCheckBox(
            nameRes = it.nameRes,
            isSelected = false
        )
    },
    val navigateBack: Boolean = false,
)