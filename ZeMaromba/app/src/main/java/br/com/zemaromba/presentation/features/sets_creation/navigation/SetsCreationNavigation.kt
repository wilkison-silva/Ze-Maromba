package br.com.zemaromba.presentation.features.sets_creation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import br.com.zemaromba.common.extensions.addRouterParameters
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.common.extensions.sharedViewModel
import br.com.zemaromba.presentation.core_ui.navigation.BaseRouter
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.features.sets_creation.screen.ExerciseDetailsScreen
import br.com.zemaromba.presentation.features.sets_creation.screen.ExerciseObservationScreen
import br.com.zemaromba.presentation.features.sets_creation.screen.SelectExerciseScreen
import br.com.zemaromba.presentation.features.sets_creation.viewmodel.CreateSetFlowViewModel
import br.com.zemaromba.presentation.features.sets_creation.viewmodel.ExerciseDetailsViewModel
import br.com.zemaromba.presentation.features.sets_creation.viewmodel.ExerciseObservationViewModel
import br.com.zemaromba.presentation.features.sets_creation.viewmodel.SelectExerciseViewModel

private object SetsCreationNavigation {

    private const val baseGraphRoute = "sets"

    object Parameters {
        const val trainingId = "training_id"
        const val setId = "set_id"
    }

    sealed class Router : BaseRouter() {
        data object SetCreationGraph : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/{${Parameters.trainingId}}/{${Parameters.setId}}"

            override fun buildRoute(vararg args: String): String {
                return baseGraphRoute.addRouterParameters(*args)
            }
        }

        data object SelectExercise : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/set_creation"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ExerciseDetails : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/exercise_details"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ExerciseObservation : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/exercise_observation"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }
    }
}

fun NavController.navigateToSetsCreationGraph(trainingId: Long, setId: Long) {
    this.navigate(
        route = SetsCreationNavigation
            .Router
            .SetCreationGraph
            .buildRoute(
                trainingId.toString(),
                setId.toString()
            )
    )
}

