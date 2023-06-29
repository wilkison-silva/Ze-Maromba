package br.com.zemaromba.core_domain.model

data class TrainingPlan(
    val id: Long,
    val name: String,
    val trainings: List<Training>
)