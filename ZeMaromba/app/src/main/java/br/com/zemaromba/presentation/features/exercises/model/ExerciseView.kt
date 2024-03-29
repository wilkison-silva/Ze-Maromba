package br.com.zemaromba.presentation.features.exercises.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ExerciseView(
    val id: Long,
    val name: String,
    @DrawableRes val favoriteIcon: Int,
    @StringRes val muscleGroups: List<Int>,
    val urlLink: String?,
    val mayExclude: Boolean,
    val isNativeFromApp: Boolean
)
