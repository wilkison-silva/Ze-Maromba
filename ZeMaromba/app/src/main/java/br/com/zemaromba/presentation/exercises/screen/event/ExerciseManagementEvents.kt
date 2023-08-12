package br.com.zemaromba.presentation.exercises.screen.event

sealed class ExerciseManagementEvents {
    object OnSaveExercise : ExerciseManagementEvents()
    data class OnShowWarningAboutRemoving(val showDialog: Boolean) : ExerciseManagementEvents()
    object OnDeleteExercise : ExerciseManagementEvents()
    data class OnEnterName(val exerciseName: String) : ExerciseManagementEvents()
    data class OnMuscleGroupSelection(val id: Int, val isSelected: Boolean) :
        ExerciseManagementEvents()
}
