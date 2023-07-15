package com.example.mjapp.ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorWhite

/**
 * 2중 카드
 * @param modifier Modifier
 * @param connerSize 카드 라운드 값. 기본 값 10dp
 * @param topCardColor 상단 카드 색상
 * @param bottomCardColor 하단 카드 색상
 * @param contents 내용
 * **/
@Composable
fun DoubleCard(
    modifier: Modifier = Modifier,
    connerSize: Dp = 10.dp,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    minHeight: Dp = 10.dp,
    contents: @Composable ColumnScope.() -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (topCard, bottomCard) = createRefs()
        Card(
            colors = CardDefaults.cardColors(
                containerColor = bottomCardColor
            ),
            border = BorderStroke(1.dp, MyColorBlack),
            shape = RoundedCornerShape(connerSize),
            modifier = Modifier
                .heightIn(min = minHeight)
                .constrainAs(bottomCard) {
                    top.linkTo(parent.top, 3.dp)
                    start.linkTo(parent.start, 3.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(topCard.bottom, (-3).dp)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {}

        Card(
            colors = CardDefaults.cardColors(
                containerColor = topCardColor
            ),
            border = BorderStroke(1.dp, MyColorBlack),
            shape = RoundedCornerShape(connerSize),
            modifier = Modifier
                .heightIn(min = minHeight)
                .constrainAs(topCard) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, 3.dp)

                    width = Dimension.fillToConstraints
                }
        ) {
            contents()
        }
    }
}