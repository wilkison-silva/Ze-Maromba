package br.com.zemaromba.presentation.features.exercises.navigation

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
import br.com.zemaromba.presentation.features.exercises.screen.ExerciseManagementScreen
import br.com.zemaromba.presentation.features.exercises.screen.ExercisesListScreen
import br.com.zemaromba.presentation.features.exercises.screen.event.ExerciseManagementEvents
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExerciseManagementViewModel
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExercisesListViewModel

private object ExerciseFeatureDestinations {

    private const val baseGraphRoute = "exercises"

    object Parameters {
        const val exerciseId = "exercise_id"
    }

    sealed class Routers(val route: String) {
        data object ExerciseGraph : Routers(route = baseGraphRoute)
        data object ExercisesListScreen : Routers(route = "$baseGraphRoute/exercises_list")
        data object ExerciseManagementScreen : Routers(
            route = "$baseGraphRoute/exercise_management/{${Parameters.exerciseId}}"
        ) {
            fun getRouteWithExerciseId(exerciseId: Long): String {
                return "$baseGraphRoute/exercise_management/$exerciseId"
            }
        }
    }

}

fun NavController.navigateToExerciseGraph() {
    this.navigate(ExerciseFeatureDestinations.Routers.ExerciseGraph.route)
}

fun NavGraphBuilder.exerciseGraph(
    navController: NavController,
    openYoutube: (videoId: String) -> Unit,
) {
    navigation(
        startDestination = ExerciseFeatureDestinations.Routers.ExercisesListScreen.route,
        route = ExerciseFeatureDestinations.Routers.ExerciseGraph.route
    ) {
        composableWithTransitionAnimation(
            route = ExerciseFeatureDestinations.Routers.ExercisesListScreen.route
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
                        route = ExerciseFeatureDestinations.Routers.ExerciseManagementScreen.getRouteWithExerciseId(
                            exerciseId = 0
                        )
                    )
                },
                onOpenExercise = { exerciseId ->
                    navController.navigate(
                        route = ExerciseFeatureDestinations.Routers.ExerciseManagementScreen.getRouteWithExerciseId(
                            exerciseId = exerciseId
                        )
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
                },
                onOpenYoutubeApp = { videoId ->
                    openYoutube(videoId)
                }
            )
        }
        composableWithTransitionAnimation(
            route = ExerciseFeatureDestinations.Routers.ExerciseManagementScreen.route,
            arguments = listOf(
                navArgument(name = ExerciseFeatureDestinations.Parameters.exerciseId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val exerciseId = remember {
                it
                    .arguments
                    ?.getLong(ExerciseFeatureDestinations.Parameters.exerciseId)
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
                onChangeUrlLink = {
                    viewModel.updateUrlLinkValue(url = it)
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