package br.com.zemaromba.presentation.features.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.core_ui.navigation.BaseRouter
import br.com.zemaromba.presentation.core_ui.navigation.PopUpToDestination
import br.com.zemaromba.presentation.features.exercises.navigation.navigateToExerciseGraph
import br.com.zemaromba.presentation.features.home.screen.HomeScreen
import br.com.zemaromba.presentation.features.home.viewmodel.HomeScreenViewModel
import br.com.zemaromba.presentation.model.MenuHome
import br.com.zemaromba.presentation.navigation.destinations.TrainingPlanDestinations
import br.com.zemaromba.presentation.features.user_configurations.navigation.navigateToUserConfigurationsGraph

private object HomeDestinations {

    private const val baseGraphRoute = "home"

    sealed class Router : BaseRouter() {

        data object HomeGraph : Router() {

            override val routePattern: String
                get() = baseGraphRoute

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object HomeScreen : Router() {

            override val routePattern: String
                get() = "$baseGraphRoute/home_menu"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

    }

}

fun NavController.navigateToHomeGraph(popUpToDestination: PopUpToDestination? = null) {
    if (popUpToDestination == null) {
        this.navigate(route = HomeDestinations.Router.HomeGraph.buildRoute())
    } else {
        this.navigate(route = HomeDestinations.Router.HomeGraph.buildRoute()) {
            popUpTo(popUpToDestination.route) {
                inclusive = popUpToDestination.inclusive
            }
        }
    }
}

fun NavGraphBuilder.addHomeGraph(
    navController: NavController
) {
    navigation(
        route = HomeDestinations.Router.HomeGraph.routePattern,
        startDestination = HomeDestinations.Router.HomeScreen.routePattern,
    ) {
        composableWithTransitionAnimation(
            route = HomeDestinations.Router.HomeScreen.routePattern,
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
                            navController.navigateToExerciseGraph()
                        }

                        MenuHome.USER_CONFIGURATIONS -> {
                            navController.navigateToUserConfigurationsGraph()
                        }
                    }
                }
            )
        }
    }
}