package br.com.zemaromba.feature.home.presentation.screen

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.core_ui.ui.theme.Black20
import br.com.zemaromba.core_ui.ui.theme.Green80
import br.com.zemaromba.core_ui.ui.theme.WhiteF5

@Composable
fun HomeScreen(
    userName: String,
    trainingsPlansQuantity: Long = 10
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteF5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Green80)
                .padding(all = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
            ) {
                Text(
                    text = "Olá, $userName!",
                    lineHeight = 40.sp,
                    color = WhiteF5,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp),
                text = "Seus Treinos",
                lineHeight = 40.sp,
                color = Black20,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                items(trainingsPlansQuantity.toInt()) {
                    Column(
                        modifier = Modifier
                            .requiredSize(width = 200.dp, height = 150.dp)
                            .background(
                                color = Color.Gray,
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
                            color = Black20,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp),
                text = "Seus Exercícios",
                lineHeight = 40.sp,
                color = Black20,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                items(trainingsPlansQuantity.toInt()) {
                    Column(
                        modifier = Modifier
                            .requiredSize(width = 200.dp, height = 150.dp)
                            .background(
                                color = Color.Gray,
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
                            color = Black20,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
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
@Composable
fun HomeScreenPreviewPixel5() {
    HomeScreen(
        userName = "Wilkison",
        trainingsPlansQuantity = 10
    )
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
    HomeScreen(
        userName = "Wilkison",
        trainingsPlansQuantity = 10
    )
}