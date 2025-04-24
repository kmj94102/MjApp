package com.example.mjapp.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle

/**
 * Outline이 있는 텍스트
 * @param text 텍스트
 * @param style 텍스트 스타일
 * @param outlineColor Outline 색상
 * @param modifier Modifier
 * **/
@Composable
fun OutlineText(
    text: String,
    style: TextStyle,
    outlineColor: Color,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Text(
            text = text,
            style = style.merge(
                TextStyle(
                    color = outlineColor,
                    fontSize = style.fontSize,
                    drawStyle = Stroke(width = 10f, join = StrokeJoin.Round)
                )
            )
        )
        Text(text = text, style = style)
    }
}