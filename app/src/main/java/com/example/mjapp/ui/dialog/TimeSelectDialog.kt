package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle16

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

    ConfirmCancelDialog2(
        isShow = isShow,
        bodyContents = {
            Text(
                "시간 선택",
                style = textStyle16(MyColorWhite),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier
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
            Spacer(Modifier.height(24.dp))
        },
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect("${hourList[hourState.currentPage]}:${minuteList[minuteState.currentPage]}")
            onDismiss()
        },
        onDismiss = onDismiss
    )
}