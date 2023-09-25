package br.com.zemaromba.presentation.features.training_plan.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.addRouterParameters
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.presentation.core_ui.navigation.BaseRouter
import br.com.zemaromba.presentation.features.training_plan.screen.SetsListScreen
import br.com.zemaromba.presentation.features.training_plan.screen.TrainingListScreen
import br.com.zemaromba.presentation.features.training_plan.screen.TrainingManagementScreen
import br.com.zemaromba.presentation.features.training_plan.screen.TrainingPlanListScreen
import br.com.zemaromba.presentation.features.training_plan.screen.TrainingPlanManagementScreen
import br.com.zemaromba.presentation.features.training_plan.screen.event.TrainingManagementEvents
import br.com.zemaromba.presentation.features.training_plan.screen.event.TrainingPlanManagementEvents
import br.com.zemaromba.presentation.features.training_plan.viewmodel.SetListViewModel
import br.com.zemaromba.presentation.features.training_plan.viewmodel.TrainingListViewModel
import br.com.zemaromba.presentation.features.training_plan.viewmodel.TrainingManagementViewModel
import br.com.zemaromba.presentation.features.training_plan.viewmodel.TrainingPlanListViewModel
import br.com.zemaromba.presentation.features.training_plan.viewmodel.TrainingPlanManagementViewModel

private object TrainingPlanDestinations {

    private const val baseGraphRoute = "training_plan"

    object Parameters {
        const val trainingPlanId = "training_plan_id"
        const val trainingId = "training_id"
    }

    sealed class Router : BaseRouter() {

        data object TrainingPlanGraph : Router() {
            override val routePattern: String
                get() = baseGraphRoute

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object TrainingPlanListScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/list"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object TrainingPlanManagementScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/management/{${Parameters.trainingPlanId}}"

            override fun buildRoute(vararg args: String): String {
                return "$baseGraphRoute/management".addRouterParameters(*args)
            }
        }

        data object TrainingsListScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/trainings_list/{${Parameters.trainingPlanId}}"

            override fun buildRoute(vararg args: String): String {
                return "$baseGraphRoute/trainings_list".addRouterParameters(*args)
            }
        }

        data object TrainingManagementScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/training/management/{${Parameters.trainingId}}/{${Parameters.trainingPlanId}}"

            override fun buildRoute(vararg args: String): String {
                return "$baseGraphRoute/training/management".addRouterParameters(*args)
            }
        }

        data object SetsListScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/training/sets_list/{${Parameters.trainingId}}/{${Parameters.trainingPlanId}}"

            override fun buildRoute(vararg args: String): String {
                return "$baseGraphRoute/training/sets_list".addRouterParameters(*args)
            }
        }

    }
}

fun NavController.navigateToTrainingPlanGraph() {
    this.navigate(
        route = TrainingPlanDestinations
            .Router.TrainingPlanGraph
            .routePattern
    )
}

fun NavGraphBuilder.addTrainingPlanGraph(
    navController: NavController,
    openYoutube: (urlLink: String) -> Unit,
    onCreateNewSet: (trainingId: Long, setId: Long) -> Unit
) {
    navigation(
        route = TrainingPlanDestinations.Router.TrainingPlanGraph.routePattern,
        startDestination = TrainingPlanDestinations.Router.TrainingPlanListScreen.routePattern,
    ) {
        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.Router.TrainingPlanListScreen.routePattern
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
                        route = TrainingPlanDestinations
                            .Router
                            .TrainingsListScreen
                            .buildRoute(trainingPlanId.toString())
                    )
                },
                onCreateTrainingPlan = {
                    navController.navigate(
                        route = TrainingPlanDestinations
                            .Router
                            .TrainingPlanManagementScreen
                            .buildRoute("0")
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.Router.TrainingPlanManagementScreen.routePattern,
            arguments = listOf(
                navArgument(name = TrainingPlanDestinations.Parameters.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.Parameters.trainingPlanId)
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
                        route = TrainingPlanDestinations.Router.TrainingPlanListScreen.routePattern,
                        inclusive = false
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.Router.TrainingsListScreen.routePattern,
            arguments = listOf(
                navArgument(name = TrainingPlanDestinations.Parameters.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.Parameters.trainingPlanId)
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
                        route = TrainingPlanDestinations
                            .Router
                            .SetsListScreen
                            .buildRoute(
                                it.toString(),
                                trainingPlanId.toString()
                            )
                    )
                },
                onCreateTraining = {
                    navController.navigate(
                        route = TrainingPlanDestinations
                            .Router
                            .TrainingManagementScreen
                            .buildRoute(
                                "0",
                                trainingPlanId.toString()
                            )
                    )
                },
                onOpenSettings = {
                    navController.navigate(
                        route = TrainingPlanDestinations
                            .Router
                            .TrainingPlanManagementScreen
                            .buildRoute(trainingPlanId.toString())
                    )
                }
            )
        }



        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.Router.TrainingManagementScreen.routePattern,
            arguments = listOf(
                navArgument(name = TrainingPlanDestinations.Parameters.trainingId) {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument(name = TrainingPlanDestinations.Parameters.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.Parameters.trainingId)
                    .orZero()
            }
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.Parameters.trainingPlanId)
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
                        route = TrainingPlanDestinations.Router.TrainingPlanListScreen.routePattern,
                        inclusive = false
                    )
                }
            )
        }

        composableWithTransitionAnimation(
            route = TrainingPlanDestinations.Router.SetsListScreen.routePattern,
            arguments = listOf(
                navArgument(name = TrainingPlanDestinations.Parameters.trainingId) {
                    type = NavType.LongType
                    defaultValue = 0
                },
                navArgument(name = TrainingPlanDestinations.Parameters.trainingPlanId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.Parameters.trainingId)
                    .orZero()
            }
            val trainingPlanId = remember {
                it
                    .arguments
                    ?.getLong(TrainingPlanDestinations.Parameters.trainingPlanId)
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
                        route = TrainingPlanDestinations
                            .Router
                            .TrainingManagementScreen
                            .buildRoute(
                                trainingId.toString(),
                                trainingPlanId.toString()
                            )
                    )
                },
                onOpenYoutubeApp = { urlLink ->
                    openYoutube(urlLink)
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