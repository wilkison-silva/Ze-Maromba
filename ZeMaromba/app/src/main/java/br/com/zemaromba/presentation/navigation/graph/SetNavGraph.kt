package br.com.zemaromba.presentation.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import br.com.zemaromba.presentation.sets.viewmodel.CreateSetViewModel

fun NavGraphBuilder.setGraph(
    navController: NavController
) {
    navigation(
        startDestination = SetCreationRouter.SelectExercise.route,
        route = SetCreationRouter.SetCreationGraph.route,
        arguments = listOf(
            navArgument(name = "training_id") {
                type = NavType.LongType
            }
        )
    ) {
        composableWithTransitionAnimation(
            route = SetCreationRouter.SelectExercise.route
        ) {
            val viewModel = it.sharedViewModel<CreateSetViewModel>(navController = navController)
            val state = viewModel.state.collectAsStateWithLifecycle().value
            val trainingId = remember {
                it
                    .arguments
                    ?.getLong(SetCreationRouter.trainingId)
                    .orZero()
            }
            LaunchedEffect(key1 = Unit) {
                viewModel.setTrainingId(value = trainingId)
            }
            SelectExerciseScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateForward = {
                    viewModel.updateProgressBar(
                        initialProgress = 0.33f,
                        targetProgress = 0.66f
                    )
                    navController.navigate(SetCreationRouter.ExerciseDetails.route)
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
            val viewModel = it.sharedViewModel<CreateSetViewModel>(navController = navController)
            val state = viewModel.state.collectAsStateWithLifecycle().value
            ExerciseDetailsScreen(
                state = state,
                onNavigateBack = {
                    viewModel.updateProgressBar(
                        initialProgress = 0.66f,
                        targetProgress = 0.33f
                    )
                    navController.popBackStack()
                },
                onNavigateForward = {
                    viewModel.updateProgressBar(
                        initialProgress = 0.66f,
                        targetProgress = 1.0f
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
            val viewModel = it.sharedViewModel<CreateSetViewModel>(navController = navController)
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
                onNavigateBack = {
                    viewModel.updateProgressBar(
                        initialProgress = 1f,
                        targetProgress = 0.66f
                    )
                    navController.popBackStack()
                },
                onChangeObservation = { observation ->
                    viewModel.updateObservationValue(value = observation)
                },
                onFinishCreation = {
                    viewModel.createSet()
                }
            )
        }
    }
}