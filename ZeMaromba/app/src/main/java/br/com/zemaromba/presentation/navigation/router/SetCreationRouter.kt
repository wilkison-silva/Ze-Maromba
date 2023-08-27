package br.com.zemaromba.presentation.navigation.router

sealed class SetCreationRouter(val route: String) {
    object SetCreationGraph : SetCreationRouter(route = baseGraphRoute)
    object SelectExercise : SetCreationRouter(route = "$baseGraphRoute/set_creation")
    object ExerciseDetails : SetCreationRouter(route = "$baseGraphRoute/exercise_details")
    object ExerciseObservation : SetCreationRouter(route = "$baseGraphRoute/exercise_observation")

    companion object Params {
        const val baseGraphRoute = "sets"
    }
}