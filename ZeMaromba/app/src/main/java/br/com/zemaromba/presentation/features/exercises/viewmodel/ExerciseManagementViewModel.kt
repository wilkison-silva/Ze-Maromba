package br.com.zemaromba.presentation.features.exercises.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.zemaromba.common.extensions.isValidUrl
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.domain.repository.ExercisesRepository
import br.com.zemaromba.presentation.features.exercises.screen.event.ExerciseManagementEvents
import br.com.zemaromba.presentation.features.exercises.screen.state.ExerciseManagementState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExerciseManagementViewModel @Inject constructor(
    private val exercisesRepository: ExercisesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseManagementState())
    val state = _state.asStateFlow()

    fun updateUrlLinkValue(url: String) {
        _state.update {
            it.copy(
                urlLink = url,
                urlHasError = false
            )
        }
    }

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

                val typedUrl = _state.value.urlLink.orEmpty()
                if (typedUrl.isNotBlank()) {
                    if (!typedUrl.isValidUrl()) {
                        _state.update {
                            it.copy(urlHasError = true)
                        }
                        return
                    }
                }
                val muscleGroupList = state.value.muscleGroupCheckBoxStates
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
                        urlLink = _state.value.urlLink,
                        mayExclude = !_state.value.isNativeFromApp,
                        isNativeFromApp = _state.value.isNativeFromApp
                    )
                    _state.update { it.copy(navigateBack = true) }
                }
            }

            is ExerciseManagementEvents.OnMuscleGroupSelection -> {
                val checkBoxesState = _state.value.muscleGroupCheckBoxStates.toMutableList().apply {
                    this[event.id] = this[event.id].copy(isSelected = event.isSelected)
                }
                _state.update { it.copy(muscleGroupCheckBoxStates = checkBoxesState) }
            }

            is ExerciseManagementEvents.OnDeleteExercise -> {
                _state.update { it.copy(showDialog = false) }

                state.value.exerciseId?.let { exerciseId ->
                    exercisesRepository
                        .deleteExercise(exerciseId = exerciseId)
                        .onEach { itemWasRemoved ->
                            if (itemWasRemoved) {
                                _state.update { it.copy(navigateBack = true) }
                            }
                        }.launchIn(viewModelScope)
                }
            }

            is ExerciseManagementEvents.OnShowWarningAboutRemoving -> {
                _state.update { it.copy(showDialog = event.showDialog) }
            }
        }
    }

    fun retrieveExercise(exerciseId: Long) {
        if (exerciseId > 0) {
            exercisesRepository
                .getExerciseWithMuscles(exerciseId = exerciseId)
                .onEach { exercise ->
                    _state.update {
                        it.copy(
                            exerciseId = exercise.id,
                            name = exercise.name,
                            mayExclude = exercise.mayExclude,
                            isNativeFromApp = exercise.isNativeFromApp,
                            urlLink = exercise.urlLink,
                            muscleGroupCheckBoxStates = it.muscleGroupCheckBoxStates.toMutableList()
                                .apply {
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
                }.launchIn(viewModelScope)
        }
    }
}