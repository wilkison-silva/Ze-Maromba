package br.com.zemaromba.presentation.components.navbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import br.com.zemaromba.R
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavBar(
    navBarType: NavBarType,
    @DrawableRes backIconResId: Int = R.drawable.ic_arrow_back,
    onBackIconClick: (() -> Unit)?,
    @DrawableRes actionIconResId: Int?,
    onActionIconClick: (() -> Unit)?,
    title: String?,
) {
    when (navBarType) {
        NavBarType.TITLE,
        NavBarType.TITLE_ACTION,
        NavBarType.BACK_ACTION,
        NavBarType.BACK_TITLE-> {
            TopAppBar(
                navigationIcon = {
                    onBackIconClick?.let {
                        IconButton(
                            onClick = {
                                onBackIconClick()
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = backIconResId),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                },
                title = {
                    Text(
                        text = title.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = Styles.Title3Bold
                    )
                }
            )
        }

        NavBarType.BACK_TITLE_ACTION -> {
            MediumTopAppBar(
                navigationIcon = {
                    onBackIconClick?.let {
                        IconButton(
                            onClick = {
                                onBackIconClick()
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = backIconResId),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        )
                    }
                },
                title = {
                    Text(
                        modifier = Modifier.padding(start = Dimens.Space.space_12dp),
                        text = title.orEmpty(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = Styles.Title3Bold
                    )
                },
                actions = {
                    onActionIconClick?.let {
                        actionIconResId?.let { iconRes ->
                            IconButton(
                                onClick = {
                                    onActionIconClick()
                                },
                                content = {
                                    Icon(
                                        painter = painterResource(id = iconRes),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavBar(
    @DrawableRes backIconResId: Int = R.drawable.ic_arrow_back,
    onBackIconClick: (() -> Unit)?,
    actions: @Composable RowScope.() -> Unit,
    title: String?,
) {
    TopAppBar(
        navigationIcon = {
            onBackIconClick?.let {
                IconButton(
                    onClick = {
                        onBackIconClick()
                    },
                    content = {
                        Icon(
                            painter = painterResource(id = backIconResId),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        },
        title = {
            Text(
                text = title.orEmpty(),
                color = MaterialTheme.colorScheme.onSurface,
                style = Styles.Title3Bold
            )
        },
        actions = {
            actions()
        }
    )
}