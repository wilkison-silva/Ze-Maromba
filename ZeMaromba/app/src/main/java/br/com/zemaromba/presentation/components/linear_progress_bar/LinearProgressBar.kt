package br.com.zemaromba.presentation.components.linear_progress_bar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun LinearProgressBar(
    modifier: Modifier,
    initialProgress: Float,
    targetProgress: Float,
) {
    val animationPlayed = remember {
        mutableStateOf(false)
    }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed.value) targetProgress else initialProgress,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 300
        ),
        label = ""
    )
    LaunchedEffect(key1 = Unit) {
        animationPlayed.value = true
    }
    LinearProgressIndicator(
        modifier = modifier,
        progress = curPercent.value
    )
}