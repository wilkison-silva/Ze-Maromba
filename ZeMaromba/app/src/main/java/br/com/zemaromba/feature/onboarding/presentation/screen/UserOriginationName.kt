package br.com.zemaromba.feature.onboarding.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.trainingjourney.core_ui.ui.theme.Black20
import br.com.trainingjourney.core_ui.ui.theme.Green80
import br.com.trainingjourney.core_ui.ui.theme.WhiteF5
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserOriginationNameScreen(
    title: String,
    name: String,
    loadingScreen: Boolean,
    onNameChanged: (newName: String) -> Unit,
    messageWarning: String,
    buttonTitle: String,
    onNextButtonClick: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WhiteF5)
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = title,
            lineHeight = 40.sp,
            color = Black20,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        LaunchedEffect(Unit) {
            delay(200)
            focusRequester.requestFocus()
        }
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            value = name,
            singleLine = true,
            onValueChange = {
                onNameChanged(it)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Black20,
                containerColor = Color.Transparent,
                cursorColor = Black20,
                focusedBorderColor = Black20
            ),
            textStyle = TextStyle.Default.copy(
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            label = {
                Text(
                    text = "Seu nome",
                    color = Black20
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = messageWarning,
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(60.dp))
        Button(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green80,
            ),
            onClick = {
                onNextButtonClick()
            }
        ) {
            if (loadingScreen) {
                CircularProgressIndicator(
                    color = WhiteF5,
                    modifier = Modifier.size(25.dp),
                    strokeWidth = 3.dp
                )
            } else {
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
fun UserOriginationNameScreenPreviewPixel5() {
    val name = remember {
        mutableStateOf("Wilkison")
    }
    UserOriginationNameScreen(
        title = "Como podemos chamar você?",
        name = name.value,
        loadingScreen = true,
        onNameChanged = {
            name.value = it
        },
        messageWarning = "Não se preocupe, você poderá mudar esta informação depois",
        buttonTitle = "Avançar",
        onNextButtonClick = { }
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
fun UserOriginationNameScreenPreviewNexus4() {
    val name = remember {
        mutableStateOf("Wilkison")
    }
    UserOriginationNameScreen(
        title = "Como podemos chamar você?",
        name = name.value,
        loadingScreen = false,
        onNameChanged = {
            name.value = it
        },
        messageWarning = "Não se preocupe, você poderá mudar esta informação depois",
        buttonTitle = "Avançar",
        onNextButtonClick = { }
    )
}