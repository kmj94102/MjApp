package com.example.mjapp.ui.custom

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16

@Composable
fun CommonRadio(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = textStyle16(),
    isEnable: Boolean = true,
    check: Boolean,
    color: Color = MyColorPurple,
    shape: Shape = CircleShape,
    onCheckedChange: (String) -> Unit,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.nonRippleClickable {
            if (isEnable) {
                onCheckedChange(text)
            }
        }
    ) {
        val borderColor = animateColorAsState(
            targetValue = if (check) color else MyColorLightGray,
            animationSpec = tween(durationMillis = 250),
            label = ""
        )
        val fillColor = animateColorAsState(
            targetValue = if (check) color else MyColorWhite,
            animationSpec = tween(durationMillis = 500),
            label = ""
        )

        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(shape)
                .border(1.dp, borderColor.value, shape)
                .background(fillColor.value)
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        textDecoration = if (check) TextDecoration.LineThrough else TextDecoration.None,
                    )
                ) {
                    append(text)
                }
            },
            style = textStyle.copy(
                color = if (check) MyColorLightGray else MyColorBlack,
            )
        )

    }
}