package br.com.zemaromba.core_ui.components.search_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.zemaromba.R
import br.com.zemaromba.core_ui.ui.theme.ZeMarombaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String,
    onTextChanged: (newText: String) -> Unit
) {
    val text = remember {
        mutableStateOf("")
    }
    val showHint = remember {
        mutableStateOf(true)
    }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp, top = 10.dp, bottom = 10.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(start = 45.dp, end = 20.dp),
            value = text.value,
            onValueChange = {
                onTextChanged(it)
                text.value = it
                showHint.value = text.value.isEmpty()
            },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { focusManager.clearFocus() }
            )
        )
        if (showHint.value) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(start = 60.dp, end = 20.dp),
                text = hint,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview()
@Composable
fun SearchBarPreview() {
    val text = remember {
        mutableStateOf("")
    }
    ZeMarombaTheme {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            hint = "Ex: um exercício",
            onTextChanged = {
                text.value = it
            }
        )
    }
}