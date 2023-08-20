package br.com.zemaromba.presentation.training_plan.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.core_ui.ui.theme.Spacing
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.model.TrainingPlanView
import br.com.zemaromba.presentation.training_plan.screen.state.TrainingPlanState

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
            NavBar(
                navBarType = NavBarType.BACK_TITLE,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = null,
                onActionIconClick = null,
                title = stringResource(R.string.my_training_plans),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier.padding(bottom = Spacing.space_12dp),
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
                Spacer(modifier = Modifier.width(Spacing.space_4dp))
                Text(text = stringResource(R.string.fab_new_training_plan))
            }
        },
    ) { contentPadding ->
        if (state.showMessage) {
            Box(modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(Spacing.space_200dp),
                        painter = painterResource(id = R.drawable.ic_training_plans_not_found),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Spacing.space_64dp),
                        text = stringResource(id = R.string.how_about_create_your_first_training_plan),
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
                    Spacer(modifier = Modifier.height(Spacing.space_96dp))
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
        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(
                        horizontal = Spacing.space_20dp,
                        vertical = Spacing.space_64dp
                    ),
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
fun TrainingPlanListScreenPreview() {
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
            state = TrainingPlanState(trainingPlanList = trainingPlanSampleList),
            onNavigateBack = {

            },
            onOpenTrainingPlan = {

            },
            onCreateTrainingPlan = {

            }
        )
    }
}