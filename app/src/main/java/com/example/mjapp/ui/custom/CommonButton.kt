package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle20B

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(5.dp),
    backgroundColor: Color = MyColorPurple,
    borderColor: Color = MyColorBlack,
    text: String,
    textStyle: TextStyle = textStyle16B(),
    innerPadding: PaddingValues = PaddingValues(vertical = 3.dp),
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .border(1.dp, borderColor, shape)
            .background(backgroundColor)
            .nonRippleClickable { onClick() }
    ) {
        Text(text = text, style = textStyle, modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    backgroundColor: Color = MyColorRed,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
    text: String,
    textStyle: TextStyle = textStyle20B(MyColorWhite),
    innerPadding: PaddingValues = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(shape)
            .background(backgroundColor)
            .border(borderWidth, borderColor, shape)
            .nonRippleClickable(onClick)
    ) {
        Text(text = text, style = textStyle, modifier = Modifier.padding(innerPadding))
    }
}