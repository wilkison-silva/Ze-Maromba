package br.com.zemaromba.feature.exercise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.core_domain.model.MuscleGroup
import br.com.zemaromba.feature.exercise.domain.repository.ExercisesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
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
                _state.update {
                    it.copy(
                        name = event.exerciseName,
                        nameIsBlank = false
                    )
                }
            }

            is ExerciseManagementEvents.OnSaveExercise -> {
                val id = state.value.exerciseId


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

                val muscleGroupList = state.value.muscleGroupCheckBox
                    .filter { it.isSelected }
                    .mapNotNull { muscleGroupCheckBox ->
                        MuscleGroup.values().find {
                            it.nameRes == muscleGroupCheckBox.nameRes
                        }
                    }

                if (muscleGroupList.isEmpty()) {
                    _state.update { it.copy(showMessageAboutMuscleGroup = true) }
                    return
                }
                viewModelScope.launch {
                    exercisesRepository.createExercise(
                        id = id,
                        name = name,
                        muscleGroupList = muscleGroupList,
                        urlLink = null
                    )
                    _state.update { it.copy(navigateBack = true) }
                }
            }

            is ExerciseManagementEvents.OnMuscleGroupSelection -> {
                val checkBoxesState = _state.value.muscleGroupCheckBox.toMutableList().apply {
                    this[event.id] = this[event.id].copy(isSelected = event.isSelected)
                }
                _state.update { it.copy(muscleGroupCheckBox = checkBoxesState) }
            }

            is ExerciseManagementEvents.OnDeleteExercise -> {
                _state.update { it.copy(showDialog = false) }
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

            is ExerciseManagementEvents.OnShowWarningAboutRemoving -> {
                _state.update { it.copy(showDialog = event.showDialog) }
            }
        }
    }

    fun retrieveExercise(exerciseId: Long) {
        if (exerciseId > 0) {
            viewModelScope.launch(Dispatchers.IO) {
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
    data class OnShowWarningAboutRemoving(val showDialog: Boolean) : ExerciseManagementEvents()
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
    val nameIsBlank: Boolean = false,
    val showMessageAboutMuscleGroup: Boolean = false,
    val showDialog: Boolean = false
)