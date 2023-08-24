package br.com.zemaromba.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListEvents
import br.com.zemaromba.presentation.navigation.router.SetRouter
import br.com.zemaromba.presentation.sets.screen.SetFlowScreen
import br.com.zemaromba.presentation.sets.viewmodel.SetFlowViewModel

fun NavGraphBuilder.setGraph(
    navController: NavController,
    width: Int
) {
    navigation(
        startDestination = SetRouter.SetCreationScreen.route,
        route = SetRouter.SetGraph.route
    ) {
        composableWithTransitionAnimation(
            route = SetRouter.SetCreationScreen.route,
            width = width
        ) {
            val viewModel: SetFlowViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            SetFlowScreen(
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