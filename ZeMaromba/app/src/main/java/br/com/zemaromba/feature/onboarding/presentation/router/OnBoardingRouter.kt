package br.com.zemaromba.feature.onboarding.presentation.router

sealed class OnBoardingRouter(val route: String) {
    object OnBoardingGraph : OnBoardingRouter("on_boarding")
    object GetStartedScreen : OnBoardingRouter("get_started")
    object UserOriginationNameScreen : OnBoardingRouter("user_origination_name")
}