package br.com.zemaromba.presentation.features.training_plan.screen.event

sealed class TrainingManagementEvents {
    object OnSaveTraining : TrainingManagementEvents()
    data class OnShowWarningAboutRemoving(val showDialog: Boolean) : TrainingManagementEvents()
    object OnDeleteTraining : TrainingManagementEvents()
    data class OnEnterName(val trainingName: String) : TrainingManagementEvents()
}