fun NavGraphBuilder.addSetsCreationGraph(
    navController: NavController
) {
    navigation(
        route = SetsCreationNavigation.Router.SetCreationGraph.routePattern,
        startDestination = SetsCreationNavigation.Router.SelectExercise.routePattern,
        arguments = listOf(
            navArgument(name = SetsCreationNavigation.Parameters.trainingId) {
                type = NavType.LongType
            },
            navArgument(name = SetsCreationNavigation.Parameters.setId) {
                type = NavType.LongType
            }
        )
    ) {
        composableWithTransitionAnimation(
            route = SetsCreationNavigation.Router.SelectExercise.routePattern
        ) {
            val flowViewModel =
                it.sharedViewModel<CreateSetFlowViewModel>(navController = navController)
            val flowState = flowViewModel.state.collectAsStateWithLifecycle().value
            val viewModel: SelectExerciseViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(SetsCreationNavigation.Parameters.trainingId)
                    .orZero()
            }
            val setId = remember {
                it
                    .arguments
                    ?.getLong(SetsCreationNavigation.Parameters.setId)
                    .orZero()
            }
            LaunchedEffect(key1 = Unit) {
                flowViewModel.retrieveSet(setId)
            }
            LaunchedEffect(key1 = flowState.isExerciseRetrieved) {
                if (flowState.mustRetrieveExercise) {
                    flowViewModel.state.value.selectedExercise?.let { selectedExercise ->
                        viewModel.onEvent(
                            event = ExercisesListEvents.OnSelectExercise(id = selectedExercise.id)
                        )
                        flowViewModel.updateFlowData(
                            selectedExercise = selectedExercise,
                            trainingId = trainingId
                        )
                        flowViewModel.updateMustRetrieveExercise(value = false)
                        viewModel.updateScrollPosition()
                    }
                }
            }
            SelectExerciseScreen(
                state = state,
                flowState = flowState,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateForward = {
                    viewModel.state.value.selectedExercise?.let { selectedExercise ->
                        flowViewModel.updateProgressBar(
                            initialProgress = 0.33f,
                            targetProgress = 0.66f
                        )
                        flowViewModel.updateFlowData(
                            selectedExercise = selectedExercise,
                            trainingId = trainingId
                        )
                        flowViewModel.updateMustRetrieveExercise(value = false)
                        navController.navigate(SetsCreationNavigation.Router.ExerciseDetails.routePattern)
                    }
                },
                onExerciseSelected = { exerciseId ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnSelectExercise(id = exerciseId)
                    )
                },
                onSearch = { exerciseName ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnSearchExercise(
                            exerciseName = exerciseName
                        )
                    )
                },
                onFilterChange = { exerciseFilter ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnFilterChange(exerciseFilter = exerciseFilter)
                    )
                },
                onApplySelectedMuscleGroups = {
                    viewModel.onEvent(event = ExercisesListEvents.OnFilterBySelectedMuscleGroups)
                },
                onMuscleGroupSelection = { index, isSelected ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnMuscleGroupSelection(
                            id = index,
                            isSelected = isSelected
                        )
                    )
                }
            )
        }
        composableWithTransitionAnimation(
            route = SetsCreationNavigation.Router.ExerciseDetails.routePattern
        ) {
            val flowViewModel =
                it.sharedViewModel<CreateSetFlowViewModel>(navController = navController)
            val flowState = flowViewModel.state.collectAsStateWithLifecycle().value
            val viewModel: ExerciseDetailsViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = flowState.isExerciseRetrieved) {
                if (flowState.isExerciseRetrieved) {
                    viewModel.updateSeriesValue(value = flowState.seriesValue)
                    viewModel.updateRepetitionsValue(value = flowState.repetitionsValue)
                    viewModel.updateWeightValue(value = flowState.weightValue)
                    viewModel.updateRestingTimeValue(value = flowState.restingTimeValue)
                }
            }
            ExerciseDetailsScreen(
                state = state,
                flowState = flowState,
                onNavigateBack = {
                    flowViewModel.updateProgressBar(
                        initialProgress = 0.66f,
                        targetProgress = 0.33f
                    )
                    navController.popBackStack()
                },
                onNavigateForward = {
                    flowViewModel.updateProgressBar(
                        initialProgress = 0.66f,
                        targetProgress = 1.0f
                    )
                    flowViewModel.updateFlowData(
                        seriesValue = viewModel.state.value.seriesValue,
                        repetitionsValue = viewModel.state.value.repetitionsValue,
                        weightValue = viewModel.state.value.weightValue,
                        restingTimeValue = viewModel.state.value.restingTimeValue
                    )
                    navController.navigate(SetsCreationNavigation.Router.ExerciseObservation.routePattern)
                },
                onChangeSeries = { seriesValue ->
                    viewModel.updateSeriesValue(value = seriesValue)
                },
                onChangeRepetition = { repetitionsValue ->
                    viewModel.updateRepetitionsValue(value = repetitionsValue)
                },
                onChangeWeight = { weight ->
                    viewModel.updateWeightValue(value = weight)
                },
                onChangeRestingTime = { restingTime ->
                    viewModel.updateRestingTimeValue(value = restingTime)
                },
                onFillDetailsLater = { fillDetailsLater ->
                    viewModel.updateFillDetailsLater(value = fillDetailsLater)
                }
            )
        }
        composableWithTransitionAnimation(
            route = SetsCreationNavigation.Router.ExerciseObservation.routePattern
        ) {
            val flowViewModel =
                it.sharedViewModel<CreateSetFlowViewModel>(navController = navController)
            val flowState = flowViewModel.state.collectAsStateWithLifecycle().value
            val viewModel: ExerciseObservationViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = flowState.isExerciseRetrieved) {
                if (flowState.isExerciseRetrieved) {
                    viewModel.updateObservationValue(value = flowState.observation)
                }
            }
            LaunchedEffect(key1 = state.navigateBack) {
                if (state.navigateBack) {
                    navController.popBackStack(
                        route = SetsCreationNavigation.Router.SetCreationGraph.routePattern,
                        inclusive = true
                    )
                }
            }
            ExerciseObservationScreen(
                state = state,
                flowState = flowState,
                onNavigateBack = {
                    flowViewModel.updateProgressBar(
                        initialProgress = 1f,
                        targetProgress = 0.66f
                    )
                    navController.popBackStack()
                },
                onChangeObservation = { observation ->
                    viewModel.updateObservationValue(value = observation)
                },
                onFinishCreation = {
                    flowState.selectedExercise?.let { selectedExercise ->
                        viewModel.createSet(
                            setId = flowState.setId,
                            selectedExercise = selectedExercise,
                            trainingId = flowState.trainingId,
                            series = flowState.seriesValue,
                            repetitions = flowState.repetitionsValue,
                            weight = flowState.weightValue,
                            restingTime = flowState.restingTimeValue
                        )
                    }
                }
            )
        }
    }
}