package br.com.zemaromba.presentation.components.search_bar

import androidx.annotation.StringRes

data class SearchBarState(
    val text: String = "",
    @StringRes val hint: Int,
) {
    val showHint: Boolean = text.isEmpty()
}