package com.example.mjapp.ui.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.mjapp.R
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.util.nonRippleClickable

@Composable
fun IconBox(
    modifier: Modifier = Modifier,
    boxSize: DpSize = DpSize(30.dp, 30.dp),
    boxShape: Shape = RoundedCornerShape(5.dp),
    boxColor: Color = MyColorPurple,
    @DrawableRes
    iconRes: Int = R.drawable.ic_back,
    iconSize: Dp = 24.dp,
    iconColor: Color = MyColorBlack,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(boxSize)
            .clip(boxShape)
            .border(1.dp, MyColorBlack, boxShape)
            .background(boxColor)
            .nonRippleClickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
    }
}