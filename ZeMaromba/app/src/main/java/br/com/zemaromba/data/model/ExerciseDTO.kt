package br.com.zemaromba.data.model

import br.com.zemaromba.domain.model.MuscleGroup

data class ExerciseDTO(
    val id: Long,
    val name: String,
    val videoUrl: String,
    val videoId: String,
    val muscleGroups: List<MuscleGroup>
) {
    fun toExerciseEntity(): ExerciseEntity {
        return ExerciseEntity(
            id = this.id,
            name = this.name,
            favorite = false,
            urlLink = this.videoUrl,
            videoId = this.videoId
        )
    }
}
