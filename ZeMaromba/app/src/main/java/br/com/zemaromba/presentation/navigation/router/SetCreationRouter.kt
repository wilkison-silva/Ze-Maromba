package br.com.zemaromba.presentation.navigation.router

sealed class SetCreationRouter(val route: String) {
    object SetCreationGraph : SetCreationRouter(route = baseGraphRoute)
    object SelectExercise : SetCreationRouter(route = "$baseGraphRoute/set_creation")

    companion object Params {
        const val baseGraphRoute = "sets"
    }
}