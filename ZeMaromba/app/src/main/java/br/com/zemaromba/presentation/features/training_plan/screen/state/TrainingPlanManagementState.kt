package br.com.zemaromba.presentation.features.training_plan.screen.state

data class TrainingPlanManagementState(
    val trainingPlanId: Long? = null,
    val name: String = "",
    val navigateBack: Boolean = false,
    val onDeleteFinished: Boolean = false,
    val nameIsBlank: Boolean = false,
    val showDialog: Boolean = false
)