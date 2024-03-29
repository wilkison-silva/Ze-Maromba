package br.com.zemaromba.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles

@Composable
fun SimpleDialog(
    onDismissClick: () -> Unit,
    onConfirmClick: () -> Unit,
    dismissText: String,
    confirmText: String,
    titleText: String,
    descriptionText: String

) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = {
            onDismissClick()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick()
                }
            ) {
                Text(
                    text = confirmText,
                    style = Styles.BodyTextNormal,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick()
                }
            ) {
                Text(
                    text = dismissText,
                    style = Styles.BodyTextNormal,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {
            Text(
                text = titleText,
                style = Styles.Title4Bold
            )
        },
        text = {
            Text(
                text = descriptionText,
                style = Styles.BodyTextNormal
            )
        }
    )
}