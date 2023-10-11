package com.example.mjapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.myFont

fun textStyle12(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)

fun textStyle12B(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp
)

fun textStyle18(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp
)

fun textStyle18B(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp
)

fun textStyle16(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp
)

fun textStyle16B(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
    fontSize = 16.sp
)

fun textStyle24(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp
)

fun textStyle24B(): TextStyle = TextStyle(
    color = MyColorBlack,
    fontFamily = myFont,
    fontWeight = FontWeight.Bold,
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