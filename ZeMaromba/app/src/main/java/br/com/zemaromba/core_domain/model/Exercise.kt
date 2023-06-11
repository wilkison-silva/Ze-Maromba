package br.com.zemaromba.core_domain.model

data class Exercise(
    val name: String,
    val muscleGroup: List<MuscleGroup>,
)
