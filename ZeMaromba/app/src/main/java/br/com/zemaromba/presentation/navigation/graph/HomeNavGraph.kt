package br.com.zemaromba.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.navigation.router.HomeRouter
import br.com.zemaromba.presentation.model.MenuHome
import br.com.zemaromba.presentation.home.screen.HomeScreen
import br.com.zemaromba.presentation.home.screen.UserManagementScreen
import br.com.zemaromba.presentation.home.viewmodel.HomeScreenViewModel
import br.com.zemaromba.presentation.home.viewmodel.UserManagementViewModel
import br.com.zemaromba.presentation.navigation.router.ExerciseRouter
import br.com.zemaromba.presentation.navigation.router.TrainingPlanRouter

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation(
        startDestination = HomeRouter.HomeScreen.route,
        route = HomeRouter.HomeGraph.route
    ) {
        composableWithTransitionAnimation(
            route = HomeRouter.HomeScreen.route,
        ) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value

            HomeScreen(
                state = state,
                onNavigate = {
                    when (it) {
                        MenuHome.TRAINING_PLAN_SCREEN -> {
                            navController.navigate(
                                route = TrainingPlanRouter.TrainingPlanGraph.route
                            )
                        }

                        MenuHome.EXERCISES_SCREEN -> {
                            navController.navigate(ExerciseRouter.ExerciseGraph.route)
                        }
                    }
                },
                onIconAccountSettingClick = {
                    navController.navigate(HomeRouter.UserManagementScreen.route)
                }
            )
        }
        composableWithTransitionAnimation(
            route = HomeRouter.UserManagementScreen.route,
        ) {
            val viewModel: UserManagementViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value

            UserManagementScreen(
                state = state,
                onChangeName = {
                    viewModel.onEnterNewName(name = it)
                },
                onSaveName = {
                    viewModel.onSaveName()
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}