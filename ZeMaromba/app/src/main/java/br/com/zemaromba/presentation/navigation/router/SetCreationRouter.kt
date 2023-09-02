package br.com.zemaromba.presentation.navigation.router

sealed class SetCreationRouter(val route: String) {
    object SetCreationGraph : SetCreationRouter(route = "$baseGraphRoute/{$trainingId}/{$setId}") {
        fun getRouteWithTrainingId(trainingId: Long, setId: Long): String {
            return "$baseGraphRoute/$trainingId/$setId"
        }
    }
    object SelectExercise : SetCreationRouter(route = "$baseGraphRoute/set_creation")
    object ExerciseDetails : SetCreationRouter(route = "$baseGraphRoute/exercise_details")
    object ExerciseObservation : SetCreationRouter(route = "$baseGraphRoute/exercise_observation")

    companion object Params {
        const val baseGraphRoute = "sets"
        const val trainingId = "training_id"
        const val setId = "set_id"
    }
}