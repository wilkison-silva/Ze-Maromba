package br.com.zemaromba.presentation.features.onboarding.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.R
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.core_ui.navigation.BaseRouter
import br.com.zemaromba.presentation.features.onboarding.screen.GetStartedScreen
import br.com.zemaromba.presentation.features.onboarding.screen.UserOriginationNameScreen
import br.com.zemaromba.presentation.features.onboarding.screen.event.UserOriginationNameEvents
import br.com.zemaromba.presentation.features.onboarding.viewmodel.GetStartedViewModel
import br.com.zemaromba.presentation.features.onboarding.viewmodel.UserOriginationNameViewModel

private object OnBoardingDestinations {

    const val baseGraphRoute = "on_boarding"

    sealed class Router : BaseRouter() {
        data object OnBoardingGraph : Router() {
            override val routePattern: String
                get() = baseGraphRoute

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object GetStartedScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/get_started"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }

        data object UserOriginationNameScreen : Router() {
            override val routePattern: String
                get() = "$baseGraphRoute/user_origination_name"

            override fun buildRoute(vararg args: String): String {
                return routePattern
            }
        }
    }
}

fun getOnBoardingGraphRoute(): String {
    return OnBoardingDestinations.Router.OnBoardingGraph.routePattern
}

fun NavController.navigateToOnBoardingGraph() {
    this.navigate(route = OnBoardingDestinations.Router.OnBoardingGraph.buildRoute())
}

fun NavGraphBuilder.addOnBoardingGraph(
    navController: NavController,
    onFinishOnBoarding: () -> Unit,
) {
    navigation(
        route = OnBoardingDestinations.Router.OnBoardingGraph.routePattern,
        startDestination = OnBoardingDestinations.Router.GetStartedScreen.routePattern,
    ) {
        composableWithTransitionAnimation(
            route = OnBoardingDestinations.Router.GetStartedScreen.routePattern,
        ) {
            val viewModel: GetStartedViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = state.userNameIsValid) {
                if (state.userNameIsValid) {
                    onFinishOnBoarding()
                }
            }
            GetStartedScreen(
                state = state,
                title = stringResource(R.string.start_your_training_journey),
                description = stringResource(R.string.description_screen_get_started),
                buttonTitle = stringResource(R.string.button_title_get_started),
                onButtonClick = {
                    navController.navigate(
                        route = OnBoardingDestinations
                            .Router
                            .UserOriginationNameScreen
                            .buildRoute()
                    ) {
                        popUpTo(
                            route = OnBoardingDestinations
                                .Router
                                .GetStartedScreen
                                .buildRoute()
                        ) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composableWithTransitionAnimation(
            route = OnBoardingDestinations.Router.UserOriginationNameScreen.routePattern
        ) {
            val viewModel: UserOriginationNameViewModel = hiltViewModel()
            val state = viewModel.state.collectAsStateWithLifecycle().value
            LaunchedEffect(key1 = state.nameSaved) {
                if (state.nameSaved) {
                    onFinishOnBoarding()
                }
            }
            UserOriginationNameScreen(
                state = state,
                title = stringResource(R.string.whats_your_name),
                onNameChanged = { newName: String ->
                    viewModel.onEvent(
                        event = UserOriginationNameEvents.OnEnterNewName(name = newName)
                    )
                },
                messageWarning = stringResource(R.string.dont_you_worry_you_can_change_your_name_later),
                buttonTitle = stringResource(R.string.next),
                onNextButtonClick = {
                    viewModel.onEvent(
                        event = UserOriginationNameEvents.OnSaveName(name = state.name)
                    )
                }
            )
        }
    }
}