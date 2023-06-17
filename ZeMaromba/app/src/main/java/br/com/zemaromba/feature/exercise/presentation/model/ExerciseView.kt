package br.com.zemaromba.feature.exercise.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ExerciseView(
    val id: Long,
    val name: String,
    @DrawableRes val favoriteIcon: Int,
    @StringRes val muscleGroups: List<Int>
)
