package br.com.zemaromba.feature.exercise.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import br.com.zemaromba.core_ui.components.chips.FilterChipsGroup
import br.com.zemaromba.core_ui.components.search_bar.SearchBar
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.exercise.presentation.model.ExerciseView
import br.com.zemaromba.feature.exercise.presentation.viewmodel.ExercisesListState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesListScreen(
    state: ExercisesListState,
    onNavigateBack: () -> Unit,
    onNavigateToNewExercise: () -> Unit,
    onOpenExercise: (exerciseId: Long) -> Unit,
    onFavoriteExercise: (exerciseId: Long, icon: Int) -> Unit
) {

    val verticalScrollState = rememberScrollState()

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
                        text = "Exercícios",
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
                Text(text = "Novo exercício")
            }
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
                .verticalScroll(verticalScrollState)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                hint = "Ex: Bíceps na polia",
                onTextChanged = {

                }
            )
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = "Filtrar por:",
                color = MaterialTheme.colorScheme.onSurface
            )

            val chipsTitle = remember {
                mutableStateOf(listOf("Todos", "Grupo muscular", "Favoritos"))
            }
            val selectedChips = remember {
                mutableStateOf(listOf(true, false, false))
            }
            FilterChipsGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                chips = chipsTitle.value,
                selectedChips = selectedChips.value,
                onSelected = {
                    selectedChips.value =
                        selectedChips
                            .value
                            .map { false }
                            .toMutableList()
                            .apply {
                                this[it] = !this[it]
                            }
                }
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                thickness = 2.dp
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(bottom = 100.dp)
            ) {
                state.exercisesList.forEach {
                    ExerciseCardItem(
                        exerciseName = it.name,
                        muscleGroups = it.muscleGroups.map { muscleNameResource ->
                            stringResource(id = muscleNameResource)
                        }.joinToString(separator = ", "),
                        favoriteIcon = it.favoriteIcon,
                        onClick = {
                            onOpenExercise(it.id)
                        },
                        onFavoriteClick = {
                            onFavoriteExercise(it.id, it.favoriteIcon)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseCardItem(
    exerciseName: String,
    muscleGroups: String,
    favoriteIcon: Int,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
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
                Row(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 20.dp,
                        bottom = 10.dp,
                        end = 20.dp
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 60.dp),
                        text = exerciseName,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
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
            id = 1,
            name = "Bíceps Concentrado",
            favoriteIcon = R.drawable.ic_star_filled,
            muscleGroups = listOf(R.string.biceps)
        ),
        ExerciseView(
            id = 2,
            name = "Tríceps pulley",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.triceps)
        ),
        ExerciseView(
            id = 3,
            name = "Supino inclinado",
            favoriteIcon = R.drawable.ic_star_border,
            muscleGroups = listOf(R.string.chest, R.string.triceps)
        ),
        ExerciseView(
            id = 4,
            name = "Agachamento com barra livre",
            favoriteIcon = R.drawable.ic_star_filled,
            muscleGroups = listOf(
                R.string.quadriceps,
                R.string.hamstrings,
                R.string.abdomen,
                R.string.adductors
            )
        )
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

            }
        )
    }
}