package br.com.zemaromba.presentation.navigation.destinations

sealed class ExerciseDestinations(val route: String) {
    data object ExerciseGraph : ExerciseDestinations(route = baseGraphRoute)
    data object ExercisesListScreen : ExerciseDestinations(route = "$baseGraphRoute/exercises_list")
    data object ExerciseManagementScreen : ExerciseDestinations(
        route = "$baseGraphRoute/exercise_management/{$exerciseId}"
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