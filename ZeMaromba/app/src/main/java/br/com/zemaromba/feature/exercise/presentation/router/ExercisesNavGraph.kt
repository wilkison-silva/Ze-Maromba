package br.com.zemaromba.feature.exercise.presentation.router

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.feature.exercise.presentation.screen.ExerciseManagementScreen
import br.com.zemaromba.feature.exercise.presentation.screen.ExercisesListScreen
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExerciseManagementEvents
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExerciseManagementViewModel
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExercisesListEvents
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExercisesListViewModel

fun NavGraphBuilder.exerciseGraph(
    navController: NavController,
) {
    navigation(
        startDestination = ExerciseRouter.ExercisesListScreen.route,
        route = ExerciseRouter.ExerciseGraph.route
    ) {
        composable(
            route = ExerciseRouter.ExercisesListScreen.route
        ) {
            val viewModel: ExercisesListViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            ExercisesListScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNewExercise = {
                    navController.navigate(
                        route = ExerciseRouter
                            .ExerciseManagementScreen
                            .getRouteWithUserName(exerciseId = 0)
                    )
                },
                onOpenExercise = { exerciseId ->
                    navController.navigate(
                        route = ExerciseRouter
                            .ExerciseManagementScreen
                            .getRouteWithUserName(exerciseId = exerciseId)
                    )
                },
                onFavoriteExercise = { exerciseId, favoriteIcon ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnFavoriteExercise(
                            exerciseId = exerciseId,
                            favoriteIcon = favoriteIcon
                        )
                    )
                },
                onSearch = { exerciseName ->
                    viewModel.onEvent(
                        event = ExercisesListEvents.OnSearchExercise(
                            exerciseName = exerciseName
                        )
                    )
                },
                onFilterChange = { chipIndex ->
                    viewModel.onEvent(event = ExercisesListEvents.OnFilterChange(chipIndex = chipIndex))
                }
            )
        }
        composable(
            route = ExerciseRouter.ExerciseManagementScreen.route,
            arguments = listOf(
                navArgument(name = ExerciseRouter.ExerciseManagementScreen.Params.exerciseId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val exerciseId = remember {
                it
                    .arguments
                    ?.getLong(ExerciseRouter.ExerciseManagementScreen.Params.exerciseId)
                    .orZero()
            }
            val viewModel: ExerciseManagementViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = Unit) {
                viewModel.retrieveExercise(exerciseId)
            }
            ExerciseManagementScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onChangeName = { newName ->
                    viewModel
                        .onEvent(event = ExerciseManagementEvents.OnEnterName(exerciseName = newName))
                },
                onSaveExercise = {
                    viewModel.onEvent(event = ExerciseManagementEvents.OnSaveExercise)
                },
                onDeleteExercise = {
                    viewModel.onEvent(ExerciseManagementEvents.OnDeleteExercise)
                },
                onMuscleGroupSelection = { id, isSelected ->
                    viewModel.onEvent(
                        event = ExerciseManagementEvents.OnMuscleGroupSelection(
                            id = id,
                            isSelected = isSelected
                        )
                    )
                },
                onShowAlertAboutRemoving = { showDialog ->
                    viewModel.onEvent(
                        event = ExerciseManagementEvents
                            .OnShowWarningAboutRemoving(showDialog = showDialog)
                    )
                }
            )
        }
    }
}