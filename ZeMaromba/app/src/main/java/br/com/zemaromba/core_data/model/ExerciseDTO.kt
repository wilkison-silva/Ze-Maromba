package br.com.zemaromba.core_data.model

import br.com.zemaromba.core_domain.model.MuscleGroup

data class ExerciseDTO(
    val id: Long,
    val name: String,
    val videoUrl: String,
    val videoId: String,
    val muscleGroups: List<MuscleGroup>
)
