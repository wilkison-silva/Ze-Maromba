package br.com.zemaromba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.trainingjourney.core_ui.ui.theme.ZeMarombaTheme
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
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
                        )
                    }
                }
            }
        }
    }
}