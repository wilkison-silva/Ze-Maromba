package br.com.zemaromba.presentation.training_plan.screen.event

sealed class TrainingPlanManagementEvents {
    object OnSaveTrainingPlan : TrainingPlanManagementEvents()
    data class OnShowWarningAboutRemoving(val showDialog: Boolean) : TrainingPlanManagementEvents()
    object OnDeleteTrainingPlan : TrainingPlanManagementEvents()
    data class OnEnterName(val trainingPlanName: String) : TrainingPlanManagementEvents()
}
