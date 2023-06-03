package br.com.zemaromba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import br.com.trainingjourney.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.home.presentation.screen.HomeScreen
import br.com.zemaromba.feature.onboarding.presentation.OnBoardingRouter
import br.com.zemaromba.feature.onboarding.presentation.screen.GetStartedScreen
import br.com.zemaromba.feature.onboarding.presentation.screen.UserOriginationNameScreen
import br.com.zemaromba.feature.onboarding.presentation.viewmodel.TesteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeMarombaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = OnBoardingRouter.OnBoardingGraphRouter.route
                    ) {
                        onBoardingGraph(
                            navController = navController,
                            onFinishOnBoarding = { userName: String ->
                                navController.navigate("homeGraph/$userName")
                            }
                        )
                        homeGraph(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavController,
) {
    navigation(
        startDestination = "home",
        route = "homeGraph/{userName}"
    ) {
        composable(
            route = "home"
        ) {
            val userName = remember(key1 = it) {
                navController
                    .getBackStackEntry("homeGraph/{userName}")
                    .arguments
                    ?.getString("userName")
                    .orEmpty()
            }
            HomeScreen(userName = userName)
        }
    }
}

fun NavGraphBuilder.onBoardingGraph(
    navController: NavController,
    onFinishOnBoarding: (userName: String) -> Unit
) {
    navigation(
        startDestination = OnBoardingRouter.GetStartedScreen.route,
        route = OnBoardingRouter.OnBoardingGraphRouter.route
    ) {
        composable(route = OnBoardingRouter.GetStartedScreen.route) {
            val viewModel: TesteViewModel = hiltViewModel()
            GetStartedScreen(
                title = stringResource(R.string.start_your_training_journey),
                description = stringResource(R.string.description_screen_get_started),
                buttonTitle = stringResource(R.string.button_title_get_started),
                onButtonClick = {
                    viewModel.addExercises()
//                    navController.navigate(route = OnBoardingRouter.UserOriginationNameScreen.route) {
//                        popUpTo(route = OnBoardingRouter.GetStartedScreen.route) {
//                            inclusive = true
//                        }
//                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = OnBoardingRouter.UserOriginationNameScreen.route) {
            val name = remember {
                mutableStateOf("")
            }
            UserOriginationNameScreen(
                title = stringResource(R.string.whats_your_name),
                name = name.value,
                onNameChanged = {
                    name.value = it
                },
                messageWarning = stringResource(R.string.dont_you_worry_you_can_change_your_name_later),
                buttonTitle = stringResource(R.string.next),
                onNextButtonClick = { userName: String ->
                    onFinishOnBoarding(userName)
                }
            )
        }
    }
}