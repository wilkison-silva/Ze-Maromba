package br.com.zemaromba.presentation.components.loaders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import br.com.zemaromba.R
import br.com.zemaromba.presentation.core_ui.ui.theme.Dimens
import br.com.zemaromba.presentation.core_ui.ui.theme.Styles
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun SimpleLoader(
    modifier: Modifier,
    message: String
) {
    Box(modifier = modifier) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
        LottieAnimation(
            modifier = Modifier
                .size(Dimens.Space.space_128dp)
                .align(alignment = Alignment.TopCenter),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
        Text(
            modifier = Modifier
                .padding(
                    start = Dimens.Space.space_64dp,
                    end = Dimens.Space.space_64dp,
                    top = Dimens.Space.space_108dp)
                .align(alignment = Alignment.TopCenter),
            text = message,
            color = MaterialTheme.colorScheme.onSurface,
            style = Styles.Title5Normal,
            textAlign = TextAlign.Center
        )
    }
}