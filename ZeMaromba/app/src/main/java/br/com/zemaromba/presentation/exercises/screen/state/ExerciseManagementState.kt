package br.com.zemaromba.presentation.exercises.screen.state

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
    val navigateBack: Boolean = false,
    val nameIsBlank: Boolean = false,
    val showMessageAboutMuscleGroup: Boolean = false,
    val showDialog: Boolean = false
)
