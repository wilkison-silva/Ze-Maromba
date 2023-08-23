package br.com.zemaromba.presentation.core_ui.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import br.com.zemaromba.R

object Styles {

    private val RobotoFont = FontFamily(
        Font(R.font.roboto, FontWeight.Normal)
    )

    val Title1Normal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.5.sp
    )
    val Title1Bold = Title1Normal.copy(fontWeight = FontWeight.Bold)

    val Title2Normal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.5.sp
    )
    val Title2Bold = Title2Normal.copy(fontWeight = FontWeight.Bold)

    val Title3Normal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    )
    val Title3Bold = Title3Normal.copy(fontWeight = FontWeight.Bold)

    val Title4Normal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    )
    val Title4Bold = Title4Normal.copy(fontWeight = FontWeight.Bold)

    val Title5Normal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.0.sp,
        letterSpacing = 0.1.sp
    )
    val Title5Bold = Title5Normal.copy(fontWeight = FontWeight.Bold)

    val BodyTextNormal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.2.sp
    )
    val BodyTextBold = BodyTextNormal.copy(fontWeight = FontWeight.Bold)


    val ButtonText1 = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.4.sp
    )
    val ButtonText2 = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.4.sp
    )

    val CaptionNormal = TextStyle(
        fontFamily = RobotoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    )
    val CaptionBold = CaptionNormal.copy(fontWeight = FontWeight.Bold)

}
