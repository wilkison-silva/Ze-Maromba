package br.com.zemaromba.presentation.onboarding.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.button.PrimaryButton
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.onboarding.screen.state.GetStartedState

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
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(
                        start = Dimens.Space.space_20dp,
                        top = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp
                    ),
                verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_8dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    style = Styles.Title1Bold,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center,
                    style = Styles.BodyTextNormal
                )
            }
            PrimaryButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_40dp
                    )
                    .fillMaxWidth(),
                onClick = { onButtonClick() },
                title = buttonTitle
            )
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
@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
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