package br.com.zemaromba.presentation.features.training_plan.screen.state

import br.com.zemaromba.presentation.model.TrainingSummaryView

data class TrainingListState(
    val trainingSummaryViewList: List<TrainingSummaryView> = emptyList(),
    val trainingPlanName: String = "",
    val isLoadingTrainingList: Boolean = true,
    val isRetrievingTrainingPlan: Boolean = true
) {
    val showMessage: Boolean = trainingSummaryViewList.isEmpty()
}