package br.com.zemaromba.feature.training_plan.presentation.router

import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingPlanListScreen
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanListViewModel

fun NavGraphBuilder.trainingPlanGraph(
    navController: NavController,
) {
    navigation(
        startDestination = TrainingPlanRouter.TrainingPlanListScreen.route,
        route = TrainingPlanRouter.TrainingPlanGraph.route
    ) {
        composable(route = TrainingPlanRouter.TrainingPlanListScreen.route) {
            val viewModel: TrainingPlanListViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            TrainingPlanListScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOpenTrainingPlan = { trainingPlanId ->
                    navController.navigate(
                        route = TrainingPlanRouter
                            .TrainingPlanManagementScreen
                            .getRouteWithTrainingPlanId(trainingPlanId = trainingPlanId)
                    )
                },
                onCreateTrainingPlan = {

                }
            )
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