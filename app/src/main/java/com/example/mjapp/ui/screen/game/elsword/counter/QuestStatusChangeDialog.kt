package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorTurquoise
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.ElswordQuestUpdate

@Composable
fun QuestStatusChangeDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onUpdate: (String) -> Unit
) {
    if (isShow) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MyColorWhite)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                ) {
                    Text(
                        text = "상태 업데이트",
                        style = textStyle16().copy(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )
                    IconBox(
                        boxColor = MyColorRed,
                        boxShape = CircleShape,
                        iconRes = R.drawable.ic_close,
                        iconSize = 21.dp,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        onDismiss()
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 25.dp)
                        .padding(start = 20.dp, end = 17.dp)
                ) {
                    DoubleCard(
                        topCardColor = MyColorBeige,
                        minHeight = 100.dp,
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                onUpdate(ElswordQuestUpdate.Remove)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(100.dp)
                        ) {
                            Text(
                                text = "초기화",
                                style = textStyle16B().copy(
                                    fontSize = 18.sp,
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    DoubleCard(
                        topCardColor = MyColorTurquoise,
                        minHeight = 100.dp,
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                onUpdate(ElswordQuestUpdate.Ongoing)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(100.dp)
                        ) {
                            Text(
                                text = "진행",
                                style = textStyle16B().copy(
                                    fontSize = 18.sp,
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }

                    DoubleCard(
                        topCardColor = MyColorRed,
                        minHeight = 100.dp,
                        modifier = Modifier
                            .weight(1f)
                            .nonRippleClickable {
                                onUpdate(ElswordQuestUpdate.Complete)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(100.dp)
                        ) {
                            Text(
                                text = "완료",
                                style = textStyle16B().copy(
                                    fontSize = 18.sp,
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}