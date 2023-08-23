package br.com.zemaromba.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ExerciseView(
    val id: Long,
    val name: String,
    @DrawableRes val favoriteIcon: Int,
    @StringRes val muscleGroups: List<Int>,
    val urlLink: String?,
    val videoId: String?,
    val isEditable: Boolean
)
