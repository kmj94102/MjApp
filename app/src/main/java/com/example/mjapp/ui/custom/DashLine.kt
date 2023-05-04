package com.example.mjapp.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.example.mjapp.ui.theme.MyColorBlack

@Composable
fun DashLine(
    modifier: Modifier = Modifier,
    color: Color = MyColorBlack
) {
    Canvas(
        modifier = modifier
    ) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 10f), 0f)
        )
    }
}