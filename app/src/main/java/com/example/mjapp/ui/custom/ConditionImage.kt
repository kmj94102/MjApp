package com.example.mjapp.ui.custom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * 상태에 따라 변하는 이미지
 * @param value 상태 값
 * @param trueImageRes true 상태일 때 이미지
 * @param falseImageRes false 상태일 때 이미지
 * **/
@Composable
fun ConditionImage(
    value: Boolean,
    @DrawableRes
    trueImageRes: Int,
    @DrawableRes
    falseImageRes: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        painter = painterResource(id = if (value) trueImageRes else falseImageRes),
        contentDescription = contentDescription,
        colorFilter = colorFilter,
        contentScale = contentScale,
        modifier = modifier
    )
}