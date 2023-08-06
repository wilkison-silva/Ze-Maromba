package br.com.zemaromba.common.extensions

import br.com.zemaromba.core_data.model.TrainingPlanEntity
import br.com.zemaromba.core_domain.model.TrainingPlan
import br.com.zemaromba.feature.training_plan.presentation.model.TrainingPlanView

fun TrainingPlanEntity.toTrainingPlan(): TrainingPlan {
    return TrainingPlan(
        id = this.id,
        name = this.name,
        trainings = emptyList()
    )
}

fun TrainingPlan.toTrainingPlanView(): TrainingPlanView {
    return TrainingPlanView(
        id = this.id,
        name = this.name
    )
}