package br.com.zemaromba.presentation.features.exercises.screen.state

import br.com.zemaromba.domain.model.MuscleGroup

data class ExerciseManagementState(
    val exerciseId: Long? = null,
    val name: String = "",
    val muscleGroupCheckBoxStates: List<MuscleGroupCheckBoxState> = MuscleGroup.values().map {
        MuscleGroupCheckBoxState(
            nameRes = it.nameRes,
            isSelected = false
        )
    },
    val mayExclude: Boolean = false,
    val isNativeFromApp: Boolean = false,
    val urlLink: String? = null,
    val urlHasError: Boolean = false,
    val navigateBack: Boolean = false,
    val nameIsBlank: Boolean = false,
    val showMessageAboutMuscleGroup: Boolean = false,
    val showDialog: Boolean = false
)
