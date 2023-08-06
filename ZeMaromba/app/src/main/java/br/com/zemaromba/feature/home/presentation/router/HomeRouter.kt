package br.com.zemaromba.feature.home.presentation.router

sealed class HomeRouter(val route: String) {
    object HomeGraph : HomeRouter(route = baseGraphRoute)
    object HomeScreen : HomeRouter("$baseGraphRoute/home_menu")

    companion object Params {
        const val baseGraphRoute = "home"
    }
}