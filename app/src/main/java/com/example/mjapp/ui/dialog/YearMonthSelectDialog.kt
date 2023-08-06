package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorPurple

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YearMonthSelectDialog(
    year: String,
    month: String,
    isShow: Boolean,
    color: Color = MyColorPurple,
    onDismiss: () -> Unit,
    onSelect: (String, String) -> Unit
) {
    val yearList = (2020..2050).map { "$it" }
    val monthList = (1..12).map { it.toString().padStart(2, '0') }
    val yearState = rememberPagerState { yearList.size }
    val monthState = rememberPagerState { monthList.size }

    ConfirmCancelDialog(
        isShow = isShow,
        title = "연월 선택",
        bodyContents = {
            Row(
                modifier = Modifier
                    .padding(vertical = 34.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                SelectSpinner(
                    selectList = yearList,
                    state = yearState,
                    initValue = year
                )

                Spacer(modifier = Modifier.width(20.dp))

                SelectSpinner(
                    selectList = monthList,
                    state = monthState,
                    initValue = month
                )
            }
        },
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect(
                yearList[yearState.currentPage],
                monthList[monthState.currentPage]
            )
            onDismiss()
        },
        color = color,
        onDismiss = onDismiss
    )
}