package com.example.mjapp.ui.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B

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
                .fillMaxWidth()
                .constrainAs(bottomCard) {
                    top.linkTo(parent.top, 3.dp)
                    start.linkTo(parent.start, 3.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(topCard.bottom, (-3).dp)

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
                .fillMaxWidth()
                .constrainAs(topCard) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end, 3.dp)
                }
        ) {
            contents()
        }
    }
}

@Composable
fun CenteredDoubleCard(
    modifier: Modifier = Modifier,
    connerSize: Dp = 10.dp,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    minHeight: Dp = 10.dp,
    contents: @Composable ColumnScope.() -> Unit
) {
    DoubleCard(
        modifier = modifier,
        connerSize = connerSize,
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        minHeight = minHeight,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                contents()
            }
        }
    }
}

@Composable
fun ImageDoubleCard(
    modifier: Modifier = Modifier,
    connerSize: Dp = 10.dp,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    @DrawableRes resId: Int,
    imageSize: DpSize,
    innerPadding: PaddingValues
) {
    CenteredDoubleCard(
        connerSize = connerSize,
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier
                .padding(innerPadding)
                .size(imageSize)
        )
    }
}

@Composable
fun AsyncImageDoubleCard(
    modifier: Modifier = Modifier,
    connerSize: Dp = 10.dp,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    condition: Boolean,
    trueImage: String,
    falseImage: String,
    @DrawableRes
    placeholderRes: Int,
    saturation: Float = 1f,
    size: DpSize,
    innerPadding: PaddingValues
) {
    CenteredDoubleCard(
        connerSize = connerSize,
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        modifier = modifier
    ) {
        ConditionAsyncImage(
            value = condition,
            trueImage = trueImage,
            falseImage = falseImage,
            placeholder = painterResource(id = placeholderRes),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply { setToSaturation(saturation) }
            ),
            modifier = Modifier
                .padding(innerPadding)
                .size(size)
        )
    }
}

@Composable
fun DoubleCardButton(
    modifier: Modifier = Modifier,
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorWhite,
    text: String,
    textStyle: TextStyle = textStyle16B(),
    innerPadding: PaddingValues = PaddingValues(vertical = 10.dp),
    onClick: () -> Unit
) {
    DoubleCard(
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        modifier = modifier.nonRippleClickable(onClick)
    ) {
        Text(
            text = text,
            style = textStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}