package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorPurple

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSelectDialog(
    time: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val hourList = (0..23).map { "$it".padStart(2, '0') }
    val minuteList = (0..59).map { it.toString().padStart(2, '0') }
    val hourState = rememberPagerState { hourList.size }
    val minuteState = rememberPagerState { minuteList.size }

    val hour = runCatching { time.substring(0, 2) }.getOrElse { "00" }
    val minute = runCatching { time.substring(3, 5) }.getOrElse { "00" }

    ConfirmCancelDialog(
        isShow = isShow,
        title = "시간 선택",
        bodyContents = {
            Row(
                modifier = Modifier
                    .padding(vertical = 34.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                SelectSpinner(
                    selectList = hourList,
                    state = hourState,
                    initValue = hour
                )

                Spacer(modifier = Modifier.width(20.dp))

                SelectSpinner(
                    selectList = minuteList,
                    state = minuteState,
                    initValue = minute
                )
            }
        },
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect("${hourList[hourState.currentPage]}:${minuteList[minuteState.currentPage]}")
            onDismiss()
        },
        color = MyColorPurple,
        onDismiss = onDismiss
    )
}