package br.com.zemaromba.feature.exercise.presentation.router

sealed class ExerciseRouter(val route: String) {
    object ExerciseGraph : ExerciseRouter(route = baseGraphRoute)
    object ExercisesListScreen : ExerciseRouter(route = "$baseGraphRoute/exercises_list")
    object ExerciseManagementScreen : ExerciseRouter(
        route = "$baseGraphRoute/exercise_management/{${exerciseId}}"
    ) {
        fun getRouteWithExerciseId(exerciseId: Long): String {
            return "$baseGraphRoute/exercise_management/$exerciseId"
        }
    }

    companion object Params {
        const val baseGraphRoute = "exercises"
        const val exerciseId = "exercise_id"
    }
}