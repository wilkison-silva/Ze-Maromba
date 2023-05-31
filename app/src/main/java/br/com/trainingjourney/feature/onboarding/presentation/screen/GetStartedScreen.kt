package br.com.trainingjourney.feature.onboarding.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import br.com.trainingjourney.R
import br.com.trainingjourney.core_ui.ui.theme.Green80

@Composable
fun GetStartedScreen(
    title: String,
    description: String,
    buttonTitle: String,
    onButtonClick: () -> Unit,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
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
                containerColor = Green80,
            ),
            onClick = {
                onButtonClick()
            }
        ) {
            Text(
                text = buttonTitle,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun GetStartedScreenPreviewPixel5() {
    GetStartedScreen(
        title = stringResource(R.string.start_your_training_journey),
        description = stringResource(R.string.description_screen_get_started),
        buttonTitle = stringResource(R.string.button_title_get_started),
        onButtonClick = { },
        modifier = Modifier.fillMaxSize()
    )
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
    GetStartedScreen(
        title = stringResource(R.string.start_your_training_journey),
        description = stringResource(R.string.description_screen_get_started),
        buttonTitle = stringResource(R.string.button_title_get_started),
        onButtonClick = { },
        modifier = Modifier.fillMaxSize()
    )
}