package br.com.zemaromba.feature.training_plan.presentation.router

sealed class TrainingRouter(val route: String) {

    object TrainingGraph : TrainingRouter(route = baseGraphRoute)

    object TrainingListScreen : TrainingRouter(route = "$baseGraphRoute/list")

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
        fun getRoute(trainingPlanId: Long): String {
            return "$baseGraphRoute/sets_list/$trainingPlanId"
        }
    }

    companion object Params {
        private const val baseGraphRoute = "training"
        const val trainingId = "training_id"
        const val trainingPlanId = "training_plan_id"
    }
}