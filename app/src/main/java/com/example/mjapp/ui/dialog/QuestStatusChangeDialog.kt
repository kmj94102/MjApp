package com.example.mjapp.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.custom.CommonSlider
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle12B
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

    var state by remember { mutableIntStateOf(item.progress) }
    var type by remember { mutableStateOf(item.type) }
    var isEnable by remember { mutableStateOf(type == ElswordQuestUpdate.Ongoing) }

    ConfirmCancelDialog(
        isShow = isShow,
        title = "상태 업데이트",
        onCancelClick = onDismiss,
        onConfirmClick = { onUpdate(item.characterName, type, state) },
        onDismiss = onDismiss,
        bodyContents = {
            Card(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, MyColorBlack),
                colors = CardDefaults.cardColors(
                    containerColor = MyColorWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
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
                                isEnable = false
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
                                isEnable = true
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
                                    style = textStyle12B().copy(fontSize = 14.sp)
                                )
                            }
                            CommonSlider(
                                valueRange = (0..item.max).toList(),
                                enable = isEnable,
                                onValueChange = { state = it },
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
                                isEnable = false
                            }
                        )
                    }
                }
            }
        },
        color = MyColorRed
    )
}