package br.com.zemaromba.presentation.navigation.destinations

sealed class HomeDestinations(val route: String) {
    data object HomeGraph : HomeDestinations(route = baseGraphRoute)
    data object HomeScreen : HomeDestinations("$baseGraphRoute/home_menu")

    companion object Params {
        private const val baseGraphRoute = "home"
    }
}