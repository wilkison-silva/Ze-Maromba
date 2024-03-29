package br.com.zemaromba.presentation.features.training_plan.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.dialogs.SimpleDialog
import br.com.zemaromba.presentation.components.navbar.CustomNavBar
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.training_plan.screen.state.TrainingPlanManagementState
import kotlinx.coroutines.delay

@Composable
fun TrainingPlanManagementScreen(
    state: TrainingPlanManagementState,
    onChangeName: (newName: String) -> Unit,
    onSaveTrainingPlan: () -> Unit,
    onDeleteTrainingPlan: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowAlertAboutRemoving: (showDialog: Boolean) -> Unit,
    onDeleteFinished: () -> Unit
) {
    LaunchedEffect(
        key1 = state.navigateBack,
        key2 = state.onDeleteFinished
    ) {
        if (state.navigateBack) {
            onNavigateBack()
        }
        if (state.onDeleteFinished) {
            onDeleteFinished()
        }
    }
    if (state.showDialog) {
        SimpleDialog(
            onDismissClick = {
                onShowAlertAboutRemoving(false)
            },
            onConfirmClick = {
                onDeleteTrainingPlan()
            },
            dismissText = stringResource(R.string.button_cancel),
            confirmText = stringResource(R.string.button_continue),
            titleText = stringResource(R.string.warning),
            descriptionText = stringResource(R.string.warning_about_remove_training_plan),
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomNavBar(
                onBackIconClick = { onNavigateBack() },
                actions = {
                    state.trainingPlanId?.let {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                onShowAlertAboutRemoving(true)
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            onSaveTrainingPlan()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_done),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                },
                title = stringResource(R.string.title_training_plan)
            )
        }
    ) { contentPadding ->
        val verticalScrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
        ) {
            val focusManager = LocalFocusManager.current
            val focusRequester = remember { FocusRequester() }
            LaunchedEffect(Unit) {
                delay(200)
                focusRequester.requestFocus()
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .padding(all = Dimens.Space.space_20dp),
                value = state.name,
                onValueChange = {
                    onChangeName(it)
                },
                label = {
                    Text(text = stringResource(R.string.name))
                },
                placeholder = {
                    Text(text = stringResource(R.string.example_name_training_plan))
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                isError = state.nameIsBlank,
                supportingText = {
                    if (state.nameIsBlank) {
                        Text(text = stringResource(R.string.hint_put_your_name))
                    }
                }
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
fun TrainingPlanManagementScreenPreview() {
    val state = remember {
        mutableStateOf(TrainingPlanManagementState(trainingPlanId = 10))
    }

    ZeMarombaTheme {
        TrainingPlanManagementScreen(
            state = state.value,
            onNavigateBack = {

            },
            onChangeName = {
                state.value = state.value.copy(name = it)
            },
            onSaveTrainingPlan = {

            },
            onDeleteTrainingPlan = {

            },
            onShowAlertAboutRemoving = {

            },
            onDeleteFinished = {

            }
        )
    }
}