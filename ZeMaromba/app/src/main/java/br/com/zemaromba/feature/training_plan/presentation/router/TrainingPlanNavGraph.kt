package br.com.zemaromba.feature.training_plan.presentation.router

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

fun NavGraphBuilder.trainingPlanGraph(
    navController: NavController,
) {
    navigation(
        startDestination = TrainingPlanRouter.TrainingPlanHomeScreen.route,
        route = TrainingPlanRouter.TrainingPlanGraph.route
    ) {
        composable(
            route = TrainingPlanRouter.TrainingPlanHomeScreen.route,

        ) {
            Text(text = "home do plano de treino selecionado")
        }
        composable(
            route = TrainingPlanRouter.TrainingPlanManagementScreen.route,
            arguments = listOf(
                navArgument(name = TrainingPlanRouter.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            Text(text = "Gerenciar plano de treino")
        }
    }
}