package br.com.zemaromba.feature.onboarding.presentation.router

sealed class OnBoardingRouter(val route: String) {
    object OnBoardingGraph : OnBoardingRouter(route = baseGraphRoute)
    object GetStartedScreen : OnBoardingRouter(route = "$baseGraphRoute/get_started")
    object UserOriginationNameScreen : OnBoardingRouter(
        route = "$baseGraphRoute/user_origination_name"
    )
    companion object Params {
        const val baseGraphRoute = "on_boarding"
    }
}