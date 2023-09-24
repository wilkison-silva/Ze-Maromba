package br.com.zemaromba.presentation.features.training_plan.screen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.presentation.components.bottom_sheet.ListOptionsBottomSheet
import br.com.zemaromba.presentation.components.button.PrimaryButton
import br.com.zemaromba.presentation.components.loaders.SimpleLoader
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.training_plan.screen.state.SetListState
import br.com.zemaromba.presentation.model.BottomSheetOption
import br.com.zemaromba.presentation.model.BottomSheetSetOptions
import br.com.zemaromba.presentation.model.ExerciseView
import br.com.zemaromba.presentation.model.SetView

@Composable
fun SetsListScreen(
    state: SetListState,
    onNavigateBack: () -> Unit,
    onCreateSet: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenYoutubeApp: (videoId: String) -> Unit,
    onCompleteSet: (setId: Long, isCompleted: Boolean) -> Unit,
    onShowListOptionsBottomSheet: (setId: Long) -> Unit,
    onHideListOptionsBottomSheet: () -> Unit,
    onEditSet: (setId: Long) -> Unit,
    onDeleteSet: (setId: Long) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(
                navBarType = NavBarType.BACK_TITLE_ACTION,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = R.drawable.ic_settings,
                onActionIconClick = { onOpenSettings() },
                title = state.trainingName
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Dimens.Space.space_20dp),
                onClick = { onCreateSet() },
                title = stringResource(R.string.add_exercise)
            )
        }
    ) { contentPadding ->
        if (state.isLoadingTraining || state.isRetrievingSets) {
            SimpleLoader(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(top = Dimens.Space.space_48dp)
                    .fillMaxSize(),
                message = stringResource(R.string.message_loading_content)
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.Center),
                    visible = state.showMessage,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(Dimens.Space.space_200dp),
                            painter = painterResource(id = R.drawable.ic_dumbell_wrist),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(Dimens.Space.space_20dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.Space.space_64dp),
                            text = stringResource(id = R.string.how_about_create_your_first_set),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = Styles.Title5Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier.fillMaxSize(),
                    visible = !state.showMessage,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_12dp),
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(Dimens.Space.space_24dp))
                        }
                        itemsIndexed(items = state.setListView,
                            itemContent = { _: Int, setView: SetView ->
                                SetCardItem(
                                    setView = setView,
                                    onLongClick = {
                                        onShowListOptionsBottomSheet(setView.id)
                                    },
                                    onOpenDemonstrationVideo = { videoId: String ->
                                        onOpenYoutubeApp(videoId)
                                    },
                                    onCompleteSet = { setId: Long, isCompleted: Boolean ->
                                        onCompleteSet(setId, isCompleted)
                                    }
                                )
                            }
                        )
                        item {
                            Spacer(modifier = Modifier.height(Dimens.Space.space_96dp))
                        }
                    }
                }
            }
        }
    }
    if (state.showListOptionsBottomSheet) {
        ListOptionsBottomSheet(
            title = state.selectedSet?.exerciseView?.name.orEmpty(),
            bottomSheetOptions = listOf(
                BottomSheetOption(
                    id = BottomSheetSetOptions.EDIT,
                    text = stringResource(R.string.bottom_sheet_options_edit),
                    iconRes = R.drawable.ic_edit

                ),
                BottomSheetOption(
                    id = BottomSheetSetOptions.DELETE,
                    text = stringResource(R.string.bottom_sheet_options_delete),
                    iconRes = R.drawable.ic_delete
                )
            ),
            onClickOptionItem = { bottomSheetSetOptions: BottomSheetSetOptions ->
                when (bottomSheetSetOptions) {
                    BottomSheetSetOptions.EDIT -> onEditSet(state.selectedSet?.id.orZero())
                    BottomSheetSetOptions.DELETE -> onDeleteSet(state.selectedSet?.id.orZero())
                }
            },
            onDismiss = {
                onHideListOptionsBottomSheet()
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SetCardItem(
    setView: SetView,
    onLongClick: () -> Unit,
    onOpenDemonstrationVideo: (videoId: String) -> Unit,
    onCompleteSet: (setId: Long, isCompleted: Boolean) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = Dimens.Space.space_20dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .combinedClickable(
                onClick = {

                },
                onLongClick = {
                    onLongClick()
                }
            ),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimens.Space.space_20dp,
                        top = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_28dp,
                        end = Dimens.Space.space_20dp
                    ),
                text = setView.exerciseView.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = Styles.Title3Bold,
                textAlign = TextAlign.Center
            )
            IconWithText(
                drawableRes = R.drawable.ic_repeat,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_series),
                    caption = setView.quantity.toString()
                ) + labelWithCaption(
                    label = stringResource(R.string.set_repetition),
                    caption = setView.repetitions.toString()
                )
            )
            IconWithText(
                drawableRes = R.drawable.ic_weight,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_weight),
                    caption = stringResource(R.string.weight, setView.weight)
                )
            )
            IconWithText(
                drawableRes = R.drawable.ic_stopwatch,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_resting_time),
                    caption = stringResource(R.string.seconds, setView.restingTime)
                )
            )
            IconWithText(
                drawableRes = R.drawable.ic_tag,
                labelAndDescription = labelWithCaption(
                    label = stringResource(R.string.set_observation),
                    caption = setView.observation
                )
            )
            setView.exerciseView.urlLink?.let { videoIdOnYoutube ->
                IconWithText(
                    drawableRes = R.drawable.ic_play_video_youtube,
                    labelAndDescription = AnnotatedString(text = stringResource(R.string.set_how_to_do_this_exercise)),
                    onClickText = {
                        onOpenDemonstrationVideo(videoIdOnYoutube)
                    }
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(all = Dimens.Space.space_20dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                onClick = {
                    onCompleteSet(setView.id, setView.completed)
                }
            ) {
                if (setView.completed) {
                    Icon(
                        modifier = Modifier.size(Dimens.Space.space_20dp),
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(Dimens.Space.space_8dp))
                }
                Text(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Styles.ButtonText2,
                    text = if (setView.completed) {
                        stringResource(R.string.set_completed)
                    } else {
                        stringResource(R.string.set_to_be_completed)
                    }
                )
            }
        }
    }
}

