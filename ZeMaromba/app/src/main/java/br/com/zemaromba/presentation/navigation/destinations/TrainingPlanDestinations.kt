package br.com.zemaromba.presentation.navigation.destinations

sealed class TrainingPlanDestinations(val route: String) {

    data object TrainingPlanGraph : TrainingPlanDestinations(route = baseGraphRoute)

    data object TrainingPlanListScreen : TrainingPlanDestinations(route = "$baseGraphRoute/list")

    data object TrainingPlanManagementScreen : TrainingPlanDestinations(
        route = "$baseGraphRoute/management/{$trainingPlanId}"
    ) {
        fun getRouteWithTrainingPlanId(trainingPlanId: Long): String {
            return "$baseGraphRoute/management/$trainingPlanId"
        }
    }

    data object TrainingsListScreen : TrainingPlanDestinations(
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