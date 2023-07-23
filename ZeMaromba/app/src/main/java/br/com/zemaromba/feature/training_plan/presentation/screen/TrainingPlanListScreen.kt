package br.com.zemaromba.feature.training_plan.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.home.presentation.model.TrainingPlanView
import br.com.zemaromba.feature.training_plan.presentation.viewmodel.TrainingPlanState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingPlanListScreen(
    state: TrainingPlanState,
    onNavigateBack: () -> Unit,
    onOpenTrainingPlan: (trainingPlanId: Long) -> Unit,
    onCreateTrainingPlan: () -> Unit
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
                        text = stringResource(R.string.my_training_plans),
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
                    onCreateTrainingPlan()
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = stringResource(R.string.fab_new_training_plan))
            }
        },
    ) { contentPadding ->
        if (state.showMessage) {
            Box(modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
            ) {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 150.dp)
                        .align(Alignment.Center)
                        .size(200.dp),
                    painter = painterResource(id = R.drawable.ic_training_plans_not_found),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 60.dp, end = 60.dp, top = 150.dp)
                        .align(Alignment.Center),
                    text = stringResource(id = R.string.how_about_create_your_first_training_plan),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(paddingValues = contentPadding)
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                itemsIndexed(
                    items = state.trainingPlanList,
                    itemContent = { _: Int, trainingPlanView: TrainingPlanView ->
                        TrainingPlanCardItem(
                            trainingPlanName = trainingPlanView.name,
                            onClick = {
                                onOpenTrainingPlan(trainingPlanView.id)
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

@Composable
fun TrainingPlanCardItem(
    trainingPlanName: String,
    onClick: () -> Unit
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
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 60.dp, bottom = 60.dp, end = 20.dp),
                text = trainingPlanName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
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
fun ExercisesListScreenPreview() {
    val trainingPlanSampleList = listOf(
        TrainingPlanView(
            id = 1,
            name = "Monstrão em 60 dias",
        ),
        TrainingPlanView(
            id = 2,
            name = "Protocol Mass",
        ),
        TrainingPlanView(
            id = 3,
            name = "Sem dores no joelho!!!",
        ),
        TrainingPlanView(
            id = 1,
            name = "Monstrão em 60 dias",
        ),
        TrainingPlanView(
            id = 2,
            name = "Protocol Mass",
        ),
        TrainingPlanView(
            id = 3,
            name = "Sem dores no joelho!!!",
        ),
        TrainingPlanView(
            id = 1,
            name = "Monstrão em 60 dias",
        ),
        TrainingPlanView(
            id = 2,
            name = "Protocol Mass",
        ),
        TrainingPlanView(
            id = 3,
            name = "Sem dores no joelho!!!",
        ),
    )
    ZeMarombaTheme {
        TrainingPlanListScreen(
            state = TrainingPlanState(trainingPlanList = emptyList()),
            onNavigateBack = {

            },
            onOpenTrainingPlan = {

            },
            onCreateTrainingPlan = {

            }
        )
    }
}