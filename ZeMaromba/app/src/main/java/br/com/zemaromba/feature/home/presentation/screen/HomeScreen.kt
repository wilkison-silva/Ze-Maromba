package br.com.zemaromba.feature.home.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.feature.home.presentation.model.MenuHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userName: String,
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
                                append("Olá, ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("$userName!")
                            }
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start
                    )
                },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = {

                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_account_picture),
                                contentDescription = "",
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
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.Start
            ) {
                MenuCardItem(
                    icon = R.drawable.ic_training_plan_calendar,
                    title = "Meus Treinos",
                    description = "Personalize seus treinos de acordo com suas necessidades",
                    onClick = {

                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                MenuCardItem(
                    icon = R.drawable.ic_dumbell,
                    title = "Exercícios",
                    description = "Exercícios para compor seus treinos da sua maneira",
                    onClick = {
                        onNavigate(MenuHome.EXERCISES)
                    }
                )
            }
        }
    }
}

@Composable
fun MenuCardItem(
    icon: Int,
    title: String,
    description: String,
    onClick: () -> Unit = {}
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
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = title,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 60.dp, bottom = 20.dp),
                    text = description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp)
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
            userName = "Wilkison",
            onNavigate = {

            }
        )
    }
}