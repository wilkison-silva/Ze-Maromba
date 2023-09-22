package br.com.zemaromba.presentation.navigation.nav_graphs

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
import br.com.zemaromba.presentation.navigation.destinations.TrainingPlanDestinations
import br.com.zemaromba.presentation.navigation.destinations.TrainingDestinations
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
        startDestination = TrainingPlanDestinations.TrainingPlanListScreen.route,
        route = TrainingPlanDestinations.TrainingPlanGraph.route
    ) {
        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.TrainingPlanListScreen.route
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
                        route = TrainingPlanDestinations.TrainingsListScreen.getRoute(trainingPlanId = trainingPlanId)
                    )
                },
                onCreateTrainingPlan = {
                    navController.navigate(
                        route = TrainingPlanDestinations.TrainingPlanManagementScreen.getRouteWithTrainingPlanId(
                            trainingPlanId = 0
                        )
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.TrainingPlanManagementScreen.route,
            arguments = listOf(
                navArgument(name = TrainingPlanDestinations.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.trainingPlanId)
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
                        route = TrainingPlanDestinations.TrainingPlanListScreen.route,
                        inclusive = false
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.TrainingsListScreen.route,
            arguments = listOf(
                navArgument(name = TrainingPlanDestinations.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.trainingPlanId)
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
                        route = TrainingDestinations.SetsListScreen.getRouteWithParameters(
                            trainingId = it,
                            trainingPlanId = trainingPlanId
                        )
                    )
                },
                onCreateTraining = {
                    navController.navigate(
                        route = TrainingDestinations.TrainingManagementScreen.getRouteWithTrainingId(
                            trainingId = 0,
                            trainingPlanId = trainingPlanId
                        )
                    )
                },
                onOpenSettings = {
                    navController.navigate(
                        route = TrainingPlanDestinations.TrainingPlanManagementScreen.getRouteWithTrainingPlanId(
                            trainingPlanId = trainingPlanId
                        )
                    )
                }
            )
        }



        composableWithTransitionAnimation(
            route = TrainingDestinations.TrainingManagementScreen.route,
            arguments = listOf(
                navArgument(name = TrainingDestinations.trainingId) {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument(name = TrainingDestinations.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(TrainingDestinations.trainingId)
                    .orZero()
            }
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingDestinations.trainingPlanId)
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
                        route = TrainingPlanDestinations.TrainingPlanListScreen.route,
                        inclusive = false
                    )
                }
            )
        }

        composableWithTransitionAnimation(
            route = TrainingDestinations.SetsListScreen.route,
            arguments = listOf(
                navArgument(name = TrainingDestinations.trainingId) {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument(name = TrainingDestinations.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(TrainingDestinations.trainingId)
                    .orZero()
            }
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingDestinations.trainingPlanId)
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
                        route = TrainingDestinations.TrainingManagementScreen.getRouteWithTrainingId(
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