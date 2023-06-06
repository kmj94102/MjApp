package com.example.mjapp.ui.screen.calendar.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.SelectSpinner
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DateSelectDialog(
    date: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    if (isShow) {
        val year = date.substring(0, 4)
        val month = date.substring(5, 7)
        val day = date.substring(8, 10)

        val yearList = (2020..2050).map { "$it" }
        val monthList = (1..12).map { it.toString().padStart(2, '0') }
        val dayList = remember {
            mutableStateOf(getDayList(year, month))
        }

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

        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MyColorWhite)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(top = 10.dp)
                ) {
                    Text(text = "날짜 선택", style = textStyle16().copy(fontSize = 18.sp))
                    IconBox(
                        boxShape = CircleShape,
                        boxColor = MyColorRed,
                        iconRes = R.drawable.ic_close,
                        iconSize = 21.dp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        onDismiss()
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 35.dp)
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
                    Spacer(modifier = Modifier.width(20.dp))

                    SelectSpinner(
                        selectList = dayList.value,
                        state = dayState,
                        initValue = day
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 17.dp, bottom = 20.dp)
                ) {
                    DoubleCard(
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable { onDismiss() }
                    ) {
                        Text(
                            text = "취소",
                            style = textStyle16B().copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    DoubleCard(
                        topCardColor = MyColorRed,
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                onSelect(
                                    "${yearList[yearState.currentPage]}.${monthList[monthState.currentPage]}.${dayList.value[dayState.currentPage]}"
                                )
                                onDismiss()
                            }
                    ) {
                        Text(
                            text = "확인",
                            style = textStyle16B().copy(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

fun getDayList(year: String, month: String): List<String> {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year.toInt())
        set(Calendar.MONTH, month.toInt() - 1)
    }

    return (1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        .map {
            it.toString().padStart(2, '0')
        }
}