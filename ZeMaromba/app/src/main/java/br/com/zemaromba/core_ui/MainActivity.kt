package br.com.zemaromba.core_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.exercise.presentation.router.ExerciseRouter
import br.com.zemaromba.feature.exercise.presentation.router.exerciseGraph
import br.com.zemaromba.feature.home.presentation.model.MenuHome
import br.com.zemaromba.feature.home.presentation.router.HomeRouter
import br.com.zemaromba.feature.home.presentation.router.homeGraph
import br.com.zemaromba.feature.onboarding.presentation.router.OnBoardingRouter
import br.com.zemaromba.feature.onboarding.presentation.router.onBoardingGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeMarombaTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = OnBoardingRouter.OnBoardingGraph.route
                ) {
                    onBoardingGraph(
                        navController = navController,
                        onFinishOnBoarding = { userName: String ->
                            navController.navigate(
                                route = HomeRouter
                                    .HomeGraph
                                    .getRouteWithUserName(userName = userName)
                            ) {
                                popUpTo(OnBoardingRouter.OnBoardingGraph.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                    homeGraph(
                        navController = navController,
                        navigateTo = {
                            when (it) {
                                MenuHome.TRAINING_PLANS -> {

                                }
                                MenuHome.EXERCISES -> {
                                    navController.navigate(ExerciseRouter.ExerciseGraph.route)
                                }
                            }
                        }
                    )
                    exerciseGraph(navController = navController)
                }
            }
        }
    }
}