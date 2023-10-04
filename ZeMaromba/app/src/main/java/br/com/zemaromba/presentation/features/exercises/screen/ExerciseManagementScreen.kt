package br.com.zemaromba.presentation.features.exercises.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.cards.CardInfo
import br.com.zemaromba.presentation.components.dialogs.SimpleDialog
import br.com.zemaromba.presentation.components.navbar.CustomNavBar
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.exercises.screen.state.ExerciseManagementState
import br.com.zemaromba.presentation.features.exercises.screen.state.MuscleGroupCheckBoxState

@Composable
fun ExerciseManagementScreen(
    state: ExerciseManagementState,
    onChangeName: (newName: String) -> Unit,
    onChangeUrlLink: (url: String) -> Unit,
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
        SimpleDialog(
            onDismissClick = {
                onShowAlertAboutRemoving(false)
            },
            onConfirmClick = {
                onDeleteExercise()
            },
            dismissText = stringResource(R.string.button_cancel),
            confirmText = stringResource(R.string.button_continue),
            titleText = stringResource(R.string.warning),
            descriptionText = stringResource(R.string.warning_about_remove_exercise),
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomNavBar(
                onBackIconClick = { onNavigateBack() },
                actions = {
                    state.exerciseId?.let {
                        if (state.mayExclude) {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                    onShowAlertAboutRemoving(true)
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_delete),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                        }
                    }
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            onSaveExercise()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_done),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                },
                title = stringResource(R.string.title_exercise),
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
                    .padding(
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp,
                        top = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_12dp
                    )
                    .fillMaxWidth(),
                value = state.name,
                onValueChange = {
                    onChangeName(it)
                },
                label = {
                    Text(text = stringResource(R.string.name))
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(Dimens.Space.space_20dp),
                        painter = painterResource(id = R.drawable.ic_dumbell),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                placeholder = {
                    Text(text = stringResource(R.string.example_exercise))
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                isError = state.nameIsBlank,
                supportingText = {
                    if (state.nameIsBlank) {
                        Text(text = stringResource(R.string.hint_put_your_name))
                    }
                }
            )


            TextField(
                modifier = Modifier
                    .padding(
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_12dp
                    )
                    .fillMaxWidth(),
                enabled = !state.isNativeFromApp,
                value = state.urlLink.orEmpty(),
                onValueChange = {
                    onChangeUrlLink(it)
                },
                label = {
                    Text(text = stringResource(R.string.video_url))
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(Dimens.Space.space_20dp),
                        painter = painterResource(id = R.drawable.ic_play_video_youtube),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                placeholder = {
                    Text(text = stringResource(R.string.example_url))
                },
                singleLine = true,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                isError = state.urlHasError,
                supportingText = {
                    if (state.urlHasError) {
                        Text(text = stringResource(R.string.hint_put_valid_url))
                    }
                }
            )


            AnimatedVisibility(visible = state.showMessageAboutMuscleGroup) {
                CardInfo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.Space.space_20dp),
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
                    .padding(
                        top = Dimens.Space.space_12dp,
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp
                    ),
                text = stringResource(R.string.filter_muscle_group),
                style = Styles.BodyTextNormal
            )

            MuscleGroupSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.Space.space_20dp,
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_20dp
                    ),
                muscleGroupCheckBoxStateList = state.muscleGroupCheckBoxStates,
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
    muscleGroupCheckBoxStateList: List<MuscleGroupCheckBoxState>,
    onMuscleGroupSelected: (index: Int, isSelected: Boolean) -> Unit
) {
    Surface(
        modifier = modifier,
        tonalElevation = Dimens.TonalElevation.tonal_4dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = Dimens.Space.space_12dp),
        ) {
            val itemCount = if (muscleGroupCheckBoxStateList.size % 2 == 0) {
                muscleGroupCheckBoxStateList.size / 2
            } else {
                muscleGroupCheckBoxStateList.size / 2 + 1
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
                            checked = muscleGroupCheckBoxStateList[rowIndex * 2].isSelected,
                            onCheckedChange = {
                                onMuscleGroupSelected(rowIndex * 2, it)
                            }
                        )
                        Text(
                            text = stringResource(id = muscleGroupCheckBoxStateList[rowIndex * 2].nameRes),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = Styles.BodyTextNormal
                        )
                    }
                    if (muscleGroupCheckBoxStateList.size >= ((rowIndex * 2) + 2)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Checkbox(
                                checked = muscleGroupCheckBoxStateList[rowIndex * 2 + 1].isSelected,
                                onCheckedChange = {
                                    onMuscleGroupSelected(rowIndex * 2 + 1, it)
                                }
                            )
                            Text(
                                text = stringResource(id = muscleGroupCheckBoxStateList[rowIndex * 2 + 1].nameRes),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = Styles.BodyTextNormal
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
            onChangeUrlLink = {
                state.value = state.value.copy(urlLink = it)
            },
            onMuscleGroupSelection = { id, isSelected ->
                state.value = state.value.copy(
                    muscleGroupCheckBoxStates = state.value.muscleGroupCheckBoxStates.toMutableList()
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