package br.com.zemaromba.presentation.sets.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.presentation.components.button.PrimaryButton
import br.com.zemaromba.presentation.components.linear_progress_bar.LinearProgressBar
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.model.ExerciseView
import br.com.zemaromba.presentation.sets.screen.state.CreateExerciseState

@Composable
fun ExerciseDetailsScreen(
    state: CreateExerciseState,
    onNavigateBack: () -> Unit,
    onChangeSeries: (series: String) -> Unit,
    onChangeRepetition: (repetitions: String) -> Unit,
    onChangeWeight: (weight: String) -> Unit,
    onChangeRestingTime: (restintTime: String) -> Unit,
) {
    BackHandler {
        onNavigateBack()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(
                navBarType = NavBarType.BACK_TITLE,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = null,
                onActionIconClick = null,
                title = stringResource(R.string.title_config_exercise)
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Dimens.Space.space_20dp),
                onClick = {

                },
                title = stringResource(id = R.string.next),
                isEnabled = state.isAllTextInputsNotEmpty
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
        ) {
            LinearProgressBar(
                modifier = Modifier.fillMaxWidth(),
                initialProgress = state.progressBarInitial,
                targetProgress = state.progressBarTarget
            )
            Text(
                modifier = Modifier.padding(
                    start = Dimens.Space.space_20dp,
                    top = Dimens.Space.space_20dp,
                ),
                text = state.selectedExercise?.name.orEmpty(),
                color = MaterialTheme.colorScheme.onSurface,
                style = Styles.Title3Normal
            )
            Text(
                modifier = Modifier.padding(
                    start = Dimens.Space.space_20dp,
                    top = Dimens.Space.space_20dp
                ),
                text = state.selectedExercise?.muscleGroups?.map { muscleNameResource ->
                    stringResource(id = muscleNameResource)
                }?.joinToString(separator = ", ") ?: "",
                color = MaterialTheme.colorScheme.onSurface,
                style = Styles.BodyTextNormal
            )
            Row(
                modifier = Modifier
                    .padding(
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_20dp,
                        top = Dimens.Space.space_32dp,
                        bottom = Dimens.Space.space_20dp
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Space.space_20dp)
            ) {
                val focusManager = LocalFocusManager.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = state.seriesValue,
                    onValueChange = {
                        onChangeSeries(it)
                    },
                    label = {
                        Text(text = stringResource(R.string.label_input_text_series))
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = state.repetitionsValue,
                    onValueChange = {
                        onChangeRepetition(it)
                    },
                    label = {
                        Text(text = stringResource(R.string.label_input_text_repetitions))
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
                    )
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = Dimens.Space.space_20dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.Space.space_20dp)
            ) {
                val focusManager = LocalFocusManager.current
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = state.weightValue,
                    onValueChange = {
                        onChangeWeight(it)
                    },
                    label = {
                        Text(text = stringResource(R.string.label_input_text_weight))
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Decimal
                    )
                )
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = state.restingTimeValue,
                    onValueChange = {
                        onChangeRestingTime(it)
                    },
                    label = {
                        Text(text = stringResource(R.string.label_input_text_resting_time))
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Decimal
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { focusManager.clearFocus() }
                    )
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
fun SetFlowScreenPreview() {
    val exercisesSampleList = listOf(
        ExerciseView(
            id = 4,
            name = "Agachamento com barra livre",
            favoriteIcon = R.drawable.ic_star_filled,
            muscleGroups = listOf(
                R.string.quadriceps,
                R.string.hamstrings,
                R.string.abdomen,
                R.string.adductors
            ),
            urlLink = "www.youtube.com.br",
            videoId = "123344",
            isEditable = true
        ),
        ExerciseView(
            id = 1,
            name = "Bíceps Concentrado",
            favoriteIcon = R.drawable.ic_star_filled,
            muscleGroups = listOf(R.string.biceps),
            urlLink = null,
            videoId = null,
            isEditable = true
        ),
        ExerciseView(
            id = 2,
            name = "Tríceps pulley",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.triceps),
            urlLink = null,
            videoId = null,
            isEditable = true
        ),
        ExerciseView(
            id = 3,
            name = "Supino inclinado",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.chest, R.string.triceps),
            urlLink = null,
            videoId = null,
            isEditable = true
        ),
    )
    ZeMarombaTheme {
        ExerciseDetailsScreen(
            state = CreateExerciseState(
                exercisesList = exercisesSampleList,
                selectedExercise = ExerciseView(
                    id = 1,
                    name = "Bíceps concentrado",
                    favoriteIcon = R.drawable.ic_star_filled,
                    muscleGroups = listOf(
                        MuscleGroup.BICEPS.nameRes,
                        MuscleGroup.FOREARM.nameRes
                    ),
                    urlLink = null,
                    videoId = null,
                    isEditable = false
                )
            ),
            onNavigateBack = {

            },
            onChangeSeries = {

            },
            onChangeRepetition = {

            },
            onChangeRestingTime = {

            },
            onChangeWeight = {

            }
        )
    }
}
