package br.com.zemaromba.presentation.core_ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.zemaromba.common.extensions.openVideoInYoutubeOrBrowser
import br.com.zemaromba.presentation.core_ui.navigation.PopUpToDestination
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.exercises.navigation.addExerciseGraph
import br.com.zemaromba.presentation.features.home.navigation.addHomeGraph
import br.com.zemaromba.presentation.features.home.navigation.navigateToHomeGraph
import br.com.zemaromba.presentation.features.onboarding.navigation.addOnBoardingGraph
import br.com.zemaromba.presentation.features.onboarding.navigation.getOnBoardingGraphRoute
import br.com.zemaromba.presentation.features.sets_creation.navigation.addSetsCreationGraph
import br.com.zemaromba.presentation.features.sets_creation.navigation.navigateToSetsCreationGraph
import br.com.zemaromba.presentation.features.user_configurations.navigation.addUserConfigurationsGraph
import br.com.zemaromba.presentation.features.training_plan.navigation.addTrainingPlanGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel: MainActivityViewModel = hiltViewModel()
            val state = mainViewModel.state.collectAsStateWithLifecycle().value

            ZeMarombaTheme(theme = state.selectedTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
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
                        addTrainingPlanGraph(
                            navController = navController,
                            openYoutube = { urlLink: String ->
                                openVideoInYoutubeOrBrowser(urlLink = urlLink)
                            },
                            onCreateNewSet = { trainingId, setId ->
                                navController.navigateToSetsCreationGraph(
                                    trainingId = trainingId,
                                    setId = setId
                                )
                            }
                        )
                        addSetsCreationGraph(navController = navController)
                    }
                }
            }
        }
    }
}