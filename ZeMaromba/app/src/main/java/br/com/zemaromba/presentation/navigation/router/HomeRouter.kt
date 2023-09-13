package br.com.zemaromba.presentation.navigation.router

sealed class HomeRouter(val route: String) {
    object HomeGraph : HomeRouter(route = baseGraphRoute)
    object HomeScreen : HomeRouter("$baseGraphRoute/home_menu")
    object UserManagementScreen : HomeRouter("$baseGraphRoute/user_management")

    companion object Params {
        const val baseGraphRoute = "home"
    }
}