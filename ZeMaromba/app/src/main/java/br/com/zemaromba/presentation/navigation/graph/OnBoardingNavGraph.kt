package br.com.zemaromba.presentation.navigation.graph

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import br.com.zemaromba.R
import br.com.zemaromba.common.extensions.composableWithTransitionAnimation
import br.com.zemaromba.presentation.navigation.router.OnBoardingRouter
import br.com.zemaromba.presentation.onboarding.screen.GetStartedScreen
import br.com.zemaromba.presentation.onboarding.screen.UserOriginationNameScreen
import br.com.zemaromba.presentation.onboarding.screen.event.UserOriginationNameEvents
import br.com.zemaromba.presentation.onboarding.viewmodel.GetStartedViewModel
import br.com.zemaromba.presentation.onboarding.viewmodel.UserOriginationNameViewModel

fun NavGraphBuilder.onBoardingGraph(
    navController: NavController,
    onFinishOnBoarding: () -> Unit,
) {
    navigation(
        startDestination = OnBoardingRouter.GetStartedScreen.route,
        route = OnBoardingRouter.OnBoardingGraph.route
    ) {
        composableWithTransitionAnimation(
            route = OnBoardingRouter.GetStartedScreen.route,
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
                    navController.navigate(route = OnBoardingRouter.UserOriginationNameScreen.route) {
                        popUpTo(route = OnBoardingRouter.GetStartedScreen.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composableWithTransitionAnimation(
            route = OnBoardingRouter.UserOriginationNameScreen.route
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