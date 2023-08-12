package br.com.zemaromba.presentation.exercises.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.cards.CardInfo
import br.com.zemaromba.presentation.components.chips.FilterChipsGroup
import br.com.zemaromba.presentation.components.search_bar.SearchBar
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.domain.model.ExerciseFilter
import br.com.zemaromba.presentation.model.ExerciseView
import br.com.zemaromba.presentation.exercises.viewmodel.ExercisesListState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesListScreen(
    state: ExercisesListState,
    onNavigateBack: () -> Unit,
    onNavigateToNewExercise: () -> Unit,
    onOpenExercise: (exerciseId: Long) -> Unit,
    onFavoriteExercise: (exerciseId: Long, icon: Int) -> Unit,
    onSearch: (exerciseName: String) -> Unit,
    onFilterChange: (exerciseFilter: ExerciseFilter) -> Unit,
    onApplySelectedMuscleGroups: () -> Unit,
    onMuscleGroupSelection: (id: Int, isSelected: Boolean) -> Unit,
    onOpenYoutubeApp: (videoId: String) -> Unit
) {

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
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                },
                title = {
                    Text(
                        text = stringResource(R.string.title_exercises),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 20.sp
                    )
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(bottom = 10.dp),
                onClick = {
                    onNavigateToNewExercise()
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = stringResource(R.string.fab_new_exercise))
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                state = state.searchBarState,
                onTextChange = {
                    onSearch(it)
                }
            )
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(R.string.filter_by),
                color = MaterialTheme.colorScheme.onSurface
            )
            FilterChipsGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                exerciseFilters = state.exerciseFilters,
                onSelected = { exerciseFilter ->
                    onFilterChange(exerciseFilter)
                }
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                thickness = 1.dp
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
            ) {
                if (state.showNothingFound) {
                    item {
                        CardInfo(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
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
                            ExerciseCardItem(
                                exerciseName = exerciseView.name,
                                muscleGroups = exerciseView.muscleGroups.map { muscleNameResource ->
                                    stringResource(id = muscleNameResource)
                                }.joinToString(separator = ", "),
                                videoId = exerciseView.videoId,
                                favoriteIcon = exerciseView.favoriteIcon,
                                onClick = {
                                    onOpenExercise(exerciseView.id)
                                },
                                onFavoriteClick = {
                                    onFavoriteExercise(exerciseView.id, exerciseView.favoriteIcon)
                                },
                                onOpenDemonstrationVideo = { videoId ->
                                    onOpenYoutubeApp(videoId)
                                }
                            )
                        }
                    )
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }

    if (state.showMuscleGroupBottomSheet) {
        val skipPartiallyExpanded = remember { mutableStateOf(true) }
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded.value
        )

        ModalBottomSheet(
            onDismissRequest = {
                onApplySelectedMuscleGroups()
            },
            sheetState = bottomSheetState,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.4f),
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onApplySelectedMuscleGroups()
                            }
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.button_title_filter),
                        fontSize = 14.sp
                    )
                }
            }
            MuscleGroupSelector(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 40.dp),
                muscleGroupCheckBoxStateList = state.muscleGroupCheckBoxStates,
                onMuscleGroupSelected = { index, isSelected ->
                    onMuscleGroupSelection(index, isSelected)
                }
            )
        }
    }
}

@Composable
fun ExerciseCardItem(
    exerciseName: String,
    muscleGroups: String,
    videoId: String?,
    favoriteIcon: Int,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onOpenDemonstrationVideo: (videoId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .clickable {
                onClick()
            },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Box {
            Column(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, top = 20.dp, bottom = 10.dp, end = 60.dp),
                    text = exerciseName,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 60.dp, bottom = 20.dp),
                    text = muscleGroups,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic
                )
                videoId?.let { videoIdOnYoutube ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 60.dp, top = 20.dp),
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.ic_play_video_youtube),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                                .clickable {
                                    onOpenDemonstrationVideo(videoIdOnYoutube)
                                },
                            text = stringResource(R.string.exercise_demonstration_video),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp),
                onClick = {
                    onFavoriteClick()
                }
            ) {
                Icon(
                    painter = painterResource(id = favoriteIcon),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
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
fun ExercisesListScreenPreview() {
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
            videoId = null
        ),
        ExerciseView(
            id = 1,
            name = "Bíceps Concentrado",
            favoriteIcon = R.drawable.ic_star_filled,
            muscleGroups = listOf(R.string.biceps),
            urlLink = null,
            videoId = null
        ),
        ExerciseView(
            id = 2,
            name = "Tríceps pulley",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.triceps),
            urlLink = null,
            videoId = null
        ),
        ExerciseView(
            id = 3,
            name = "Supino inclinado",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.chest, R.string.triceps),
            urlLink = null,
            videoId = null
        ),
    )
    ZeMarombaTheme {
        ExercisesListScreen(
            state = ExercisesListState(exercisesList = exercisesSampleList),
            onNavigateBack = {

            },
            onNavigateToNewExercise = {

            },
            onOpenExercise = {

            },
            onFavoriteExercise = { _, _ ->

            },
            onSearch = {

            },
            onFilterChange = {

            },
            onApplySelectedMuscleGroups = {

            },
            onMuscleGroupSelection = { _, _ ->

            },
            onOpenYoutubeApp = {

            }
        )
    }
}