package br.com.zemaromba.presentation.navigation.graph

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.common.extensions.sharedViewModel
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.navigation.router.SetCreationRouter
import br.com.zemaromba.presentation.sets.screen.SelectExerciseScreen
import br.com.zemaromba.presentation.sets.viewmodel.CreateSetViewModel

fun NavGraphBuilder.setGraph(
    navController: NavController
) {
    navigation(
        startDestination = SetCreationRouter.SelectExercise.route,
        route = SetCreationRouter.SetCreationGraph.route
    ) {
        composableWithTransitionAnimation(
            route = SetCreationRouter.SelectExercise.route
        ) {
            val viewModel = it.sharedViewModel<CreateSetViewModel>(navController = navController)
            val state = viewModel.state.collectAsStateWithLifecycle().value
            SelectExerciseScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
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
    }
}