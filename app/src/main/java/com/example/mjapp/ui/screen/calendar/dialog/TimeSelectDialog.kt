package com.example.mjapp.ui.screen.calendar.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSelectDialog(
    time: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    if (isShow) {
        val hourList = (0..23).map { "$it".padStart(2, '0') }
        val minuteList = (0..59).map { it.toString().padStart(2, '0') }
        val hourState = rememberPagerState { hourList.size }
        val minuteState = rememberPagerState { minuteList.size }

        val hour = time.substring(0, 2)
        val minute = time.substring(3, 5)

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
                    Text(text = "시간 선택", style = textStyle16().copy(fontSize = 18.sp))
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
                                    "${hourList[hourState.currentPage]}:${minuteList[minuteState.currentPage]}"
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