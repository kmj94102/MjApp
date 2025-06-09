package com.example.mjapp.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle16
import java.util.Calendar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DateSelectDialog(
    date: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val year = runCatching { date.substring(0, 4) }.getOrElse { "2023" }
    val month = runCatching { date.substring(5, 7) }.getOrElse { "01" }
    val day = runCatching { date.substring(8, 10) }.getOrElse { "01" }

    val yearList = (2020..2050).map { "$it" }
    val monthList = (1..12).map { it.toString().padStart(2, '0') }
    val dayList = remember { mutableStateOf(getDayList(year, month)) }

    val yearState = rememberPagerState { yearList.size }
    val monthState = rememberPagerState { monthList.size }
    val dayState = rememberPagerState { dayList.value.size }

    LaunchedEffect(yearState.currentPage) {
        dayList.value =
            getDayList(yearList[yearState.currentPage], monthList[monthState.currentPage])
    }
    LaunchedEffect(monthState.currentPage) {
        dayList.value =
            getDayList(yearList[yearState.currentPage], monthList[monthState.currentPage])
    }

    ConfirmCancelDialog2(
        isShow = isShow,
        bodyContents = {
            Text(
                "날짜 선택",
                style = textStyle16(MyColorWhite),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                SelectSpinner(
                    selectList = yearList,
                    state = yearState,
                    initValue = year,
                    width = 80.dp
                )

                SelectSpinner(
                    selectList = monthList,
                    state = monthState,
                    initValue = month,
                    width = 80.dp
                )

                SelectSpinner(
                    selectList = dayList.value,
                    state = dayState,
                    initValue = day,
                    width = 80.dp
                )
            }
            Spacer(Modifier.height(24.dp))
        },
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect(
                "${yearList[yearState.currentPage]}.${monthList[monthState.currentPage]}.${dayList.value[dayState.currentPage]}"
            )
            onDismiss()
        },
        onDismiss = onDismiss
    )
}

fun getDayList(year: String, month: String): List<String> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year.toInt())
        set(Calendar.MONTH, month.toInt() - 1)
    }

    return (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        .map { it.toString().padStart(2, '0') }
}