package br.com.zemaromba.presentation.navigation.router

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

    object TrainingsListScreen : TrainingPlanRouter(
        route = "$baseGraphRoute/trainings_list/{$trainingPlanId}"
    ) {
        fun getRoute(trainingPlanId: Long): String {
            return "$baseGraphRoute/trainings_list/$trainingPlanId"
        }
    }

    companion object Params {
        private const val baseGraphRoute = "training_plan"
        const val trainingPlanId = "training_plan_id"
    }
}