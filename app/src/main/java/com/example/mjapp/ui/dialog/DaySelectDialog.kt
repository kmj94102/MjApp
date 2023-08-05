package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.screen.calendar.dialog.getDayList
import com.example.mjapp.ui.theme.MyColorTurquoise

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DaySelectDialog(
    isShow: Boolean,
    yearMonth: String,
    selectDay: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val yearMonthList = yearMonth.split(".")
    val dayList = getDayList(yearMonthList[0], yearMonthList[1])
    val dayState = rememberPagerState { dayList.size }

    ConfirmCancelDialog(
        isShow = isShow,
        title = "날짜 선택",
        topButtonContents = { DialogCloseButton(onClose = onDismiss) },
        bodyContents = {
            Box(
                modifier = Modifier
                    .padding(vertical = 34.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                SelectSpinner(
                    selectList = dayList,
                    state = dayState,
                    initValue = selectDay
                )
            }
        },
        onCancelClick = onDismiss,
        onConfirmClick = {
            onConfirm(dayList[dayState.currentPage])
            onDismiss()
        },
        color = MyColorTurquoise,
        onDismiss = onDismiss
    )
}