package br.com.zemaromba.presentation.navigation.router

sealed class SetRouter(val route: String) {
    object SetGraph : SetRouter(route = baseGraphRoute)
    object SetCreationScreen : SetRouter(route = "$baseGraphRoute/set_creation")

    companion object Params {
        const val baseGraphRoute = "sets"
    }
}