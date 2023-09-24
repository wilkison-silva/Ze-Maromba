package br.com.zemaromba.presentation.navigation.nav_graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.features.user_configurations.screen.ConfigurationListScreen
import br.com.zemaromba.presentation.features.user_configurations.screen.ThemeSelectionScreen
import br.com.zemaromba.presentation.features.user_configurations.screen.UserManagementScreen
import br.com.zemaromba.presentation.features.user_configurations.viewmodel.ThemeSelectionViewModel
import br.com.zemaromba.presentation.features.user_configurations.viewmodel.UserManagementViewModel
import br.com.zemaromba.presentation.navigation.destinations.UserConfigurationsDestinations

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
                    navController.navigate(UserConfigurationsDestinations.UserManagementScreen.route)
                },
                onNavigateToThemeConfigs = {
                    navController.navigate(UserConfigurationsDestinations.ThemeConfigurationListScreen.route)
                },
                onNavigateToContacts = {

                }
            )
        }

        composableWithTransitionAnimation(
            route = UserConfigurationsDestinations.UserManagementScreen.route,
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

        composableWithTransitionAnimation(
            route = UserConfigurationsDestinations.ThemeConfigurationListScreen.route,
        ) {
            val viewModel: ThemeSelectionViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value

            ThemeSelectionScreen(
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onClickItem = {
                    viewModel.onSelectedTheme(theme = it)
                },
            )
        }
    }
}