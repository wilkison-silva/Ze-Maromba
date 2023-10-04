package br.com.zemaromba.data.model

import br.com.zemaromba.domain.model.MuscleGroup

data class ExerciseDTO(
    val id: Long,
    val name: String,
    val videoUrl: String,
    val muscleGroups: List<MuscleGroup>
) {
    fun toExerciseEntity(): ExerciseEntity {
        return ExerciseEntity(
            id = this.id,
            name = this.name,
            isFavorite = false,
            urlLink = this.videoUrl,
            mayExclude = false,
            isNativeFromApp = true
        )
    }
}
