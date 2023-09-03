package br.com.zemaromba.presentation.navigation.graph

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
import br.com.zemaromba.presentation.navigation.router.TrainingPlanRouter
import br.com.zemaromba.presentation.navigation.router.TrainingRouter
import br.com.zemaromba.presentation.training_plan.screen.SetsListScreen
import br.com.zemaromba.presentation.training_plan.screen.TrainingListScreen
import br.com.zemaromba.presentation.training_plan.screen.TrainingManagementScreen
import br.com.zemaromba.presentation.training_plan.screen.TrainingPlanListScreen
import br.com.zemaromba.presentation.training_plan.screen.TrainingPlanManagementScreen
import br.com.zemaromba.presentation.training_plan.screen.event.TrainingManagementEvents
import br.com.zemaromba.presentation.training_plan.screen.event.TrainingPlanManagementEvents
import br.com.zemaromba.presentation.training_plan.viewmodel.SetListViewModel
import br.com.zemaromba.presentation.training_plan.viewmodel.TrainingListViewModel
import br.com.zemaromba.presentation.training_plan.viewmodel.TrainingManagementViewModel
import br.com.zemaromba.presentation.training_plan.viewmodel.TrainingPlanListViewModel
import br.com.zemaromba.presentation.training_plan.viewmodel.TrainingPlanManagementViewModel

fun NavGraphBuilder.trainingPlanGraph(
    navController: NavController,
    openYoutube: (videoId: String) -> Unit,
    onCreateNewSet: (trainingId: Long, setId: Long) -> Unit
) {
    navigation(
        startDestination = TrainingPlanRouter.TrainingPlanListScreen.route,
        route = TrainingPlanRouter.TrainingPlanGraph.route
    ) {
        composableWithTransitionAnimation(
            route = TrainingPlanRouter.TrainingPlanListScreen.route
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
                        route = TrainingPlanRouter.TrainingsListScreen.getRoute(trainingPlanId = trainingPlanId)
                    )
                },
                onCreateTrainingPlan = {
                    navController.navigate(
                        route = TrainingPlanRouter.TrainingPlanManagementScreen.getRouteWithTrainingPlanId(
                            trainingPlanId = 0
                        )
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanRouter.TrainingPlanManagementScreen.route,
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
                        route = TrainingRouter.SetsListScreen.getRouteWithParameters(
                            trainingId = it,
                            trainingPlanId = trainingPlanId
                        )
                    )
                },
                onCreateTraining = {
                    navController.navigate(
                        route = TrainingRouter.TrainingManagementScreen.getRouteWithTrainingId(
                            trainingId = 0,
                            trainingPlanId = trainingPlanId
                        )
                    )
                },
                onOpenSettings = {
                    navController.navigate(
                        route = TrainingPlanRouter.TrainingPlanManagementScreen.getRouteWithTrainingPlanId(
                            trainingPlanId = trainingPlanId
                        )
                    )
                }
            )
        }



        composableWithTransitionAnimation(
            route = TrainingRouter.TrainingManagementScreen.route,
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
                onSaveTraining = {
                    viewModel.onEvent(event = TrainingManagementEvents.OnSaveTraining)
                },
                onDeleteTraining = {
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

        composableWithTransitionAnimation(
            route = TrainingRouter.SetsListScreen.route,
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
            val viewModel: SetListViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = Unit) {
                viewModel.retrieveSets(trainingId = trainingId)
            }
            LaunchedEffect(key1 = Unit) {
                viewModel.getTraining(trainingId = trainingId)
            }
            SetsListScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onShowListOptionsBottomSheet = { setId ->
                    viewModel.showListOptionsBottomSheet(setId = setId)
                },
                onHideListOptionsBottomSheet = {
                    viewModel.hideListOptionsBottomSheet()
                },
                onCreateSet = {
                    onCreateNewSet(trainingId, 0)
                },
                onOpenSettings = {
                    navController.navigate(
                        route = TrainingRouter.TrainingManagementScreen.getRouteWithTrainingId(
                            trainingId = trainingId,
                            trainingPlanId = trainingPlanId
                        )
                    )
                },
                onOpenYoutubeApp = { videoId ->
                    openYoutube(videoId)
                },
                onCompleteSet = { setId, isCompleted ->
                    viewModel.completeSet(setId = setId, isCompleted = isCompleted)
                },
                onEditSet = { setId: Long ->
                    viewModel.hideListOptionsBottomSheet()
                    onCreateNewSet(trainingId, setId)
                },
                onDeleteSet = { setId: Long ->
                    viewModel.deleteSet(setId = setId)
                }
            )
        }
    }
}