@Composable
fun IconWithText(
    @DrawableRes drawableRes: Int?,
    labelAndDescription: AnnotatedString
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.Space.space_20dp,
                bottom = Dimens.Space.space_20dp,
                end = Dimens.Space.space_20dp
            ),
        verticalAlignment = Alignment.Top
    ) {
        drawableRes?.let {
            Icon(
                modifier = Modifier
                    .padding(top = Dimens.Space.space_2dp)
                    .size(Dimens.Space.space_16dp),
                painter = painterResource(id = it),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(Dimens.Space.space_12dp))
        }
        Text(
            text = labelAndDescription,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = Styles.CaptionNormal,
        )
    }
}

@Composable
fun IconWithText(
    @DrawableRes drawableRes: Int?,
    labelAndDescription: AnnotatedString,
    onClickText: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(
                start = Dimens.Space.space_20dp,
                end = Dimens.Space.space_20dp,
                bottom = Dimens.Space.space_20dp
            )
            .clip(shape = MaterialTheme.shapes.extraSmall)
            .clickable {
                onClickText()
            }
            .padding(all = Dimens.Space.space_2dp),
        verticalAlignment = Alignment.Top
    ) {
        drawableRes?.let {
            Icon(
                modifier = Modifier
                    .padding(top = Dimens.Space.space_2dp)
                    .size(Dimens.Space.space_16dp),
                painter = painterResource(id = it),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(Dimens.Space.space_12dp))
        }
        Text(
            modifier = Modifier,
            text = labelAndDescription,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = Styles.CaptionNormal,
        )
    }
}

fun labelWithCaption(
    label: String,
    caption: String
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
            )
        ) {
            append("$label: ")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Medium,
            )
        ) {
            append(caption)
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
fun SetsListScreenPreview() {
    val setsSampleList = listOf(
        SetView(
            id = 0,
            quantity = 4,
            repetitions = 12,
            exerciseView = ExerciseView(
                id = 1,
                name = "Bíceps concentrado",
                favoriteIcon = R.drawable.ic_star_filled,
                muscleGroups = listOf(
                    MuscleGroup.BICEPS.nameRes,
                    MuscleGroup.FOREARM.nameRes
                ),
                urlLink = "12345667",
                mayExclude = true,
                isNativeFromApp = true
            ),
            weight = 12,
            observation = "Lorem ipsum dolor Bla bla What im doing here, urusai desu Primeira série com peso maximo depois drop-set",
            completed = false,
            restingTime = 60
        ),
        SetView(
            id = 0,
            quantity = 4,
            repetitions = 12,
            exerciseView = ExerciseView(
                id = 1,
                name = "Bíceps concentrado",
                favoriteIcon = R.drawable.ic_star_filled,
                muscleGroups = listOf(
                    MuscleGroup.BICEPS.nameRes,
                    MuscleGroup.FOREARM.nameRes
                ),
                urlLink = "12345667",
                mayExclude = true,
                isNativeFromApp = true
            ),
            weight = 12,
            observation = "Lorem ipsum dolor Bla bla What im doing here, urusai desu Primeira série com peso maximo depois drop-set",
            completed = false,
            restingTime = 60
        ),
        SetView(
            id = 0,
            quantity = 4,
            repetitions = 12,
            exerciseView = ExerciseView(
                id = 1,
                name = "Bíceps concentrado",
                favoriteIcon = R.drawable.ic_star_filled,
                muscleGroups = listOf(
                    MuscleGroup.BICEPS.nameRes,
                    MuscleGroup.FOREARM.nameRes
                ),
                urlLink = "12345667",
                mayExclude = true,
                isNativeFromApp = true
            ),
            weight = 12,
            observation = "Lorem ipsum dolor Bla bla What im doing here, urusai desu Primeira série com peso maximo depois drop-set",
            completed = false,
            restingTime = 60
        )
    )
    ZeMarombaTheme {
        SetsListScreen(
            state = SetListState(
                setListView = setsSampleList,
                trainingName = "Semana 09 - Dia 02"
            ),
            onNavigateBack = {

            },
            onCreateSet = {

            },
            onOpenSettings = {

            },
            onOpenYoutubeApp = {

            },
            onCompleteSet = { _, _ ->

            },
            onShowListOptionsBottomSheet = {

            },
            onHideListOptionsBottomSheet = {

            },
            onDeleteSet = {

            },
            onEditSet = {

            }
        )
    }
}