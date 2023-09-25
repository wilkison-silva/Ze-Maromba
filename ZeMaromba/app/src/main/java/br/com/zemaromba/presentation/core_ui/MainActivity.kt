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
import br.com.zemaromba.presentation.core_ui.navigation.PopUpToDestination
import br.com.zemaromba.presentation.features.exercises.navigation.addExerciseGraph
import br.com.zemaromba.presentation.features.home.navigation.addHomeGraph
import br.com.zemaromba.presentation.features.home.navigation.navigateToHomeGraph
import br.com.zemaromba.presentation.features.onboarding.navigation.addOnBoardingGraph
import br.com.zemaromba.presentation.features.onboarding.navigation.getOnBoardingGraphRoute
import br.com.zemaromba.presentation.navigation.nav_graphs.setsGraph
import br.com.zemaromba.presentation.navigation.nav_graphs.trainingPlanGraph
import br.com.zemaromba.presentation.navigation.destinations.SetCreationDestinations
import br.com.zemaromba.presentation.features.user_configurations.navigation.addUserConfigurationsGraph
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
                        startDestination = getOnBoardingGraphRoute()
                    ) {
                        addOnBoardingGraph(
                            navController = navController,
                            onFinishOnBoarding = {
                                navController.navigateToHomeGraph(
                                    popUpToDestination = PopUpToDestination(
                                        route = getOnBoardingGraphRoute(),
                                        inclusive = true
                                    )
                                )
                            }
                        )
                        addHomeGraph(navController = navController)
                        addUserConfigurationsGraph(navController = navController)
                        addExerciseGraph(
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