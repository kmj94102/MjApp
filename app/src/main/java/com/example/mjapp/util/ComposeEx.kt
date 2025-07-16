package com.example.mjapp.util

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.ui.theme.myFont

fun textStyle12(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = TextStyle(
    color = color,
    fontFamily = myFont,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    textAlign = textAlign,
    lineHeight = 18.sp,
    letterSpacing = -(0.025).em
)

fun textStyle12B(
    color: Color = MyColorBlack,
    textAlign: TextAlign = TextAlign.Start
): TextStyle = textStyle12(color, textAlign).copy(fontWeight = FontWeight.Bold)

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
    lineHeight = 26.sp,
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

fun Modifier.innerShadow(
    shape: Shape,
    color: Color,
    blur: Dp,
    offsetY: Dp,
    offsetX: Dp,
    spread: Dp
) = drawWithContent {
    drawContent()

    val rect = Rect(Offset.Zero, size)
    val paint = Paint().apply {
        this.color = color
        this.isAntiAlias = true
    }

    val shadowOutline = shape.createOutline(size, layoutDirection, this)

    drawIntoCanvas { canvas ->
        canvas.saveLayer(rect, paint)
        canvas.drawOutline(shadowOutline, paint)

        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
        if (blur.toPx() > 0) {
            frameworkPaint.maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
        }
        paint.color = Color.Black

        val spreadOffsetX = offsetX.toPx() + if (offsetX.toPx() < 0) -spread.toPx() else spread.toPx()
        val spreadOffsetY = offsetY.toPx() + if (offsetY.toPx() < 0) -spread.toPx() else spread.toPx()

        canvas.translate(spreadOffsetX, spreadOffsetY)
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}


@Preview
@Composable
fun InnerShadowTest() {
    Scaffold { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            stickyHeader {
                Row(
                    modifier = Modifier.fillMaxWidth().background(MyColorWhite).innerShadow(
                        shape = RoundedCornerShape(0.dp),
                        color = MyColorWhite,
                        blur = 10.dp,
                        offsetY = 10.dp,
                        offsetX = 10.dp,
                        spread = 10.dp
                    )
                ) {
                    Text("이곳은 해더입니다.", modifier = Modifier.fillMaxWidth().padding(20.dp))
                }
            }
            items((0..100).toList()) {
                Text("이곳은 아이템입니다. $it", modifier = Modifier.fillMaxWidth().padding(20.dp))
            }
        }
    }
}