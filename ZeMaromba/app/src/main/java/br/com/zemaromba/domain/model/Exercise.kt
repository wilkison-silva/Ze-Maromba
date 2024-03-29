package br.com.zemaromba.domain.model

import br.com.zemaromba.R
import br.com.zemaromba.presentation.features.exercises.model.ExerciseView

data class Exercise(
    val id: Long,
    val name: String,
    val isFavorite: Boolean,
    val muscleGroupList: List<MuscleGroup>,
    val urlLink: String?,
    val mayExclude: Boolean,
    val isNativeFromApp: Boolean
) {
    fun toExerciseView(): ExerciseView {
        return ExerciseView(
            id = this.id,
            name = this.name,
            favoriteIcon = if (this.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_border,
            muscleGroups = this.muscleGroupList.map { muscleGroup -> muscleGroup.nameRes },
            urlLink = this.urlLink,
            mayExclude = this.mayExclude,
            isNativeFromApp = this.isNativeFromApp
        )
    }
}
