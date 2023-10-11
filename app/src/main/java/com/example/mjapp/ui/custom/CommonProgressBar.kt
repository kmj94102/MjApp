package com.example.mjapp.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorWhite

@Composable
fun CommonProgressBar(
    modifier: Modifier,
    height: Dp = 15.dp,
    thumbColor: Color = MyColorWhite,
    activeTrackColor: Color = MyColorBeige,
    inactiveTrackColor: Color = MyColorLightGray,
    max: Int,
    currentPosition: Int,
    onValueChanged: (Float) -> Unit
) {
    var currentDragX by remember { mutableFloatStateOf(0f) }
    var position by remember { mutableFloatStateOf(0f) }
    var isDrag by remember { mutableStateOf(false) }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        isDrag = true
                        currentDragX = it.x
                    },
                    onDragEnd = {
                        isDrag = false
                        val newProgress = currentDragX / size.width
                        onValueChanged(newProgress)
                    },
                    onDrag = { change, _ ->
                        currentDragX = change.position.x
                    }
                )
            }
    ) {
        val height10Percent = size.height / 10
        currentDragX = if (isDrag) currentDragX else (currentPosition.toFloat() / max) * size.width

        drawRoundRect(
            size = Size(size.width, size.height),
            color = inactiveTrackColor,
            cornerRadius = CornerRadius(size.height, size.height)
        )

        drawRoundRect(
            size = Size(currentDragX.coerceIn(0f, size.width), size.height),
            color = activeTrackColor,
            cornerRadius = CornerRadius(size.height, size.height)
        )

        drawCircle(
            radius = size.height / 2 - height10Percent,
            color = thumbColor,
            center = Offset(
                currentDragX.coerceIn(0f, size.width) - height10Percent * 6,
                center.y
            )
        )
    }

    LaunchedEffect(currentPosition) {
        position = currentPosition.toFloat()
    }
}