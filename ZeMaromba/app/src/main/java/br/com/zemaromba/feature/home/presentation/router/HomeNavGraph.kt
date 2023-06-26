package br.com.zemaromba.feature.home.presentation.router

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.zemaromba.feature.home.presentation.model.MenuHome
import br.com.zemaromba.feature.home.presentation.screen.HomeScreen
import br.com.zemaromba.feature.home.presentation.viewmodel.HomeScreenViewModel

fun NavGraphBuilder.homeGraph(
    navigateTo: (menu: MenuHome) -> Unit
) {
    navigation(
        startDestination = HomeRouter.HomeScreen.route,
        route = HomeRouter.HomeGraph.route
    ) {
        composable(
            route = HomeRouter.HomeScreen.route
        ) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            HomeScreen(
                state = state,
                onNavigate = {
                    navigateTo(it)
                }
            )
        }
    }
}