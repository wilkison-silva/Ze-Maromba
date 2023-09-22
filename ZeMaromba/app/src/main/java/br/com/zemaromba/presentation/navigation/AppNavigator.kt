package br.com.zemaromba.presentation.navigation

import androidx.navigation.NavController

class AppNavigator(
    private val navController: NavController
) {

    fun goToDestination(
        routeDestination: String
    ) {
        navController.navigate(route = routeDestination)
    }

    fun goToDestination(
        routeDestination: String,
        popUpToRoute: String,
        isInclusive: Boolean
    ) {
        navController.navigate(route = routeDestination) {
            popUpTo(route = popUpToRoute) {
                inclusive = isInclusive
            }
        }
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun goBack(
        routeDestination: String,
        isInclusive: Boolean
    ) {
        navController.popBackStack(
            route = routeDestination,
            inclusive = isInclusive
        )
    }
}