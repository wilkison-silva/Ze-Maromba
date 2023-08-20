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
import br.com.zemaromba.R
import br.com.zemaromba.presentation.core_ui.ui.theme.Spacing
import br.com.zemaromba.presentation.core_ui.ui.theme.Style
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
                .requiredHeightIn(
                    min = Spacing.space_0dp,
                    max = Spacing.space_96dp
                )
                .padding(end = Spacing.space_20dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(Spacing.space_4dp),
                color = borderColor,
                content = {

                }
            )
            Spacer(modifier = Modifier.width(Spacing.space_20dp))
            Icon(
                modifier = Modifier.size(Spacing.space_24dp),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = onSurfaceColor
            )
            Spacer(modifier = Modifier.width(Spacing.space_20dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.space_4dp),
                text = message,
                color = onSurfaceColor,
                style = Style.Body1
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
                .padding(Spacing.space_12dp),
            icon = R.drawable.ic_training_plan_calendar,
            message = "Planeja seus treinos melhor com a gente, assim você vai ir mais longe e ganhar mais massa magra",
            borderColor = MaterialTheme.colorScheme.tertiary,
            surfaceColor = MaterialTheme.colorScheme.tertiaryContainer,
            onSurfaceColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}