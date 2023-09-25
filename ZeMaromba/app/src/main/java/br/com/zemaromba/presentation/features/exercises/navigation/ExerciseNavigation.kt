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
import br.com.zemaromba.common.extensions.addRouterParameters
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.presentation.core_ui.navigation.BaseRouter
import br.com.zemaromba.presentation.features.exercises.screen.ExerciseManagementScreen
import br.com.zemaromba.presentation.features.exercises.screen.ExercisesListScreen
import br.com.zemaromba.presentation.features.exercises.screen.event.ExerciseManagementEvents
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExerciseManagementViewModel
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.features.exercises.viewmodel.ExercisesListViewModel

private object ExerciseDestinations {

    private const val baseGraphRoute = "exercises"

    object Parameters {
        const val exerciseId = "exercise_id"
    }

    sealed class Router : BaseRouter() {

        data object ExerciseGraph : Router() {

            override val routePattern: String
                get() = baseGraphRoute

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ExercisesListScreen : Router() {

            override val routePattern: String
                get() = "$baseGraphRoute/exercises_list"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ExerciseManagementScreen : Router() {

            override val routePattern: String
                get() = "$baseGraphRoute/exercise_management/{${Parameters.exerciseId}}"

            override fun buildRoute(vararg args: String): String {
                return "$baseGraphRoute/exercise_management".addRouterParameters(*args)
            }
        }
    }

}

fun NavController.navigateToExerciseGraph() {
    this.navigate(route = ExerciseDestinations.Router.ExerciseGraph.buildRoute())
}

fun NavGraphBuilder.addExerciseGraph(
    navController: NavController,
    openYoutube: (videoId: String) -> Unit,
) {
    navigation(
        route = ExerciseDestinations.Router.ExerciseGraph.routePattern,
        startDestination = ExerciseDestinations.Router.ExercisesListScreen.routePattern,
    ) {
        composableWithTransitionAnimation(
            route = ExerciseDestinations.Router.ExercisesListScreen.routePattern
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
                        route = ExerciseDestinations
                            .Router
                            .ExerciseManagementScreen
                            .buildRoute("0")
                    )
                },
                onOpenExercise = { exerciseId ->
                    navController.navigate(
                        route = ExerciseDestinations
                            .Router
                            .ExerciseManagementScreen
                            .buildRoute(exerciseId.toString())
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
            route = ExerciseDestinations.Router.ExerciseManagementScreen.routePattern,
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