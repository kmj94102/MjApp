package com.example.mjapp.ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * 상태에 따라 변하는 이미지 (Coil 용)
 * @param value 상태 값
 * @param trueImage true 상태일 때 이미지
 * @param falseImage false 상태일 때 이미지
 * **/
@Composable
fun ConditionAsyncImage(
    value: Boolean,
    trueImage: String,
    falseImage: String,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    contentDescription: String? = null,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(if (value) trueImage else falseImage)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        placeholder = placeholder,
        colorFilter = colorFilter,
        contentScale = contentScale,
        modifier = modifier
    )
}