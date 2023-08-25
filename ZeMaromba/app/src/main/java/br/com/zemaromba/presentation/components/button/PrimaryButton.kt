package br.com.zemaromba.presentation.components.button

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    isEnabled: Boolean = true,
    showLoading: Boolean = false
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = { onClick() },
        enabled = isEnabled && !showLoading
    ) {
        if (showLoading) {
            CircularProgressIndicator(
                color = if (isEnabled && !showLoading) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(vertical = Dimens.Space.space_4dp)
                    .size(Dimens.Space.space_24dp),
                strokeWidth = Dimens.Space.space_3dp
            )
        } else {
            Text(
                modifier = Modifier.padding(vertical = Dimens.Space.space_4dp),
                text = title,
                textAlign = TextAlign.Center,
                style = Styles.ButtonText1
            )
        }
    }
}