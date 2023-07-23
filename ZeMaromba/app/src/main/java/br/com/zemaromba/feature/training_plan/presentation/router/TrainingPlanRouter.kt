package br.com.zemaromba.feature.training_plan.presentation.router

sealed class TrainingPlanRouter(val route: String) {

    object TrainingPlanGraph : TrainingPlanRouter(route = baseGraphRoute)

    object TrainingPlanHomeScreen : TrainingPlanRouter(
        route = "$baseGraphRoute/{${Params.trainingPlanId}}"
    ) {
        fun getRouteWithTrainingPlanId(trainingPlanId: Long): String {
            return "$baseGraphRoute/$trainingPlanId"
        }
    }

    object TrainingPlanManagementScreen : TrainingPlanRouter(
        route = "$baseGraphRoute/management/{${Params.trainingPlanId}}"
    ) {
        fun getRouteWithTrainingPlanId(trainingPlanId: Long): String {
            return "$baseGraphRoute/management/$trainingPlanId"
        }
    }

    companion object Params {
        const val baseGraphRoute = "training_plan"
        const val trainingPlanId = "training_plan_id"
    }
}