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

abstract class Router {
    abstract val routePattern: String
    abstract fun getFullRoute(vararg args: String): String
}

private object ExerciseDestinations {

    private const val baseGraphRoute = "exercises"

    object Parameters {
        const val exerciseId = "exercise_id"
    }

    sealed class Destinations : Router() {
        data object ExerciseGraph : Destinations() {

            override val routePattern: String
                get() = baseGraphRoute

            override fun getFullRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ExercisesListScreen : Destinations() {

            override val routePattern: String
                get() = "$baseGraphRoute/exercises_list"

            override fun getFullRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ExerciseManagementScreen : Destinations() {

            override val routePattern: String
                get() = "$baseGraphRoute/exercise_management/{${Parameters.exerciseId}}"

            override fun getFullRoute(vararg args: String): String {
                var finalRoute = "$baseGraphRoute/exercise_management"
                args.forEach { arg ->
                    finalRoute += "/$arg"
                }
                return finalRoute
            }
        }
    }

}

fun NavController.navigateToExerciseGraph() {
    this.navigate(route = ExerciseDestinations.Destinations.ExerciseGraph.getFullRoute())
}

fun NavGraphBuilder.exerciseGraph(
    navController: NavController,
    openYoutube: (videoId: String) -> Unit,
) {
    navigation(
        route = ExerciseDestinations.Destinations.ExerciseGraph.routePattern,
        startDestination = ExerciseDestinations.Destinations.ExercisesListScreen.routePattern,
    ) {
        composableWithTransitionAnimation(
            route = ExerciseDestinations.Destinations.ExercisesListScreen.routePattern
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
                        route = ExerciseDestinations.Destinations.ExerciseManagementScreen.getFullRoute("0")
                    )
                },
                onOpenExercise = { exerciseId ->
                    navController.navigate(
                        route = ExerciseDestinations.Destinations.ExerciseManagementScreen.getFullRoute(exerciseId.toString())
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
            route = ExerciseDestinations.Destinations.ExerciseManagementScreen.routePattern,
            arguments = listOf(
                navArgument(name = ExerciseDestinations.Parameters.exerciseId) {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val exerciseId = remember {
                it
                    .arguments
                    ?.getLong(ExerciseDestinations.Parameters.exerciseId)
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