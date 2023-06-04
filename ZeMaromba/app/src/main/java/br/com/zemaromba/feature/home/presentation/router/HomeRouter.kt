package br.com.zemaromba.feature.home.presentation.router

sealed class HomeRouter(val route: String) {
    object HomeGraph : HomeRouter("home/{${Params.userName}}") {
        fun getRouteWithUserName(userName: String): String {
            return "home/$userName"
        }
        object Params {
            const val userName = "userName"
        }
    }
    object HomeScreen : HomeRouter("home")
}