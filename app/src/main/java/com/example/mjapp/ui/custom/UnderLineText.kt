package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun UnderLineText(
    modifier: Modifier = Modifier,
    textValue: String,
    textStyle: TextStyle,
    underLineColor: Color,
    underLineHeight: Dp = 2.dp
) {
    ConstraintLayout(modifier = modifier) {
        val (text, underLine) = createRefs()

        Text(
            text = textValue,
            style = textStyle,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Box(
            modifier = Modifier
                .height(underLineHeight)
                .background(underLineColor)
                .constrainAs(underLine) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )
    }
}