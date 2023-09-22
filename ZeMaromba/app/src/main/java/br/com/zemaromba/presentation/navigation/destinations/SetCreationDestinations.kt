package br.com.zemaromba.presentation.navigation.destinations

sealed class SetCreationDestinations(val route: String) {
    data object SetCreationGraph : SetCreationDestinations(route = "$baseGraphRoute/{$trainingId}/{$setId}") {
        fun getRouteWithTrainingId(trainingId: Long, setId: Long): String {
            return "$baseGraphRoute/$trainingId/$setId"
        }
    }
    data object SelectExercise : SetCreationDestinations(route = "$baseGraphRoute/set_creation")
    data object ExerciseDetails : SetCreationDestinations(route = "$baseGraphRoute/exercise_details")
    data object ExerciseObservation : SetCreationDestinations(route = "$baseGraphRoute/exercise_observation")

    companion object Params {
        const val baseGraphRoute = "sets"
        const val trainingId = "training_id"
        const val setId = "set_id"
    }
}