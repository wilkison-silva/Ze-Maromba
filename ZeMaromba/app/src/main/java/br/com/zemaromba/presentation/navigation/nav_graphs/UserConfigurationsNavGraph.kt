package br.com.zemaromba.presentation.navigation.nav_graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.navigation.destinations.HomeDestinations
import br.com.zemaromba.presentation.navigation.destinations.UserConfigurationsDestinations
import br.com.zemaromba.presentation.user_configurations.screen.ConfigurationListScreen

fun NavGraphBuilder.userConfigurationsGraph(
    navController: NavController
) {
    navigation(
        startDestination = UserConfigurationsDestinations.MenuConfigOptionsListScreen.route,
        route = UserConfigurationsDestinations.UserConfigurationsGraph.route
    ) {
        composableWithTransitionAnimation(
            route = UserConfigurationsDestinations.MenuConfigOptionsListScreen.route
        ) {
            ConfigurationListScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToUserAccountConfigs = {
                    navController.navigate(HomeDestinations.UserManagementScreen.route)
                },
                onNavigateToThemeConfigs = {

                },
                onNavigateToContacts = {

                }
            )
        }
    }
}