package br.com.zemaromba.feature.training_origination.domain.model

data class Exercise(
    val name: String,
    val primaryMuscleGroup: List<MuscleGroup>,
    val secondaryMuscleGroup: List<MuscleGroup>
)
