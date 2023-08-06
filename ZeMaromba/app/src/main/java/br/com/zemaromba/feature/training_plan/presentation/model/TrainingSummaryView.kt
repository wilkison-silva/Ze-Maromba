package br.com.zemaromba.feature.training_plan.presentation.model

import br.com.zemaromba.core_domain.model.MuscleGroup

data class TrainingSummaryView(
    val id: Long,
    val name: String,
    val exercisesQuantity: Int,
    val muscleGroups: List<MuscleGroup>,
    val percentageDone: Int
) {
    val hasExercises = exercisesQuantity > 0
}
