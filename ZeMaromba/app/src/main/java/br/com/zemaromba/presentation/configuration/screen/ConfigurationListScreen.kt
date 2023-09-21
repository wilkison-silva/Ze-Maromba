package br.com.zemaromba.presentation.configuration.screen

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import br.com.zemaromba.BuildConfig
import br.com.zemaromba.R
import br.com.zemaromba.presentation.components.navbar.NavBar
import br.com.zemaromba.presentation.components.navbar.NavBarType
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme

@Composable
fun ConfigurationListScreen(
    onNavigateBack: () -> Unit,
    onNavigateToUserAccountConfigs: () -> Unit,
    onNavigateToThemeConfigs: (exerciseId: Long) -> Unit,
    onNavigateToContacts: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NavBar(
                navBarType = NavBarType.BACK_TITLE,
                onBackIconClick = { onNavigateBack() },
                actionIconResId = null,
                onActionIconClick = null,
                title = stringResource(R.string.title_configurations)
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = contentPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            ConfigurationItem(
                iconRes = R.drawable.ic_account_picture,
                iconSize = Dimens.Space.space_32dp,
                title = R.string.config_menu_item_profile_title,
                subtitle = R.string.config_menu_item_profile_subtitle,
                onClick = {

                }
            )
            ConfigurationItem(
                iconRes = R.drawable.ic_constrast_color,
                title = R.string.config_menu_item_theme_title,
                subtitle = R.string.config_menu_item_theme_subtitle,
                onClick = {

                }
            )
            ConfigurationItem(
                iconRes = R.drawable.ic_ballon_chat,
                title = R.string.config_menu_item_contact_title,
                subtitle = R.string.config_menu_item_contact_subtitle,
                onClick = {

                }
            )
            VersionItem(version = BuildConfig.VERSION_NAME)
        }
    }
}

@Composable
fun ConfigurationItem(
    @DrawableRes iconRes: Int,
    iconSize: Dp = Dimens.Space.space_28dp,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(Dimens.Space.space_108dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = Dimens.Space.space_20dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.Space.space_20dp)
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_8dp)
        ) {
            Text(
                text = stringResource(id = title),
                style = Styles.Title4Bold
            )
            Text(
                text = stringResource(id = subtitle),
                style = Styles.Title5Normal
            )
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = Dimens.Thickness.thickness_1dp
    )
}

@Composable
fun VersionItem(
    version: String
) {
    Column(
        modifier = Modifier
            .padding(
                top = Dimens.Space.space_32dp,
                start = Dimens.Space.space_20dp,
                end = Dimens.Space.space_20dp,
                bottom = Dimens.Space.space_32dp
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimens.Space.space_8dp)
    ) {
        Text(
            text = stringResource(R.string.app_version),
            style = Styles.Title4Bold
        )
        Text(
            text = stringResource(R.string.version, version),
            style = Styles.Title5Normal
        )
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
fun ExercisesListScreenPreview() {
    ZeMarombaTheme {
        ConfigurationListScreen(
            onNavigateBack = {

            },
            onNavigateToUserAccountConfigs = {

            },
            onNavigateToThemeConfigs = {

            },
            onNavigateToContacts = {

            }
        )
    }
}