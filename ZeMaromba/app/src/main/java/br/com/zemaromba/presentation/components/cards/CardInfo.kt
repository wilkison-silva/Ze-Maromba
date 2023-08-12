package br.com.zemaromba.presentation.components.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme

@Composable
fun CardInfo(
    modifier: Modifier,
    @DrawableRes icon: Int,
    message: String,
    borderColor: Color,
    surfaceColor: Color,
    onSurfaceColor: Color,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = surfaceColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeightIn(min = 0.dp, max = 100.dp)
                .padding(end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(5.dp),
                color = borderColor
            ) {

            }
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = "",
                tint = onSurfaceColor
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 5.dp),
                text = message,
                color = onSurfaceColor,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardInfoPreview() {
    ZeMarombaTheme {
        CardInfo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            icon = R.drawable.ic_training_plan_calendar,
            message = "Planeja seus treinos melhor com a gente, assim você vai ir mais longe e ganhar mais massa magra",
            borderColor = MaterialTheme.colorScheme.tertiary,
            surfaceColor = MaterialTheme.colorScheme.tertiaryContainer,
            onSurfaceColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}