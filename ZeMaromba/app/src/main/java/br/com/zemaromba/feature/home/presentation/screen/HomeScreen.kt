package br.com.zemaromba.feature.home.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.trainingjourney.core_ui.ui.theme.Black20
import br.com.trainingjourney.core_ui.ui.theme.WhiteF5

@Composable
fun HomeScreen(
    userName: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteF5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black20),
            horizontalArrangement = Arrangement.Center
        ) {
            Column {
                Spacer(modifier = Modifier.height(100.dp))
                Text(
                    text = "Olá, $userName!",
                    lineHeight = 40.sp,
                    color = WhiteF5,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
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
@Composable
fun HomeScreenPreviewPixel5() {
    HomeScreen(
        userName = "Wilkison",
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
    )
}