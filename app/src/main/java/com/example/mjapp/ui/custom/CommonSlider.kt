package com.example.mjapp.ui.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import kotlin.math.abs

/**
 * 커스텀 Slider
 * @param height Slider 높이, 권장 사이즈 15.Dp, 25.Dp
 * @param valueRange Slider 범위
 * @param onValueChange Slider 값 변경 리스너
 * **/
@Composable
fun <T>CommonSlider(
    modifier: Modifier = Modifier,
    height: Dp = 15.dp,
    thumbColor: Color = MyColorWhite,
    activeTrackColor: Color = MyColorRed,
    inactiveTrackColor: Color = MyColorLightGray,
    disableTrackColor: Color = MyColorGray,
    enable: Boolean,
    valueRange: List<T>,
    onValueChange: (T) -> Unit
) {
    var currentDragX by remember { mutableStateOf(0f) }
    var offsetList by remember { mutableStateOf( listOf<Offset>() ) }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .pointerInput(Unit) {
                if (enable.not()) return@pointerInput

                detectDragGestures(
                    onDragStart = {
                        currentDragX = it.x
                    },
                    onDragEnd = {},
                    onDrag = { change, _ ->
                        val (nearestIndex, nearestOffset) = offsetList
                            .mapIndexed { index, offset -> index to offset }
                            .minByOrNull { (_, offset) -> abs(change.position.x - offset.x) }
                            ?: (0 to Offset(0f, 0f))

                        currentDragX = nearestOffset.x + size.width / 20
                        runCatching {
                            onValueChange(valueRange[nearestIndex])
                        }
                    }
                )
            }
    ) {
        val with10Percent = size.width / 10
        val height10Percent = size.height / 10

        val interval = (size.width - with10Percent) / (valueRange.size - 1)
        offsetList = List(valueRange.size) { index ->
            val x = with10Percent * 0.5f + interval * index
            Offset(x, center.y)
        }

        drawRoundRect(
            size = Size(size.width, size.height),
            color = inactiveTrackColor,
            cornerRadius = CornerRadius(size.height, size.height)
        )

        offsetList.forEach {
            drawCircle(
                radius = size.height / 2 - height10Percent * 2,
                color = thumbColor,
                center = it
            )
        }

        drawRoundRect(
            size = Size(currentDragX.coerceIn(with10Percent , size.width), size.height),
            color = if (enable) activeTrackColor else disableTrackColor,
            cornerRadius = CornerRadius(size.height, size.height)
        )

        drawCircle(
            radius = size.height / 2 - height10Percent,
            color = thumbColor,
            center = Offset(currentDragX.coerceIn(with10Percent , size.width) - height10Percent * 6, center.y)
        )
    }
}