package br.com.zemaromba.feature.training_plan.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanManagementState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
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
    LaunchedEffect(key1 = state.navigateBack, key2 = state.onDeleteFinished) {
        if (state.navigateBack) {
            onNavigateBack()
        }
        if (state.onDeleteFinished) {
            onDeleteFinished()
        }
    }
    if (state.showDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurface,
            onDismissRequest = {
                onShowAlertAboutRemoving(false)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteTrainingPlan()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_continue),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onShowAlertAboutRemoving(false)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_cancel),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            title = {
                Text(text = stringResource(R.string.warning))
            },
            text = {
                Text(
                    text = stringResource(R.string.warning_about_remove_training_plan),
                    fontSize = 16.sp,
                )
            }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateBack()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.title_training_plan),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                },
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
                }
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
                    .padding(horizontal = 20.dp, vertical = 20.dp),
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
fun ExercisesManagementScreenPreview() {
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