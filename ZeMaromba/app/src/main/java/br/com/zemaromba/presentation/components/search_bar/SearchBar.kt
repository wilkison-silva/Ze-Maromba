package br.com.zemaromba.presentation.components.search_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import br.com.zemaromba.R
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.core_ui.ui.theme.ZeMarombaTheme

@Composable
fun SearchBar(
    modifier: Modifier,
    state: SearchBarState,
    onTextChange: (newText: String) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = CircleShape
            ),
        value = state.text,
        onValueChange = {
            onTextChange(it)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(start = Dimens.Space.space_12dp),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = state.text.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = Dimens.Space.space_12dp)
                        .clickable {
                            onTextChange("")
                        },
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = state.hint),
                style = Styles.BodyTextNormal
            )
        },
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Sentences
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        textStyle = Styles.BodyTextNormal
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    val text = remember {
        mutableStateOf("")
    }
    ZeMarombaTheme {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            state = SearchBarState(
                text = text.value,
                hint = R.string.hint_searchbar_exercise,
            ),
            onTextChange = {
                text.value = it
            }
        )
    }
}