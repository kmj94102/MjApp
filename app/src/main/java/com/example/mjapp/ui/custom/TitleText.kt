package com.example.mjapp.ui.custom

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.mjapp.util.textStyle24B

@Composable
fun TitleText(
    title: String,
    color: Color,
    modifier: Modifier = Modifier,
    style: TextStyle = textStyle24B()
) {
    Text(
        text = title,
        style = style.copy(color = color),
        modifier = modifier
    )
}