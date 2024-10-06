package com.example.happy.presentation.detail

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerLoadingAnimation() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE1E1E1))
            .shimmerLoadingAnimation(false),
    )
}

fun Modifier.shimmerLoadingAnimation(
    isLoadingCompleted: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = Color.White,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier = if (isLoadingCompleted) {
    this
} else {
    composed {
        val shimmerColors = listOf(
            color.copy(alpha = 0.0f),
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.0f),
        )
        val transition = rememberInfiniteTransition(label = "")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
            shape = shape
        )
    }
}
