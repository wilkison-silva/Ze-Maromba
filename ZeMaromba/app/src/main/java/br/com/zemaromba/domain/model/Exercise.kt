package br.com.zemaromba.domain.model

import br.com.zemaromba.R
import br.com.zemaromba.presentation.model.ExerciseView

data class Exercise(
    val id: Long,
    val name: String,
    val favorite: Boolean,
    val muscleGroupList: List<MuscleGroup>,
    val urlLink: String?,
    val videoId: String?,
) {
    fun toExerciseView(): ExerciseView {
        return ExerciseView(
            id = this.id,
            name = this.name,
            favoriteIcon = if (this.favorite) R.drawable.ic_star_filled else R.drawable.ic_star_border,
            muscleGroups = this.muscleGroupList.map { muscleGroup -> muscleGroup.nameRes },
            urlLink = this.urlLink,
            videoId = this.videoId
        )
    }
}
