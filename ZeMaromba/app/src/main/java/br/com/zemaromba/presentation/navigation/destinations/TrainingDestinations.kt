package br.com.zemaromba.presentation.navigation.destinations

sealed class TrainingDestinations(val route: String) {

    data object TrainingManagementScreen : TrainingDestinations(
        route = "$baseGraphRoute/management/{$trainingId}/{$trainingPlanId}"
    ) {
        fun getRouteWithTrainingId(trainingId: Long, trainingPlanId: Long): String {
            return "$baseGraphRoute/management/$trainingId/$trainingPlanId"
        }
    }

    data object SetsListScreen : TrainingDestinations(
        route = "$baseGraphRoute/sets_list/{$trainingId}/{$trainingPlanId}"
    ) {
        fun getRouteWithParameters(trainingId: Long, trainingPlanId: Long): String {
            return "$baseGraphRoute/sets_list/$trainingId/$trainingPlanId"
        }
    }

    companion object Params {
        private const val baseGraphRoute = "training"
        const val trainingId = "training_id"
        const val trainingPlanId = "training_plan_id"
    }
}