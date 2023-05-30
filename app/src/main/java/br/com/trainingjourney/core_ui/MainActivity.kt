package br.com.trainingjourney.core_ui

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
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import br.com.trainingjourney.R
import br.com.trainingjourney.core_ui.ui.theme.TrainingJourneyTheme
import br.com.trainingjourney.feature.onboarding.presentation.screen.GetStartedScreen
import br.com.trainingjourney.feature.onboarding.presentation.screen.UserOriginationNameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrainingJourneyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "onboarding"
                    ) {
                        onBoardingGraph(
                            navController = navController,
                            onFinishOnBoarding = {
                                //Navegar para home?
                            }
                        )
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.onBoardingGraph(
    navController: NavController,
    onFinishOnBoarding: () -> Unit
) {
    navigation(startDestination = "getStarted", route = "onboarding") {
        composable(route = "getStarted") {
            GetStartedScreen(
                title = stringResource(R.string.comece_a_sua_jornada_de_treinos),
                description = stringResource(R.string.description_screen_get_started),
                buttonTitle = stringResource(R.string.button_title_get_started),
                onButtonClick = {
                    navController.navigate(route = "userOriginationName") {
                        popUpTo(route = "getStarted") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        composable(route = "userOriginationName") {
            val name = remember {
                mutableStateOf("")
            }
            UserOriginationNameScreen(
                modifier = Modifier.fillMaxSize(),
                title = "Como podemos chamar você?",
                name = name.value,
                onNameChanged = {
                    name.value = it
                },
                messageWarning = "Não se preocupe, você poderá mudar esta informação depois",
                buttonTitle = "Avançar",
                onNextButtonClick = { _: String ->
                    onFinishOnBoarding()
                }
            )
        }
    }
}