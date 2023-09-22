package br.com.zemaromba.presentation.navigation.destinations

sealed class HomeDestinations(val route: String) {
    data object HomeGraph : HomeDestinations(route = baseGraphRoute)
    data object HomeScreen : HomeDestinations("$baseGraphRoute/home_menu")
    data object UserManagementScreen : HomeDestinations("$baseGraphRoute/user_management")

    companion object Params {
        private const val baseGraphRoute = "home"
    }
}