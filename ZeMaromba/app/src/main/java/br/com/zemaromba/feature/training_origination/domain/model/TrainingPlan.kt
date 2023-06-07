package br.com.zemaromba.feature.training_origination.domain.model

data class TrainingPlan(
    val name: String,
    val trainings: List<Training>
)