package com.example.mjapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mjapp.ui.theme.ColorBlack
import com.example.mjapp.ui.theme.myFont

fun textStyle12(): TextStyle = TextStyle(
    color = ColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

fun textStyle12B(): TextStyle = TextStyle(
    color = ColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp
)

fun textStyle16(): TextStyle = TextStyle(
    color = ColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

fun textStyle16B(): TextStyle = TextStyle(
    color = ColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

fun textStyle24(): TextStyle = TextStyle(
    color = ColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp
)

fun Modifier.nonRippleClickable(
    onClick: () -> Unit
) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}