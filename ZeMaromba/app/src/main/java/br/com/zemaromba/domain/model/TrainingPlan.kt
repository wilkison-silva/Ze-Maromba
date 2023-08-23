package br.com.zemaromba.domain.model

import br.com.zemaromba.presentation.model.TrainingPlanView

data class TrainingPlan(
    val id: Long,
    val name: String,
    val trainings: List<Training>
) {
    fun toTrainingPlanView(): TrainingPlanView {
        return TrainingPlanView(
            id = this.id,
            name = this.name
        )
    }
}