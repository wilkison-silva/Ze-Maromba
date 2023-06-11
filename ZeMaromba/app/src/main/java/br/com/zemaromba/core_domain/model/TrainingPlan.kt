package br.com.zemaromba.core_domain.model

data class TrainingPlan(
    val name: String,
    val trainings: List<Training>
)