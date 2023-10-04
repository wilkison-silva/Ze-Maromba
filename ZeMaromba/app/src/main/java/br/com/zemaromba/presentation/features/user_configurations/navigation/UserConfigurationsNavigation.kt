package br.com.zemaromba.presentation.features.user_configurations.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.core_ui.navigation.BaseRouter
import br.com.zemaromba.presentation.features.user_configurations.screen.ConfigurationListScreen
import br.com.zemaromba.presentation.features.user_configurations.screen.ThemeSelectionScreen
import br.com.zemaromba.presentation.features.user_configurations.screen.UserManagementScreen
import br.com.zemaromba.presentation.features.user_configurations.viewmodel.ThemeSelectionViewModel
import br.com.zemaromba.presentation.features.user_configurations.viewmodel.UserManagementViewModel

private object UserConfigurationsDestinations {

    private const val baseGraphRoute = "user_configurations"

    sealed class Router : BaseRouter() {
        data object UserConfigurationsGraph : Router() {
            override val routePattern: String
                get() = baseGraphRoute

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object MenuConfigOptionsListScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/menu_config_options_list"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object UserManagementScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/user_management"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object ThemeConfigurationListScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/theme_config"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }
    }
}

fun NavController.navigateToUserConfigurationsGraph() {
    this.navigate(route = UserConfigurationsDestinations.Router.UserConfigurationsGraph.buildRoute())
}

fun NavGraphBuilder.addUserConfigurationsGraph(
    navController: NavController,
    onContactByEmail: () -> Unit
) {
    navigation(
        route = UserConfigurationsDestinations.Router.UserConfigurationsGraph.routePattern,
        startDestination = UserConfigurationsDestinations.Router.MenuConfigOptionsListScreen.routePattern,
    ) {
        composableWithTransitionAnimation(
            route = UserConfigurationsDestinations.Router.MenuConfigOptionsListScreen.routePattern
        ) {
            ConfigurationListScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToUserAccountConfigs = {
                    navController.navigate(UserConfigurationsDestinations.Router.UserManagementScreen.buildRoute())
                },
                onNavigateToThemeConfigs = {
                    navController.navigate(UserConfigurationsDestinations.Router.ThemeConfigurationListScreen.buildRoute())
                },
                onContactByEmailClick = {
                    onContactByEmail()
                }
            )
        }

        composableWithTransitionAnimation(
            route = UserConfigurationsDestinations.Router.UserManagementScreen.routePattern,
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
            route = UserConfigurationsDestinations.Router.ThemeConfigurationListScreen.routePattern,
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