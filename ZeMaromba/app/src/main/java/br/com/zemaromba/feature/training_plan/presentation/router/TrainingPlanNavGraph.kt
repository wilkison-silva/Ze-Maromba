package br.com.zemaromba.feature.training_plan.presentation.router

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.orZero

fun NavGraphBuilder.trainingPlanGraph(
    navController: NavController,
    trainingPlanId: Long?
) {
    navigation(
        startDestination = TrainingPlanRouter.TrainingPlanGraph.route,
        route = TrainingPlanRouter
            .TrainingPlanHomeScreen
            .getRouteWithTrainingPlanId(trainingPlanId = trainingPlanId.orZero())
    ) {

    }
}