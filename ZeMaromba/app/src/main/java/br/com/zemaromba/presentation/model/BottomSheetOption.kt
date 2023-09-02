package br.com.zemaromba.presentation.model

import androidx.annotation.DrawableRes

data class BottomSheetOption<T>(
    val id: T,
    val text: String,
    @DrawableRes val iconRes: Int?
)
