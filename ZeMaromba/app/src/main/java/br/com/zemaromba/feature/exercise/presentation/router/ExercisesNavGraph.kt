package br.com.zemaromba.feature.exercise.presentation.router

import android.util.Log
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
import br.com.zemaromba.feature.exercise.presentation.screen.ExerciseManagementScreen
import br.com.zemaromba.feature.exercise.presentation.screen.ExercisesListScreen
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExerciseManagementEvents
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExerciseManagementViewModel
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
                    navController.navigate(route = "exercise_management/0")
                },
                onOpenExercise = { exerciseId ->
                    navController.navigate(route = "exercise_management/$exerciseId")
                }
            )
        }
        composable(
            route = ExerciseRouter.ExerciseManagementScreen.route+"/{exercise_id}",
            arguments = listOf(
                navArgument(name = "exercise_id") {
                    type = NavType.LongType
                    defaultValue = 0
                }
            )
        ) {
            val exerciseId = remember {
                it.arguments?.getLong("exercise_id") ?: 0
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
                onChangeName = {
                    viewModel.onEvent(event = ExerciseManagementEvents.OnEnterName(exerciseName = it))
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
                }
            )
        }
    }
}