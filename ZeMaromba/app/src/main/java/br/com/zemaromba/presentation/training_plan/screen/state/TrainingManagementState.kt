package br.com.zemaromba.presentation.training_plan.screen.state

data class TrainingManagementState(
    val trainingId: Long? = null,
    val trainingPlanId: Long? = null,
    val name: String = "",
    val navigateBack: Boolean = false,
    val onDeleteFinished: Boolean = false,
    val nameIsBlank: Boolean = false,
    val showDialog: Boolean = false
)