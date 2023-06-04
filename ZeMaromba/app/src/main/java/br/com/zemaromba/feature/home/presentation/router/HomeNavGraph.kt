package br.com.zemaromba.feature.home.presentation.router

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.zemaromba.feature.home.presentation.screen.HomeScreen

fun NavGraphBuilder.homeGraph(
    navController: NavController,
) {
    navigation(
        startDestination = HomeRouter.HomeScreen.route,
        route = HomeRouter.HomeGraph.route
    ) {
        composable(
            route = HomeRouter.HomeScreen.route
        ) {
            val userName = remember {
                navController
                    .getBackStackEntry(route = HomeRouter.HomeGraph.route)
                    .arguments
                    ?.getString(HomeRouter.HomeGraph.Params.userName)
                    .orEmpty()
            }
            HomeScreen(userName = userName)
        }
    }
}