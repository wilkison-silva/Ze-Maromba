package br.com.zemaromba.presentation.training_plan.screen.state

import br.com.zemaromba.presentation.model.TrainingSummaryView

data class TrainingListState(
    val trainingSummaryViewList: List<TrainingSummaryView> = emptyList(),
    val trainingPlanName: String = ""
) {
    val showMessage: Boolean = trainingSummaryViewList.isEmpty()
}