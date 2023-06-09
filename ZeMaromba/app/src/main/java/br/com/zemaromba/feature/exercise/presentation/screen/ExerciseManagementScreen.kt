package br.com.zemaromba.feature.exercise.presentation.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.components.cards.CardInfo
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExerciseManagementState
import br.com.zemaromba.feature.exercise.presentation.viewmodel.MuscleGroupCheckBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseManagementScreen(
    state: ExerciseManagementState,
    onChangeName: (newName: String) -> Unit,
    onMuscleGroupSelection: (id: Int, isSelected: Boolean) -> Unit,
    onSaveExercise: () -> Unit,
    onDeleteExercise: () -> Unit,
    onNavigateBack: () -> Unit,
    onShowAlertAboutRemoving: (showDialog: Boolean) -> Unit
) {
    LaunchedEffect(key1 = state.navigateBack) {
        if (state.navigateBack) {
            onNavigateBack()
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
                        onDeleteExercise()
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
                    text = stringResource(R.string.warning_about_remove_exercise),
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
                        text = stringResource(R.string.title_exercise),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    state.exerciseId?.let {
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
                            onSaveExercise()
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
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                value = state.name,
                onValueChange = {
                    onChangeName(it)
                },
                label = {
                    Text(text = stringResource(R.string.name))
                },
                placeholder = {
                    Text(text = stringResource(R.string.example_exercise))
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

            AnimatedVisibility(visible = state.showMessageAboutMuscleGroup) {
                CardInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    icon = R.drawable.ic_tips_and_updates,
                    message = stringResource(R.string.message_select_at_least_one_muscle_group),
                    borderColor = MaterialTheme.colorScheme.tertiary,
                    surfaceColor = MaterialTheme.colorScheme.tertiaryContainer,
                    onSurfaceColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                text = stringResource(R.string.filter_muscle_group)
            )

            MuscleGroupSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                muscleGroupCheckBoxList = state.muscleGroupCheckBox,
                onMuscleGroupSelected = { index, isSelected ->
                    onMuscleGroupSelection(index, isSelected)
                }
            )

        }
    }
}

@Composable
fun MuscleGroupSelector(
    modifier: Modifier,
    muscleGroupCheckBoxList: List<MuscleGroupCheckBox>,
    onMuscleGroupSelected: (index: Int, isSelected: Boolean) -> Unit
) {
    Surface(
        modifier = modifier
            ,
        tonalElevation = 5.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp),
        ) {
            val itemCount = if (muscleGroupCheckBoxList.size % 2 == 0) {
                muscleGroupCheckBoxList.size / 2
            } else {
                muscleGroupCheckBoxList.size / 2 + 1
            }
            repeat(itemCount) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Checkbox(
                            checked = muscleGroupCheckBoxList[rowIndex * 2].isSelected,
                            onCheckedChange = {
                                onMuscleGroupSelected(rowIndex * 2, it)
                            }
                        )
                        Text(
                            text = stringResource(id = muscleGroupCheckBoxList[rowIndex * 2].nameRes),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    if (muscleGroupCheckBoxList.size >= ((rowIndex * 2) + 2)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Checkbox(
                                checked = muscleGroupCheckBoxList[rowIndex * 2 + 1].isSelected,
                                onCheckedChange = {
                                    onMuscleGroupSelected(rowIndex * 2 + 1, it)
                                }
                            )
                            Text(
                                text = stringResource(id = muscleGroupCheckBoxList[rowIndex * 2 + 1].nameRes),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
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
        mutableStateOf(ExerciseManagementState(exerciseId = 10))
    }

    ZeMarombaTheme {
        ExerciseManagementScreen(
            state = state.value,
            onNavigateBack = {

            },
            onChangeName = {
                state.value = state.value.copy(name = it)
            },
            onMuscleGroupSelection = { id, isSelected ->
                state.value = state.value.copy(
                    muscleGroupCheckBox = state.value.muscleGroupCheckBox.toMutableList()
                        .apply {
                            this[id] = this[id].copy(isSelected = isSelected)
                        }
                )
            },
            onSaveExercise = {

            },
            onDeleteExercise = {

            },
            onShowAlertAboutRemoving = {

            }
        )
    }
}