package br.com.zemaromba.presentation.navigation.router

sealed class SetCreationRouter(val route: String) {
    object SetCreationGraph : SetCreationRouter(route = "$baseGraphRoute/{$trainingId}") {
        fun getRouteWithTrainingId(trainingId: Long): String {
            return "$baseGraphRoute/$trainingId"
        }
    }
    object SelectExercise : SetCreationRouter(route = "$baseGraphRoute/set_creation")
    object ExerciseDetails : SetCreationRouter(route = "$baseGraphRoute/exercise_details")
    object ExerciseObservation : SetCreationRouter(route = "$baseGraphRoute/exercise_observation")

    companion object Params {
        const val baseGraphRoute = "sets"
        const val trainingId = "training_id"
    }
}