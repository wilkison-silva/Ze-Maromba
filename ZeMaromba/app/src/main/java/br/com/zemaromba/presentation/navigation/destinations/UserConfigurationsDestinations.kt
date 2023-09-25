package br.com.zemaromba.presentation.navigation.destinations

sealed class UserConfigurationsDestinations(val route: String) {
    data object UserConfigurationsGraph : UserConfigurationsDestinations(route = baseGraphRoute)
    data object MenuConfigOptionsListScreen : UserConfigurationsDestinations(route = "$baseGraphRoute/menu_config_options_list")
    data object UserManagementScreen : UserConfigurationsDestinations("$baseGraphRoute/user_management")
    data object ThemeConfigurationListScreen : UserConfigurationsDestinations(route = "$baseGraphRoute/theme_config")

    companion object Params {
        private const val baseGraphRoute = "user_configurations"
    }
}