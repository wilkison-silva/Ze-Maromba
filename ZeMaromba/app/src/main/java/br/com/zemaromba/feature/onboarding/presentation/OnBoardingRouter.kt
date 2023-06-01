package br.com.zemaromba.feature.onboarding.presentation

sealed class OnBoardingRouter(val route: String) {
    object OnBoardingGraphRouter : OnBoardingRouter("onboarding")
    object GetStartedScreen : OnBoardingRouter("get_started")
    object UserOriginationNameScreen : OnBoardingRouter("user_origination_name")
}