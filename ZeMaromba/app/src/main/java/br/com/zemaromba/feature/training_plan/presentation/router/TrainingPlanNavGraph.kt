package br.com.zemaromba.feature.training_plan.presentation.router

import android.util.Log
import androidx.compose.material3.Text
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
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingListScreen
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingManagementScreen
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingPlanListScreen
import br.com.zemaromba.feature.training_plan.presentation.screen.TrainingPlanManagementScreen
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingListViewModel
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingManagementEvents
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingManagementState
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingManagementViewModel
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanListViewModel
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanManagementEvents
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanManagementViewModel
import javax.security.auth.login.LoginException

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
                            .TrainingsListScreen
                            .getRoute(trainingPlanId = trainingPlanId)
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
                },
                onDeleteFinished = {
                    navController.popBackStack(
                        route = TrainingPlanRouter.TrainingPlanListScreen.route,
                        inclusive = false
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanRouter.TrainingsListScreen.route,
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
            val viewModel: TrainingListViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = Unit) {
                viewModel.retrieveTrainingPlan(trainingPlanId = trainingPlanId)
            }
            LaunchedEffect(key1 = Unit) {
                viewModel.getTrainings(trainingPlanId = trainingPlanId)
            }
            TrainingListScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onOpenTraining = {
                    navController.navigate(
                        route = TrainingRouter
                            .TrainingManagementScreen
                            .getRouteWithTrainingId(
                                trainingId = it,
                                trainingPlanId = trainingPlanId
                            )
                    )
                },
                onCreateTraining = {
                    navController.navigate(
                        route = TrainingRouter
                            .TrainingManagementScreen
                            .getRouteWithTrainingId(
                                trainingId = 0,
                                trainingPlanId = trainingPlanId
                            )
                    )
                },
                onOpenSettings = {
                    navController.navigate(
                        route = TrainingPlanRouter
                            .TrainingPlanManagementScreen
                            .getRouteWithTrainingPlanId(trainingPlanId = trainingPlanId)
                    )
                }
            )
        }



        composableWithTransitionAnimation(
            route = TrainingRouter.TrainingManagementScreen.route,
            width = width,
            arguments = listOf(
                navArgument(name = TrainingRouter.trainingId) {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument(name = TrainingRouter.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(TrainingRouter.trainingId)
                    .orZero()
            }
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingRouter.trainingPlanId)
                    .orZero()
            }
            val viewModel: TrainingManagementViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = Unit) {
                viewModel.retrieveTraining(
                    trainingId = trainingId,
                    trainingPlanId = trainingPlanId
                )
            }
            TrainingManagementScreen(
                state = state,
                onChangeName = {
                    viewModel.onEvent(
                        event = TrainingManagementEvents.OnEnterName(trainingName = it)
                    )
                },
                onSaveTrainingPlan = {
                    viewModel.onEvent(event = TrainingManagementEvents.OnSaveTraining)
                },
                onDeleteTrainingPlan = {
                    viewModel.onEvent(event = TrainingManagementEvents.OnDeleteTraining)
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                onShowAlertAboutRemoving = {
                    viewModel.onEvent(
                        event = TrainingManagementEvents
                            .OnShowWarningAboutRemoving(showDialog = it)
                    )
                },
                onDeleteFinished = {
                    navController.popBackStack(
                        route = TrainingPlanRouter.TrainingPlanListScreen.route,
                        inclusive = false
                    )
                }
            )
        }

    }
}