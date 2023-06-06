package com.example.mjapp.ui.custom

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.util.textStyle24B
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectSpinner(
    selectList: List<String>,
    state: PagerState,
    initValue: String,
    width: Dp = 90.dp,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        VerticalPager(
            contentPadding = PaddingValues(vertical = 60.dp),
            state = state,
            modifier = Modifier
                .size(width, 180.dp)
        ) { index ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(width, 60.dp)
            ) {
                Text(
                    text = selectList[index],
                    style = textStyle24B().copy(fontSize = 22.sp),
                    modifier = Modifier.graphicsLayer {
                        val pageOffset = ((state.currentPage - index)
                                + state.currentPageOffsetFraction).absoluteValue
                        alpha = lerp(
                            start = 0.2f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(top = 60.dp)
                .size(width, 1.dp)
                .background(MyColorGray)
        )
        Box(
            modifier = Modifier
                .padding(top = 120.dp)
                .size(width, 1.dp)
                .background(MyColorGray)
        )
    }

    LaunchedEffect(Unit) {
        val index = selectList.indexOf(initValue)
        state.scrollToPage(if (index == -1) 0 else index)
    }
}