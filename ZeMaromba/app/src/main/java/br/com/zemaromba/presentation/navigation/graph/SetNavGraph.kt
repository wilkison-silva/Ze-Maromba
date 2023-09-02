package br.com.zemaromba.presentation.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.common.extensions.sharedViewModel
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.navigation.router.SetCreationRouter
import br.com.zemaromba.presentation.navigation.router.TrainingRouter
import br.com.zemaromba.presentation.sets.screen.ExerciseDetailsScreen
import br.com.zemaromba.presentation.sets.screen.ExerciseObservationScreen
import br.com.zemaromba.presentation.sets.screen.SelectExerciseScreen
import br.com.zemaromba.presentation.sets.viewmodel.CreateSetFlowViewModel
import br.com.zemaromba.presentation.sets.viewmodel.ExerciseDetailsViewModel
import br.com.zemaromba.presentation.sets.viewmodel.ExerciseObservationViewModel
import br.com.zemaromba.presentation.sets.viewmodel.SelectExerciseViewModel

fun NavGraphBuilder.setGraph(
    navController: NavController
) {
    navigation(
        startDestination = SetCreationRouter.SelectExercise.route,
        route = SetCreationRouter.SetCreationGraph.route,
        arguments = listOf(
            navArgument(name = SetCreationRouter.trainingId) {
                type = NavType.LongType
            },
            navArgument(name = SetCreationRouter.setId) {
                type = NavType.LongType
            }
        )
    ) {
        composableWithTransitionAnimation(
            route = SetCreationRouter.SelectExercise.route
        ) {
            val flowViewModel = it.sharedViewModel<CreateSetFlowViewModel>(navController = navController)
            val flowState = flowViewModel.state.collectAsStateWithLifecycle().value
            val viewModel: SelectExerciseViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(SetCreationRouter.trainingId)
                    .orZero()
            }
            val setId = remember {
                it
                    .arguments
                    ?.getLong(SetCreationRouter.setId)
                    .orZero()
            }
            LaunchedEffect(key1 = Unit) {
                flowViewModel.retrieveSet(setId)
            }
            LaunchedEffect(key1 = flowState.isExerciseRetrieved) {
                if (flowState.isExerciseRetrieved) {
                    flowViewModel.state.value.selectedExercise?.let { selectedExercise ->
                        viewModel.onEvent(
                            event = ExercisesListEvents.OnSelectExercise(id = selectedExercise.id)
                        )
                        flowViewModel.updateFlowData(
                            selectedExercise = selectedExercise,
                            trainingId = trainingId
                        )
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
                        navController.navigate(SetCreationRouter.ExerciseDetails.route)
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
            route = SetCreationRouter.ExerciseDetails.route
        ) {
            val flowViewModel = it.sharedViewModel<CreateSetFlowViewModel>(navController = navController)
            val flowState = flowViewModel.state.collectAsStateWithLifecycle().value
            val viewModel: ExerciseDetailsViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
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
                    navController.navigate(SetCreationRouter.ExerciseObservation.route)
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
            route = SetCreationRouter.ExerciseObservation.route
        ) {
            val flowViewModel = it.sharedViewModel<CreateSetFlowViewModel>(navController = navController)
            val flowState = flowViewModel.state.collectAsStateWithLifecycle().value
            val viewModel: ExerciseObservationViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = state.navigateBack) {
                if (state.navigateBack) {
                    navController.popBackStack(
                        route = TrainingRouter.SetsListScreen.route,
                        inclusive = false
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