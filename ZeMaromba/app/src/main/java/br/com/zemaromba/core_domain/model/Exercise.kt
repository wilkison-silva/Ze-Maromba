package br.com.zemaromba.core_domain.model

data class Exercise(
    val id: Long,
    val name: String,
    val favorite: Boolean,
    val muscleGroup: List<MuscleGroup>,
)
