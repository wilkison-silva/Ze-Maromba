package br.com.zemaromba.presentation.training_plan.screen.state

import br.com.zemaromba.presentation.model.TrainingPlanView

data class TrainingPlanState(
    val trainingPlanList: List<TrainingPlanView> = emptyList(),
) {
    val showMessage: Boolean = trainingPlanList.isEmpty()
}