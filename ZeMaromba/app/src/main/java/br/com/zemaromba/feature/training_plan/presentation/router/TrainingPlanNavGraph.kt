package br.com.zemaromba.feature.training_plan.presentation.router

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingPlanListScreen
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingPlanManagementScreen
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanListViewModel
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanManagementEvents
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanManagementViewModel

fun NavGraphBuilder.trainingPlanGraph(
    navController: NavController,
    width: Int
) {
    navigation(
        startDestination = TrainingPlanRouter.TrainingPlanListScreen.route,
        route = TrainingPlanRouter.TrainingPlanGraph.route
    ) {
        composableWithTransitionAnimation(
            route = TrainingPlanRouter.TrainingPlanListScreen.route,
            width = width
        ) {
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
                    navController.navigate(
                        route = TrainingPlanRouter
                            .TrainingPlanManagementScreen
                            .getRouteWithTrainingPlanId(trainingPlanId = 0)
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanRouter.TrainingPlanManagementScreen.route,
            width = width,
            arguments = listOf(
                navArgument(name = TrainingPlanRouter.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanRouter.trainingPlanId)
                    .orZero()
            }
            val viewModel: TrainingPlanManagementViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = Unit) {
                viewModel.retrieveTrainingPlan(trainingPlanId)
            }
            TrainingPlanManagementScreen(
                state = state,
                onChangeName = {
                    viewModel.onEvent(
                        event = TrainingPlanManagementEvents.OnEnterName(trainingPlanName = it)
                    )
                },
                onSaveTrainingPlan = {
                    viewModel.onEvent(event = TrainingPlanManagementEvents.OnSaveTrainingPlan)
                },
                onDeleteTrainingPlan = {
                    viewModel.onEvent(event = TrainingPlanManagementEvents.OnDeleteTrainingPlan)
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onShowAlertAboutRemoving = {
                    viewModel.onEvent(
                        event = TrainingPlanManagementEvents
                            .OnShowWarningAboutRemoving(showDialog = it)
                    )
                }
            )
        }
    }
}