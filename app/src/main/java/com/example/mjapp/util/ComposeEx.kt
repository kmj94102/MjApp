package com.example.mjapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.myFont

fun textStyle14(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    textAlign = textAlign,
    lineHeight = 20.sp,
    letterSpacing = -(0.025).em
)

fun textStyle14B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = textStyle14(color, textAlign).copy(fontWeight = FontWeight.Bold)

fun textStyle16(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    textAlign = textAlign,
    letterSpacing = -(0.025).em
)

fun textStyle16B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = textStyle16(color, textAlign).copy(fontWeight = FontWeight.Bold)

fun textStyle18(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    textAlign = textAlign,
    letterSpacing = -(0.025).em
)

fun textStyle18B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = textStyle18(color, textAlign).copy(fontWeight = FontWeight.Bold)

fun textStyle20(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    textAlign = textAlign,
    letterSpacing = -(0.025).em,
    lineHeight = 34.sp
)

fun textStyle20B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = textStyle20(color, textAlign).copy(fontWeight = FontWeight.Bold)

fun textStyle24(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    textAlign = textAlign,
    lineHeight = 24.sp,
    letterSpacing = -(0.025).em
)

fun textStyle24B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = textStyle24(color, textAlign).copy(fontWeight = FontWeight.Bold)

fun textStyle30B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 30.sp,
    textAlign = textAlign,
    lineHeight = 34.sp,
    letterSpacing = -(0.025).em
)

fun Modifier.nonRippleClickable(
    onClick: () -> Unit
) = composed {
    this.clickable(
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

inline fun <T> LazyListScope.items(
    items: List<T>,
    noinline key: ((item: T) -> Any)? = null,
    noinline contentType: (item: T) -> Any? = { null },
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
    crossinline emptyItemContent: @Composable LazyItemScope.() -> Unit,
) {
    if (items.isEmpty()) {
        item { emptyItemContent() }
    } else {
        items(
            count = items.size,
            key = if (key != null) { index: Int -> key(items[index]) } else null,
            contentType = { index: Int -> contentType(items[index]) }
        ) {
            itemContent(items[it])
        }
    }
}

fun pokemonBackground() = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF5DBEE1),
        Color(0xFF226496)
    )
)

fun pokemonGrayBackground() = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFA2A2A2),
        Color(0xFF767676)
    )
)