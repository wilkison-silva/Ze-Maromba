package br.com.zemaromba.presentation.features.home.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.home.screen.state.HomeState
import br.com.zemaromba.presentation.model.MenuHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onNavigate: (menu: MenuHome) -> Unit
) {

    val verticalScrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                )
                            ) {
                                append(stringResource(R.string.title_hello))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("${state.userName}!")
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                        style = Styles.Title3Bold,
                        textAlign = TextAlign.Start
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            onNavigate(MenuHome.USER_CONFIGURATIONS)
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_account_picture),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    )
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .verticalScroll(state = verticalScrollState)
        ) {
            Spacer(modifier = Modifier.height(Dimens.Space.space_20dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.Start
            ) {
                MenuHomeItem(
                    icon = R.drawable.ic_training_plan_calendar,
                    title = stringResource(R.string.my_training_plans),
                    description = stringResource(R.string.customized_training_plans),
                    onClick = {
                        onNavigate(MenuHome.TRAINING_PLAN_SCREEN)
                    }
                )
                Spacer(modifier = Modifier.height(Dimens.Space.space_12dp))
                MenuHomeItem(
                    icon = R.drawable.ic_dumbell,
                    title = stringResource(R.string.exercises),
                    description = stringResource(R.string.message_create_your_own_exercises),
                    onClick = {
                        onNavigate(MenuHome.EXERCISES_SCREEN)
                    }
                )
                Spacer(modifier = Modifier.height(Dimens.Space.space_12dp))
                FutureFeature(
                    icon = R.drawable.ic_graph,
                    title = stringResource(R.string.title_historics),
                    description = stringResource(R.string.description_historic_feature)
                )
            }
        }
    }
}

@Composable
fun MenuHomeItem(
    icon: Int,
    title: String,
    description: String,
    onClick: () -> Unit = {}
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
        )
    ) {
        Box {
            Column(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_12dp)
            ) {
                Row(
                    modifier = Modifier.padding(
                        start = Dimens.Space.space_20dp,
                        top = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_8dp,
                        end = Dimens.Space.space_20dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(Dimens.Space.space_28dp),
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(Dimens.Space.space_20dp))
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = Styles.Title3Bold
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.Space.space_20dp,
                            end = Dimens.Space.space_64dp,
                            bottom = Dimens.Space.space_20dp
                        ),
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = Styles.CaptionNormal
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = Dimens.Space.space_16dp)
            )
        }
    }
}

@Composable
fun FutureFeature(
    icon: Int,
    title: String,
    description: String,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = Dimens.Space.space_20dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_12dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        start = Dimens.Space.space_20dp,
                        top = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_8dp,
                        end = Dimens.Space.space_20dp
                    ).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        modifier = Modifier.size(Dimens.Space.space_28dp),
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(Dimens.Space.space_20dp))
                    Text(
                        text = title,
                        style = Styles.Title3Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(horizontal = Dimens.Space.space_12dp, vertical = Dimens.Space.space_4dp),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Space.space_8dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(Dimens.Space.space_16dp),
                        painter = painterResource(id = R.drawable.ic_computer_terminal),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = stringResource(R.string.coming_soon),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = Styles.CaptionBold
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = Dimens.Space.space_20dp,
                        end = Dimens.Space.space_64dp,
                        bottom = Dimens.Space.space_20dp
                    ),
                text = description,
                style = Styles.CaptionNormal
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
fun HomeScreenPreview() {
    ZeMarombaTheme {
        HomeScreen(
            state = HomeState(userName = "Wilkison"),
            onNavigate = {

            }
        )
    }
}