package br.com.zemaromba.feature.exercise.presentation.router

sealed class ExerciseRouter(val route: String) {
    object ExerciseGraph : ExerciseRouter("exercises")
    object ExercisesListScreen : ExerciseRouter("exercises_list")
    object ExerciseManagementScreen : ExerciseRouter("exercise_management")
}