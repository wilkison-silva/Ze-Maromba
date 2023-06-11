package br.com.zemaromba.feature.exercise_origination.presentation.router

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.zemaromba.feature.exercise_origination.presentation.screen.ExercisesListscreen

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
            ExercisesListscreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToNewExercise = {

                }
            )
        }
    }
}