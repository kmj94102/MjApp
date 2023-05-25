package com.example.mjapp.ui.screen.game.elsword.counter

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
fun QuestSelectDialog(
    list: List<String>,
    selectItem: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    if (isShow) {
        val state = rememberPagerState()

        Dialog(onDismissRequest = onDismiss) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MyColorWhite)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                ) {
                    Text(
                        text = "퀘스트 선택",
                        style = textStyle16().copy(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                    IconBox(
                        boxShape = CircleShape,
                        boxColor = MyColorRed,
                        iconSize = 21.dp,
                        iconRes = R.drawable.ic_close,
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                SelectSpinner(
                    selectList = list,
                    state = state,
                    initValue = selectItem,
                    width = 1500.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 35.dp, horizontal = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                ) {
                    DoubleCard(
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                onDismiss()
                            }
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
                    Spacer(modifier = Modifier.width(5.dp))

                    DoubleCard(
                        topCardColor = MyColorRed,
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                onSelect(list[state.currentPage])
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