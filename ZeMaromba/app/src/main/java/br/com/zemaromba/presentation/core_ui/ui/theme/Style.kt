package br.com.zemaromba.presentation.core_ui.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class Style {
    companion object {
        val Button1 = TextStyle(
            fontFamily = RobotoFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 0.sp,
            letterSpacing = 0.4.sp
        )
        val Title1 = TextStyle(
            fontFamily = RobotoFont,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.5.sp
        )
        val Title2 = TextStyle(
            fontFamily = RobotoFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.0.sp,
            letterSpacing = 0.2.sp
        )
        val Title3 = TextStyle(
            fontFamily = RobotoFont,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.0.sp,
            letterSpacing = 0.1.sp
        )
        val Body1 = TextStyle(
            fontFamily = RobotoFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp
        )
        val Label = TextStyle(
            fontFamily = RobotoFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        )
    }
}
