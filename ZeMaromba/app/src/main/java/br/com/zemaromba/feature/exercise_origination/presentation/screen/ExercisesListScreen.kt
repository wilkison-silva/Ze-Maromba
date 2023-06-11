package br.com.zemaromba.feature.exercise_origination.presentation.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.components.chips.FilterChipsGroup
import br.com.zemaromba.core_ui.components.search_bar.SearchBar
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesListscreen(
    onNavigateBack: () -> Unit,
    onNavigateToNewExercise: () -> Unit
) {

    val verticalScrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 20.dp, end = 20.dp),
                        onClick = {
                            onNavigateBack()
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_back),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    )
                },
                title = {
                    Text(
                        text = "Exercícios",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
        }
    }

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.surface)
//            .verticalScroll(state = verticalScrollState)
//    ) {
//        Surface(
//            tonalElevation = 5.dp,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//        ) {
//            Row(
//                modifier = Modifier.padding(all = 20.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = buildAnnotatedString {
//                        withStyle(
//                            style = SpanStyle(
//                                fontWeight = FontWeight.Light,
//                            )
//                        ) {
//                            append("Olá, ")
//                        }
//                        withStyle(
//                            style = SpanStyle(
//                                fontWeight = FontWeight.Bold,
//                            )
//                        ) {
//                            append("$userName!")
//                        }
//                    },
//                    color = MaterialTheme.colorScheme.onSurface,
//                    fontSize = 28.sp,
//                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Start
//                )
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_account_profile_picture),
//                    contentDescription = "",
//                    tint = MaterialTheme.colorScheme.onSurface
//                )
//            }
//        }
//        Spacer(modifier = Modifier.height(20.dp))
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(MaterialTheme.colorScheme.background),
//            horizontalAlignment = Alignment.Start
//        ) {
//            MenuCardItem(
//                icon = R.drawable.ic_training_plan_calendar,
//                title = "Meus Treinos",
//                description = "Personalize seus treinos de acordo com suas necessidades",
//                onClick = {
//
//                }
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            MenuCardItem(
//                icon = R.drawable.ic_dumbell,
//                title = "Exercícios",
//                description = "Exercícios para compor seus treinos da sua maneira",
//                onClick = {
//
//                }
//            )
//        }
//    }
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
fun ExercisesListscreenPreview() {
    ZeMarombaTheme {
        ExercisesListscreen(
            onNavigateBack = {

            },
            onNavigateToNewExercise = {

            }
        )
    }
}