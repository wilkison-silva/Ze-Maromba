package br.com.zemaromba.presentation.components.bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import br.com.zemaromba.presentation.model.BottomSheetOption
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ListOptionsBottomSheet(
    title: String?,
    bottomSheetOptions: List<BottomSheetOption<T>>,
    onClickOptionItem: (optionId: T) -> Unit,
    onDismiss: () -> Unit
) {
    val skipPartiallyExpanded = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded.value
    )
    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = bottomSheetState,
    ) {
        if (!title.isNullOrBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.Space.space_20dp,
                        bottom = Dimens.Space.space_40dp,
                        start = Dimens.Space.space_28dp,
                        end = Dimens.Space.space_20dp,
                    ),
                text = title,
                textAlign = TextAlign.Left,
                style = Styles.Title3Normal
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = Dimens.Thickness.thickness_0dp
            )
        }
        LazyColumn(
            modifier = Modifier
                .padding(bottom = Dimens.Space.space_64dp)
                .fillMaxWidth(),
        ) {
            itemsIndexed(
                items = bottomSheetOptions,
                itemContent = { _, bottomSheetOption: BottomSheetOption<T> ->
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope
                                    .launch {
                                        delay(300)
                                        bottomSheetState.hide()
                                    }
                                    .invokeOnCompletion {
                                        if (!bottomSheetState.isVisible) {
                                            onClickOptionItem(bottomSheetOption.id)
                                        }
                                    }
                            }
                            .padding(
                                top = Dimens.Space.space_20dp,
                                bottom = Dimens.Space.space_20dp,
                                start = Dimens.Space.space_28dp
                            ),
                        text = bottomSheetOption.text,
                        textAlign = TextAlign.Left,
                        style = Styles.ButtonText1
                    )
                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = Dimens.Thickness.thickness_0dp
                    )
                }
            )
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope
                                .launch {
                                    delay(300)
                                    bottomSheetState.hide()
                                }
                                .invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        onDismiss()
                                    }
                                }
                        }
                        .padding(vertical = Dimens.Space.space_20dp),
                    text = "Fechar",
                    textAlign = TextAlign.Center,
                    style = Styles.ButtonText2
                )
            }
        }
    }
}