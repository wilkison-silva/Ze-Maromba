package br.com.zemaromba.feature.home.presentation.router

sealed class HomeRouter(val route: String) {
    object HomeGraph : HomeRouter("homeGraphEntry")
    object HomeScreen : HomeRouter("home")
}