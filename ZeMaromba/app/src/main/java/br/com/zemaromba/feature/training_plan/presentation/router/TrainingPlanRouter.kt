package br.com.zemaromba.feature.training_plan.presentation.router

sealed class TrainingPlanRouter(val route: String) {

    object TrainingPlanGraph : TrainingPlanRouter(route = baseGraphRoute)

    object TrainingPlanListScreen : TrainingPlanRouter(route = "$baseGraphRoute/list")

    object TrainingPlanManagementScreen : TrainingPlanRouter(
        route = "$baseGraphRoute/management/{$trainingPlanId}"
    ) {
        fun getRouteWithTrainingPlanId(trainingPlanId: Long): String {
            return "$baseGraphRoute/management/$trainingPlanId"
        }
    }

    companion object Params {
        private const val baseGraphRoute = "training_plan"
        const val trainingPlanId = "training_plan_id"
    }
}