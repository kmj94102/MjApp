package com.example.mjapp.ui.screen.calendar.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.Recurrence

@Composable
fun RecurrenceSelectDialog(
    initValue: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    if (isShow) {
        val checkValue = remember {
            mutableStateOf(Recurrence.getRecurrenceState(initValue))
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
                    Text(text = "반복 설정", style = textStyle16().copy(fontSize = 18.sp))
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
                
                Spacer(modifier = Modifier.height(30.dp))

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    CommonRadio(
                        text = Recurrence.NoRecurrence.koreanName,
                        check = checkValue.value == Recurrence.NoRecurrence,
                        onCheckedChange = {
                            checkValue.value = Recurrence.NoRecurrence
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CommonRadio(
                        text = Recurrence.Daily.koreanName,
                        check = checkValue.value == Recurrence.Daily,
                        onCheckedChange = {
                            checkValue.value = Recurrence.Daily
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    CommonRadio(
                        text = Recurrence.Weekly.koreanName,
                        check = checkValue.value == Recurrence.Weekly,
                        onCheckedChange = {
                            checkValue.value = Recurrence.Weekly
                        },
                        modifier = Modifier.weight(1f)
                    )
                    CommonRadio(
                        text = Recurrence.Monthly.koreanName,
                        check = checkValue.value == Recurrence.Monthly,
                        onCheckedChange = {
                            checkValue.value = Recurrence.Monthly
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.padding(horizontal = 20.dp)) {
                    CommonRadio(
                        text = Recurrence.Yearly.koreanName,
                        check = checkValue.value == Recurrence.Yearly,
                        onCheckedChange = {
                            checkValue.value = Recurrence.Yearly
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(23.dp))

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
                                onSelect(checkValue.value.originName)
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