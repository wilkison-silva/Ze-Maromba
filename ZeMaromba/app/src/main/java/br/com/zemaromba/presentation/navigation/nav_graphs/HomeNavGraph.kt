package br.com.zemaromba.presentation.navigation.nav_graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.home.screen.HomeScreen
import br.com.zemaromba.presentation.home.viewmodel.HomeScreenViewModel
import br.com.zemaromba.presentation.model.MenuHome
import br.com.zemaromba.presentation.navigation.destinations.ExerciseDestinations
import br.com.zemaromba.presentation.navigation.destinations.HomeDestinations
import br.com.zemaromba.presentation.navigation.destinations.TrainingPlanDestinations
import br.com.zemaromba.presentation.navigation.destinations.UserConfigurationsDestinations

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(
        startDestination = HomeDestinations.HomeScreen.route,
        route = HomeDestinations.HomeGraph.route
    ) {
        composableWithTransitionAnimation(
            route = HomeDestinations.HomeScreen.route,
        ) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value

            HomeScreen(
                state = state,
                onNavigate = {
                    when (it) {
                        MenuHome.TRAINING_PLAN_SCREEN -> {
                            navController.navigate(
                                route = TrainingPlanDestinations.TrainingPlanGraph.route
                            )
                        }

                        MenuHome.EXERCISES_SCREEN -> {
                            navController.navigate(ExerciseDestinations.ExerciseGraph.route)
                        }

                        MenuHome.USER_CONFIGURATIONS -> {
                            navController.navigate(
                                route = UserConfigurationsDestinations.UserConfigurationsGraph.route
                            )
                        }
                    }
                }
            )
        }
    }
}