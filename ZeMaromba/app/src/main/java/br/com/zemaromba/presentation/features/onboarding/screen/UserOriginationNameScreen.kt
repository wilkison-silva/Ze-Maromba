package br.com.zemaromba.presentation.features.onboarding.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.button.PrimaryButton
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.onboarding.screen.state.UserOriginationNameState

@Composable
fun UserOriginationNameScreen(
    state: UserOriginationNameState,
    title: String,
    onNameChanged: (newName: String) -> Unit,
    messageWarning: String,
    buttonTitle: String,
    onNextButtonClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = Dimens.Space.space_20dp),
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = Styles.Title2Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(Dimens.Space.space_20dp))
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = Dimens.Space.space_32dp)
                    .fillMaxWidth(),
                value = state.name,
                singleLine = true,
                onValueChange = {
                    onNameChanged(it)
                },
                textStyle = TextStyle.Default.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                label = {
                    Text(
                        text = stringResource(R.string.your_name),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
            )
            Spacer(modifier = Modifier.height(Dimens.Space.space_20dp))
            Text(
                modifier = Modifier.padding(horizontal = Dimens.Space.space_20dp),
                text = messageWarning,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                style = Styles.BodyTextNormal
            )
            Spacer(modifier = Modifier.height(Dimens.Space.space_64dp))
            PrimaryButton(
                modifier = Modifier
                    .padding(
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_40dp
                    )
                    .fillMaxWidth(),
                onClick = { onNextButtonClick() },
                title = buttonTitle,
                showLoading = state.showLoadingOnButton
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
fun UserOriginationNameScreenPreview() {
    val name = remember {
        mutableStateOf("Wilkison")
    }
    ZeMarombaTheme {
        UserOriginationNameScreen(
            state = UserOriginationNameState(name = "Wilkison"),
            title = "Como podemos chamar você?",
            onNameChanged = {
                name.value = it
            },
            messageWarning = "Não se preocupe, você poderá mudar esta informação depois",
            buttonTitle = "Avançar",
            onNextButtonClick = { }
        )
    }
}