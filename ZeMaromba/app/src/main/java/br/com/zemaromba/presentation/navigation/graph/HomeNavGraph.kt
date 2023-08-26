package br.com.zemaromba.presentation.navigation.graph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.navigation.router.HomeRouter
import br.com.zemaromba.presentation.model.MenuHome
import br.com.zemaromba.presentation.home.screen.HomeScreen
import br.com.zemaromba.presentation.home.viewmodel.HomeScreenViewModel

fun NavGraphBuilder.homeGraph(
    navigateTo: (menu: MenuHome) -> Unit
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
                    navigateTo(it)
                }
            )
        }
    }
}