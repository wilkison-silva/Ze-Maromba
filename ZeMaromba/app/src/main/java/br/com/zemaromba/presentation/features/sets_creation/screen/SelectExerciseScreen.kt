package br.com.zemaromba.presentation.features.sets_creation.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.BuildConfig
import br.com.zemaromba.R
import br.com.zemaromba.common.extensions.orZero
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.presentation.components.ads.AdmobBanner
import br.com.zemaromba.presentation.components.bottom_sheet.MuscleGroupSelectorBottomSheet
import br.com.zemaromba.presentation.components.button.PrimaryButton
import br.com.zemaromba.presentation.components.cards.CardInfo
import br.com.zemaromba.presentation.components.chips.FilterChipsGroup
import br.com.zemaromba.presentation.components.loaders.SimpleLoader
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.components.search_bar.SearchBar
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.sets_creation.screen.state.CreateExerciseState
import br.com.zemaromba.presentation.features.sets_creation.screen.state.SelectExerciseScreenState
import br.com.zemaromba.presentation.features.exercises.model.ExerciseView

@Composable
fun SelectExerciseScreen(
    state: SelectExerciseScreenState,
    flowState: CreateExerciseState,
    onNavigateBack: () -> Unit,
    onNavigateForward: () -> Unit,
    onExerciseSelected: (exerciseId: Long) -> Unit,
    onSearch: (exerciseName: String) -> Unit,
    onFilterChange: (exerciseFilter: ExerciseFilter) -> Unit,
    onApplySelectedMuscleGroups: () -> Unit,
    onMuscleGroupSelection: (id: Int, isSelected: Boolean) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(
                navBarType = NavBarType.BACK_TITLE,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = null,
                onActionIconClick = null,
                title = stringResource(id = R.string.title_select_exercise),
                hasProgressBar = true,
                progressBarInitial = flowState.progressBarInitial,
                progressBarTarget = flowState.progressBarTarget
            )
        },
        bottomBar = {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Dimens.Space.space_20dp),
                onClick = { onNavigateForward() },
                title = stringResource(id = R.string.next),
                isEnabled = state.selectedExercise != null
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
        ) {
            if (state.isLoading) {
                SimpleLoader(
                    modifier = Modifier
                        .padding(top = Dimens.Space.space_48dp)
                        .fillMaxSize()
                    ,
                    message = stringResource(R.string.message_loading_content)
                )
            } else {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = Dimens.Space.space_20dp),
                    state = state.searchBarState,
                    onTextChange = {
                        onSearch(it)
                    }
                )
                AdmobBanner(
                    modifier = Modifier.fillMaxWidth(),
                    bannerUnitId = BuildConfig.BANNER_SELECT_EXERCISE_FOR_SET_CREATION
                )
                Text(
                    modifier = Modifier
                        .padding(
                            start = Dimens.Space.space_20dp,
                            top = Dimens.Space.space_8dp
                        ),
                    text = stringResource(R.string.filter_by),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = Styles.BodyTextNormal
                )
                FilterChipsGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimens.Space.space_20dp),
                    exerciseFilters = state.exerciseFilters,
                    onSelected = { exerciseFilter ->
                        onFilterChange(exerciseFilter)
                    }
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.Space.space_20dp,
                            vertical = Dimens.Space.space_12dp
                        ),
                    thickness = Dimens.Thickness.thickness_0dp
                )

                val lazyListState = rememberLazyListState()
                LaunchedEffect(key1 = state.scrollPosition) {
                    if (state.scrollPosition > 0) {
                        lazyListState.scrollToItem(index = state.scrollPosition)
                    }
                }
                LazyColumn(
                    state = lazyListState,
                    verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_8dp),
                    modifier = Modifier
                ) {
                    if (state.showNothingFound) {
                        item {
                            CardInfo(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Dimens.Space.space_20dp),
                                icon = R.drawable.ic_warning,
                                message = stringResource(R.string.card_info_found_no_exercises),
                                borderColor = MaterialTheme.colorScheme.secondary,
                                surfaceColor = MaterialTheme.colorScheme.secondaryContainer,
                                onSurfaceColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    } else {
                        itemsIndexed(
                            items = state.exercisesList,
                            itemContent = { _: Int, exerciseView: ExerciseView ->
                                SelectableExerciseCardItem(
                                    exerciseName = exerciseView.name,
                                    muscleGroups = exerciseView.muscleGroups.map { muscleNameResource ->
                                        stringResource(id = muscleNameResource)
                                    }.joinToString(separator = ", "),
                                    favoriteIcon = exerciseView.favoriteIcon,
                                    onClick = {
                                        onExerciseSelected(exerciseView.id)
                                    },
                                    isSelected = exerciseView.id == state.selectedExercise?.id.orZero()
                                )
                            }
                        )
                        item {
                            Spacer(modifier = Modifier.height(Dimens.Space.space_96dp))
                        }
                    }
                }
                if (state.showMuscleGroupBottomSheet) {
                    MuscleGroupSelectorBottomSheet(
                        onApplySelectedMuscleGroups = { onApplySelectedMuscleGroups() },
                        onMuscleGroupSelection = { index: Int, isSelected: Boolean ->
                            onMuscleGroupSelection(index, isSelected)
                        },
                        muscleGroupCheckBoxStates = state.muscleGroupCheckBoxStates
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableExerciseCardItem(
    exerciseName: String,
    muscleGroups: String,
    favoriteIcon: Int,
    onClick: () -> Unit,
    isSelected: Boolean
) {
    Card(
        modifier = Modifier
            .padding(horizontal = Dimens.Space.space_20dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = if (isSelected) {
            BorderStroke(
                width = Dimens.Thickness.thickness_1dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            null
        }
    ) {
        Box {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.Space.space_20dp,
                            top = Dimens.Space.space_20dp,
                            bottom = Dimens.Space.space_12dp,
                            end = Dimens.Space.space_64dp
                        ),
                    text = exerciseName,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Styles.BodyTextBold
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.Space.space_20dp,
                            end = Dimens.Space.space_64dp,
                            bottom = Dimens.Space.space_20dp
                        ),
                    text = muscleGroups,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Styles.CaptionNormal
                )
            }
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = Dimens.Space.space_24dp),
                painter = painterResource(id = favoriteIcon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
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
fun SelectExerciseScreenPreview() {
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
            mayExclude = true,
            isNativeFromApp = true
        ),
        ExerciseView(
            id = 1,
            name = "Bíceps Concentrado",
            favoriteIcon = R.drawable.ic_star_filled,
            muscleGroups = listOf(R.string.biceps),
            urlLink = null,
            mayExclude = true,
            isNativeFromApp = true
        ),
        ExerciseView(
            id = 2,
            name = "Tríceps pulley",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.triceps),
            urlLink = null,
            mayExclude = true,
            isNativeFromApp = true
        ),
        ExerciseView(
            id = 3,
            name = "Supino inclinado",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.chest, R.string.triceps),
            urlLink = null,
            mayExclude = true,
            isNativeFromApp = true
        ),
    )
    ZeMarombaTheme {
        SelectExerciseScreen(
            state = SelectExerciseScreenState(exercisesList = exercisesSampleList),
            flowState = CreateExerciseState(),
            onNavigateBack = {

            },
            onNavigateForward = {

            },
            onExerciseSelected = {

            },
            onSearch = {

            },
            onFilterChange = {

            },
            onApplySelectedMuscleGroups = {

            },
            onMuscleGroupSelection = { _, _ ->

            }
        )
    }
}
