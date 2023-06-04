package br.com.zemaromba.feature.onboarding.presentation.router

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.zemaromba.R
import br.com.zemaromba.feature.onboarding.presentation.screen.GetStartedScreen
import br.com.zemaromba.feature.onboarding.presentation.screen.UserOriginationNameScreen
import br.com.zemaromba.feature.onboarding.presentation.viewmodel.MainActivityViewModel
import br.com.zemaromba.feature.onboarding.presentation.viewmodel.UserOriginationNameEvents
import br.com.zemaromba.feature.onboarding.presentation.viewmodel.UserOriginationNameViewModel

fun NavGraphBuilder.onBoardingGraph(
    navController: NavController,
    onFinishOnBoarding: (userName: String) -> Unit
) {
    navigation(
        startDestination = OnBoardingRouter.GetStartedScreen.route,
        route = OnBoardingRouter.OnBoardingGraph.route
    ) {
        composable(route = OnBoardingRouter.GetStartedScreen.route) {
            val viewModel: MainActivityViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = state.userNameIsValid) {
                if (state.userNameIsValid) {
                    onFinishOnBoarding(state.userName)
                }
            }
            GetStartedScreen(
                title = stringResource(R.string.start_your_training_journey),
                loadingScreen = state.loadingScreen,
                description = stringResource(R.string.description_screen_get_started),
                buttonTitle = stringResource(R.string.button_title_get_started),
                onButtonClick = {
                    navController.navigate(route = OnBoardingRouter.UserOriginationNameScreen.route) {
                        popUpTo(route = OnBoardingRouter.GetStartedScreen.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = OnBoardingRouter.UserOriginationNameScreen.route) {
            val viewModel: UserOriginationNameViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState().value
            LaunchedEffect(key1 = state.nameSaved) {
                if (state.nameSaved) {
                    onFinishOnBoarding(state.name)
                }
            }
            UserOriginationNameScreen(
                title = stringResource(R.string.whats_your_name),
                name = state.name,
                loadingScreen = state.loadingScreen,
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