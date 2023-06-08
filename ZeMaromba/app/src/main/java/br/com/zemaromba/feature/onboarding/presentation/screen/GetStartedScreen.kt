package br.com.zemaromba.feature.onboarding.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.onboarding.presentation.viewmodel.GetStartedState

@Composable
fun GetStartedScreen(
    state: GetStartedState,
    title: String,
    description: String,
    buttonTitle: String,
    onButtonClick: () -> Unit
) {
    if (state.loadingScreen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.image_exercise_welcome),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp),
                text = title,
                lineHeight = 40.sp,
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 20.dp, top = 200.dp, end = 20.dp),
                text = description,
                color = Color.LightGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    onButtonClick()
                }
            ) {
                Text(
                    text = buttonTitle,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GetStartedScreenPreviewPixel5() {
    ZeMarombaTheme {
        GetStartedScreen(
            state = GetStartedState(loadingScreen = false),
            title = stringResource(R.string.start_your_training_journey),
            description = stringResource(R.string.description_screen_get_started),
            buttonTitle = stringResource(R.string.button_title_get_started),
            onButtonClick = { }
        )
    }
}

@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GetStartedScreenPreviewPixel5DarkMode() {
    ZeMarombaTheme {
        GetStartedScreen(
            state = GetStartedState(loadingScreen = false),
            title = stringResource(R.string.start_your_training_journey),
            description = stringResource(R.string.description_screen_get_started),
            buttonTitle = stringResource(R.string.button_title_get_started),
            onButtonClick = { }
        )
    }
}

@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GetStartedScreenPreviewNexus4() {
    ZeMarombaTheme {
        GetStartedScreen(
            state = GetStartedState(loadingScreen = false),
            title = stringResource(R.string.start_your_training_journey),
            description = stringResource(R.string.description_screen_get_started),
            buttonTitle = stringResource(R.string.button_title_get_started),
            onButtonClick = { },
        )
    }
}

@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GetStartedScreenPreviewNexus4DarkMode() {
    ZeMarombaTheme {
        GetStartedScreen(
            state = GetStartedState(loadingScreen = false),
            title = stringResource(R.string.start_your_training_journey),
            description = stringResource(R.string.description_screen_get_started),
            buttonTitle = stringResource(R.string.button_title_get_started),
            onButtonClick = { },
        )
    }
}