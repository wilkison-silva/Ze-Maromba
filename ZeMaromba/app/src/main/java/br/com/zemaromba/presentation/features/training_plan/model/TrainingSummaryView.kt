package br.com.zemaromba.presentation.features.training_plan.model

import androidx.annotation.StringRes
import br.com.zemaromba.R
import br.com.zemaromba.domain.model.MuscleGroup

data class TrainingSummaryView(
    val id: Long,
    val name: String,
    val exercisesQuantity: Int,
    val muscleGroups: List<MuscleGroup>,
    val percentageDone: Int
) {
    val hasExercises = exercisesQuantity > 0

    @StringRes
    val exerciseText = if (exercisesQuantity > 1) {
        R.string.training_summary_card_exercises_quantity_plural
    } else {
        R.string.training_summary_card_exercises_quantity_singular
    }
}
