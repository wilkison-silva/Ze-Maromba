package br.com.zemaromba.presentation.features.user_configurations.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme
import br.com.zemaromba.presentation.features.user_configurations.screen.state.ThemeSelectionScreenState
import br.com.zemaromba.presentation.features.user_configurations.model.SelectableThemeItemView
import br.com.zemaromba.presentation.features.user_configurations.model.Theme

@Composable
fun ThemeSelectionScreen(
    state: ThemeSelectionScreenState,
    onNavigateBack: () -> Unit,
    onClickItem: (theme: Theme) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(
                navBarType = NavBarType.BACK_TITLE,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = null,
                onActionIconClick = null,
                title = stringResource(R.string.themes)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .padding(top = Dimens.Space.space_32dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = Dimens.Space.space_48dp),
            verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_20dp)
        ) {
            state.selectableThemeItems.forEach { item ->
                SelectableThemeItem(
                    selectableThemeItemView = item,
                    onClick = { theme: Theme ->
                        onClickItem(theme)
                    },
                )
            }
        }
    }
}

@Composable
private fun SelectableThemeItem(
    selectableThemeItemView: SelectableThemeItemView,
    onClick: (themeType: Theme) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = Dimens.Space.space_20dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .clickable {
                onClick(selectableThemeItemView.themeType)
            },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = if (selectableThemeItemView.isSelected) {
            BorderStroke(
                width = Dimens.Thickness.thickness_1dp,
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            BorderStroke(
                width = Dimens.Thickness.thickness_1dp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Dimens.Space.space_24dp,
                    top = Dimens.Space.space_24dp,
                    bottom = Dimens.Space.space_24dp,
                    end = Dimens.Space.space_12dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = selectableThemeItemView.name),
                color = MaterialTheme.colorScheme.onSurface,
                style = Styles.BodyTextBold
            )
            RadioButton(
                selected = selectableThemeItemView.isSelected,
                onClick = {
                    onClick(selectableThemeItemView.themeType)
                }
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
fun ThemeSelectionScreenPreview() {
    ZeMarombaTheme {
        ThemeSelectionScreen(
            state = ThemeSelectionScreenState(),
            onNavigateBack = {

            },
            onClickItem = {

            }
        )
    }
}