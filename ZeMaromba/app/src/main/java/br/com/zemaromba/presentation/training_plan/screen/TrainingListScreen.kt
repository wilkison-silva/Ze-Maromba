package br.com.zemaromba.presentation.training_plan.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.domain.model.MuscleGroup
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.core_ui.ui.theme.Spacing
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.model.TrainingSummaryView
import br.com.zemaromba.presentation.training_plan.screen.state.TrainingListState

@Composable
fun TrainingListScreen(
    state: TrainingListState,
    onNavigateBack: () -> Unit,
    onOpenTraining: (trainingId: Long) -> Unit,
    onCreateTraining: () -> Unit,
    onOpenSettings: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(
                navBarType = NavBarType.BACK_TITLE_ACTION,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = R.drawable.ic_settings,
                onActionIconClick = { onOpenSettings() },
                title = state.trainingPlanName
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(bottom = Spacing.space_12dp),
                onClick = {
                    onCreateTraining()
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(Spacing.space_4dp))
                Text(text = stringResource(R.string.fab_new_training))
            }
        },
    ) { contentPadding ->
        if (state.showMessage) {
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(Spacing.space_200dp),
                        painter = painterResource(id = R.drawable.ic_training_list),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(Spacing.space_20dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.space_64dp),
                        text = stringResource(id = R.string.how_about_create_your_first_training),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Spacing.space_12dp),
                modifier = Modifier.padding(paddingValues = contentPadding)
            ) {
                item {
                    Spacer(modifier = Modifier.height(Spacing.space_20dp))
                }
                itemsIndexed(
                    items = state.trainingSummaryViewList,
                    itemContent = { _: Int, trainingSummaryView: TrainingSummaryView ->
                        TrainingCardItem(
                            trainingSummaryView = trainingSummaryView,
                            onClick = {
                                onOpenTraining(trainingSummaryView.id)
                            }
                        )
                    }
                )
                item {
                    Spacer(modifier = Modifier.height(Spacing.space_96dp))
                }
            }
        }
    }
}

@Composable
fun TrainingCardItem(
    trainingSummaryView: TrainingSummaryView, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.space_20dp)
            .clickable {
                onClick()
            },
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
                    .padding(all = Spacing.space_20dp),
                text = trainingSummaryView.name,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Spacing.space_20dp,
                        bottom = Spacing.space_20dp,
                        end = Spacing.space_20dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(Spacing.space_16dp),
                    painter = painterResource(id = R.drawable.ic_dumbell),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(Spacing.space_20dp))
                Text(
                    text = if (trainingSummaryView.hasExercises) {
                        stringResource(
                            id = R.string.training_summary_card_exercises_quantity,
                            trainingSummaryView.exercisesQuantity
                        )
                    } else {
                        stringResource(id = R.string.training_summary_card_exercises_none)
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Italic,
                )
            }
            if (trainingSummaryView.hasExercises) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Spacing.space_20dp,
                            bottom = Spacing.space_20dp,
                            end = Spacing.space_20dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(Spacing.space_16dp),
                        painter = painterResource(id = R.drawable.ic_muscle_groups_chest),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(Spacing.space_20dp))
                    Text(
                        text = stringResource(R.string.training_card_muscle_groups_of) + trainingSummaryView.muscleGroups.map {
                            stringResource(id = it.nameRes)
                        }.joinToString(separator = ", "),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Italic
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Spacing.space_20dp,
                            bottom = Spacing.space_20dp,
                            end = Spacing.space_20dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                            .border(
                                width = Spacing.space_1dp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                shape = CircleShape
                            )
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = CircleShape
                            )
                            .padding(
                                horizontal = Spacing.space_16dp,
                                vertical = Spacing.space_8dp
                            )
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.training_summary_card_percentage_done,
                                trainingSummaryView.percentageDone
                            ),
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic
                        )
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
fun TrainingListScreenPreview() {
    val trainingSampleList = listOf(
        TrainingSummaryView(
            id = 1, name = "Treino do dia 01", exercisesQuantity = 12, muscleGroups = listOf(
                MuscleGroup.BICEPS, MuscleGroup.CHEST, MuscleGroup.DORSAL, MuscleGroup.TRICEPS
            ), percentageDone = 60
        ),
    )
    ZeMarombaTheme {
        TrainingListScreen(state = TrainingListState(
            trainingSummaryViewList = trainingSampleList,
            trainingPlanName = "Destruidor de pernas"
        ), onNavigateBack = {

        }, onOpenTraining = {

        }, onCreateTraining = {

        }, onOpenSettings = {

        })
    }
}