package com.example.mjapp.ui.screen.game.elsword.counter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.custom.CommonSlider
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*
import com.example.network.model.ElswordQuestUpdate
import com.example.network.model.ElswordQuestUpdateInfo

@Composable
fun QuestStatusChangeDialog(
    item: ElswordQuestUpdateInfo?,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onUpdate: (String, String, Int) -> Unit
) {
    if (item == null) return
    if (isShow.not()) return

    var state by remember {
        mutableStateOf(item.progress)
    }
    var type by remember {
        mutableStateOf(item.type)
    }

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

            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, MyColorBlack),
                colors = CardDefaults.cardColors(
                    containerColor = MyColorWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = item.image,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.size(width = 81.dp, height = 147.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 1.dp, height = 147.dp)
                            .background(MyColorBlack)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                    ) {
                        CommonRadio(
                            text = "진행 안 함",
                            textStyle = textStyle12B().copy(fontSize = 14.sp),
                            color = MyColorRed,
                            check = type == ElswordQuestUpdate.Remove,
                            onCheckedChange = {
                                type = ElswordQuestUpdate.Remove
                            }
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        CommonRadio(
                            text = "진행 중",
                            textStyle = textStyle12B().copy(fontSize = 14.sp),
                            color = MyColorRed,
                            check = type == ElswordQuestUpdate.Ongoing,
                            onCheckedChange = {
                                type = ElswordQuestUpdate.Ongoing
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = item.questName,
                                    style = textStyle12().copy(fontSize = 14.sp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "${state}/${item.max}",
                                    style = textStyle12B().copy(fontSize = 14.sp),
                                    modifier = Modifier
                                        .padding(end = 0.dp)
                                )
                            }
                            CommonSlider(
                                valueRange = (0..item.max).toList(),
                                enable = type == ElswordQuestUpdate.Ongoing,
                                onValueChange = {
                                    state = it
                                },
                                modifier = Modifier.padding(top = 5.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        CommonRadio(
                            text = "완료",
                            textStyle = textStyle12B().copy(fontSize = 14.sp),
                            color = MyColorRed,
                            check = type == ElswordQuestUpdate.Complete,
                            onCheckedChange = {
                                type = ElswordQuestUpdate.Complete
                            }
                        )
                    }
                }
            }

            DoubleCard(
                topCardColor = MyColorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 13.dp, bottom = 15.dp)
                    .nonRippleClickable {
                        onUpdate(item.characterName, type, state.toInt())
                    }
            ) {
                Text(
                    text = "확인",
                    textAlign = TextAlign.Center,
                    style = textStyle16B(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
            }

        }
    }
}