package br.com.zemaromba.presentation.navigation.router

sealed class TrainingRouter(val route: String) {

    object TrainingManagementScreen : TrainingRouter(
        route = "$baseGraphRoute/management/{$trainingId}/{$trainingPlanId}"
    ) {
        fun getRouteWithTrainingId(trainingId: Long, trainingPlanId: Long): String {
            return "$baseGraphRoute/management/$trainingId/$trainingPlanId"
        }
    }

    object SetsListScreen : TrainingRouter(
        route = "$baseGraphRoute/sets_list/{$trainingId}"
    ) {
        fun getRouteWithParameters(trainingId: Long): String {
            return "$baseGraphRoute/sets_list/$trainingId"
        }
    }

    companion object Params {
        private const val baseGraphRoute = "training"
        const val trainingId = "training_id"
        const val trainingPlanId = "training_plan_id"
    }
}