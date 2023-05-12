package com.example.mjapp.ui.custom

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.util.textStyle12B

@Composable
fun CommonProgressBar(
    modifier: Modifier = Modifier,
    percent: Int,
    progressColor: Color = MyColorPurple,
) {
    val width = percent / 100.0f
    val isStart = remember { mutableStateOf(false) }
    val state by animateFloatAsState(
        targetValue = if (isStart.value) width else 0.0f,
        animationSpec = tween(durationMillis = 2500)
    )

    LaunchedEffect(Unit) {
        isStart.value = true
    }

    Box(
        modifier = modifier
            .height(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MyColorLightGray)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(state)
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(progressColor)
        )
        Text(
            text = "$percent %",
            style = textStyle12B(),
            modifier = Modifier
                .padding(start = 10.dp)
                .align(Alignment.CenterStart)
        )
    }
}