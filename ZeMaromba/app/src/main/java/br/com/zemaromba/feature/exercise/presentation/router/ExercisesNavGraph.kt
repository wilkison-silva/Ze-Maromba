package br.com.zemaromba.feature.exercise.presentation.router

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.zemaromba.feature.exercise.presentation.screen.ExerciseManagementScreen
import br.com.zemaromba.feature.exercise.presentation.screen.ExercisesListScreen
import br.com.zemaromba.feature.exercise.presentation.screen.MuscleGroupCheckBox
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
                    navController.navigate(ExerciseRouter.ExerciseManagementScreen.route)
                }
            )
        }
        composable(
            route = ExerciseRouter.ExerciseManagementScreen.route
        ) {
            val name = remember {
                mutableStateOf("")
            }
            val muscleGroups = remember {
                mutableStateOf(
                    listOf(
                        MuscleGroupCheckBox(
                            name = "Peitoral",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Dorsal",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Trapézio",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Bíceps",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Antebraço",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Quadríceps",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Abdomen",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Posteriores",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Adultores",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Abdutores",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Glúteos",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Panturrilhas",
                            isSelected = false
                        ),
                        MuscleGroupCheckBox(
                            name = "Lombar",
                            isSelected = false
                        )
                    )
                )
            }
            ExerciseManagementScreen(
                name = name.value,
                muscleGroups = muscleGroups.value,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onChangeName = {
                    name.value = it
                },
                onMuscleGroupSelection = { id, isSelected ->
                    muscleGroups.value =
                        muscleGroups.value
                            .toMutableList()
                            .apply {
                                this[id] = this[id].copy(isSelected = isSelected)
                            }
                }
            )
        }
    }
}