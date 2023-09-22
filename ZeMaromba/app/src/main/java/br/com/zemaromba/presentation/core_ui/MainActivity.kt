package br.com.zemaromba.presentation.core_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.zemaromba.common.extensions.openVideoInYoutubeOrBrowser
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.model.Theme
import br.com.zemaromba.presentation.navigation.nav_graphs.exerciseGraph
import br.com.zemaromba.presentation.navigation.nav_graphs.homeGraph
import br.com.zemaromba.presentation.navigation.nav_graphs.onBoardingGraph
import br.com.zemaromba.presentation.navigation.nav_graphs.setsGraph
import br.com.zemaromba.presentation.navigation.nav_graphs.trainingPlanGraph
import br.com.zemaromba.presentation.navigation.destinations.HomeDestinations
import br.com.zemaromba.presentation.navigation.destinations.OnBoardingDestinations
import br.com.zemaromba.presentation.navigation.destinations.SetCreationDestinations
import br.com.zemaromba.presentation.navigation.nav_graphs.userConfigurationsGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = mainViewModel.state.collectAsStateWithLifecycle().value
            ZeMarombaTheme(
                theme = state.selectedTheme
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = OnBoardingDestinations.OnBoardingGraph.route
                    ) {
                        onBoardingGraph(
                            navController = navController,
                            onFinishOnBoarding = {
                                navController.navigate(route = HomeDestinations.HomeGraph.route) {
                                    popUpTo(OnBoardingDestinations.OnBoardingGraph.route) {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                        homeGraph(navController = navController)
                        userConfigurationsGraph(navController = navController)
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
                                    route = SetCreationDestinations
                                        .SetCreationGraph
                                        .getRouteWithTrainingId(
                                            trainingId = trainingId,
                                            setId = setId
                                        )
                                )
                            }
                        )
                        setsGraph(navController = navController)
                    }
                }
            }
        }
    }
}