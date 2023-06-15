package br.com.zemaromba.feature.exercise.presentation.model

data class ExerciseView(
    val id: Long,
    val name: String,
    val favoriteIcon: Int,
    val muscleGroups: List<Int>
)
