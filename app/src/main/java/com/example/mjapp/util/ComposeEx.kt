package com.example.mjapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.myFont

fun textStyle12(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    textAlign = textAlign
)

fun textStyle12B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    textAlign = textAlign
)

fun textStyle18(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    textAlign = textAlign
)

fun textStyle18B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    textAlign = textAlign
)

fun textStyle16(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    textAlign = textAlign
)

fun textStyle16B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp,
    textAlign = textAlign
)

fun textStyle24(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    textAlign = textAlign
)

fun textStyle24B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    textAlign = textAlign
)

fun Modifier.nonRippleClickable(
    onClick: () -> Unit
) = composed {
    this.then(
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    )
}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}