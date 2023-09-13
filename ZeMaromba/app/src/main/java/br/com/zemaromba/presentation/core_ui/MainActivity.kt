package br.com.zemaromba.presentation.core_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.zemaromba.common.extensions.openVideoInYoutubeOrBrowser
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.navigation.graph.exerciseGraph
import br.com.zemaromba.presentation.navigation.graph.homeGraph
import br.com.zemaromba.presentation.navigation.graph.onBoardingGraph
import br.com.zemaromba.presentation.navigation.graph.setGraph
import br.com.zemaromba.presentation.navigation.graph.trainingPlanGraph
import br.com.zemaromba.presentation.navigation.router.HomeRouter
import br.com.zemaromba.presentation.navigation.router.OnBoardingRouter
import br.com.zemaromba.presentation.navigation.router.SetCreationRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZeMarombaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = OnBoardingRouter.OnBoardingGraph.route
                    ) {
                        onBoardingGraph(
                            navController = navController,
                            onFinishOnBoarding = {
                                navController.navigate(route = HomeRouter.HomeGraph.route) {
                                    popUpTo(OnBoardingRouter.OnBoardingGraph.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                        homeGraph(navController = navController)
                        exerciseGraph(
                            navController = navController,
                            openYoutube = { videoId: String ->
                                openVideoInYoutubeOrBrowser(urlLink = videoId)
                            }
                        )
                        trainingPlanGraph(
                            navController = navController,
                            openYoutube = { videoId: String ->
                                openVideoInYoutubeOrBrowser(urlLink = videoId)
                            },
                            onCreateNewSet = { trainingId, setId ->
                                navController.navigate(
                                    route = SetCreationRouter
                                        .SetCreationGraph
                                        .getRouteWithTrainingId(
                                            trainingId = trainingId,
                                            setId = setId
                                        )
                                )
                            }
                        )
                        setGraph(navController = navController)
                    }
                }
            }
        }
    }
}