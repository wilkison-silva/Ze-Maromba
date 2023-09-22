package br.com.zemaromba.presentation.navigation.destinations

sealed class OnBoardingDestinations(val route: String) {
    data object OnBoardingGraph : OnBoardingDestinations(route = baseGraphRoute)
    data object GetStartedScreen : OnBoardingDestinations(route = "$baseGraphRoute/get_started")
    data object UserOriginationNameScreen : OnBoardingDestinations(
        route = "$baseGraphRoute/user_origination_name"
    )
    companion object Params {
        const val baseGraphRoute = "on_boarding"
    }
}