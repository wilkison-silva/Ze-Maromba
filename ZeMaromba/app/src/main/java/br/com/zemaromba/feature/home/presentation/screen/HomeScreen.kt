package br.com.zemaromba.feature.home.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme

@Composable
fun HomeScreen(
    userName: String,
    trainingsPlansQuantity: Long = 10
) {

    val verticalScrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(state = verticalScrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(all = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Olá, $userName!",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_account_profile_picture),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp),
                text = "Meus Treinos",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .padding(start = 20.dp),
                text = "Personalize seus treinos",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(trainingsPlansQuantity.toInt()) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Card(
                        modifier = Modifier
                            .requiredSize(width = 200.dp, height = 100.dp)
                            .clickable {

                            },
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.15f)),
                        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.secondaryContainer)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                text = "Monstro do lala na academia",
                                lineHeight = 20.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }

                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "Criar novo treino",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp),
                text = "Meus exercícios",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier
                    .padding(start = 20.dp),
                text = "Crie seus próprios exercícios",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(trainingsPlansQuantity.toInt()) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Column(
                        modifier = Modifier
                            .requiredSize(width = 200.dp, height = 100.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(size = 10.dp)
                            )
                            .clickable {

                            },
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp),
                            text = "Monstro do lala na academia",
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedButton(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "Criar novo exercício",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
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
@Composable
fun HomeScreenPreviewPixel5() {
    ZeMarombaTheme {
        HomeScreen(
            userName = "Wilkison",
            trainingsPlansQuantity = 10
        )
    }
}

@Preview(
    name = "pixel_5",
    device = "spec:parent=pixel_5",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeScreenPreviewPixel5DarkMode() {
    ZeMarombaTheme {
        HomeScreen(
            userName = "Wilkison",
            trainingsPlansQuantity = 10
        )
    }
}

@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeScreenPreviewNexus4() {
    ZeMarombaTheme {
        HomeScreen(
            userName = "Wilkison",
            trainingsPlansQuantity = 10
        )
    }
}

@Preview(
    name = "Nexus 4",
    device = "id:Nexus 4",
    apiLevel = 33,
    showSystemUi = false,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun HomeScreenPreviewNexus4DarkMode() {
    ZeMarombaTheme {
        HomeScreen(
            userName = "Wilkison",
            trainingsPlansQuantity = 10
        )
    }
}