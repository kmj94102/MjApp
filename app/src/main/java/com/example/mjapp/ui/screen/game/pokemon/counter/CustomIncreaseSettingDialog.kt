package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
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
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.PokemonCounter

@Composable
fun CustomIncreaseSettingDialog(
    isShow: Boolean,
    selectValue: PokemonCounter,
    onDismiss: () -> Unit,
    onUpdateClick: (Int, String) -> Unit
) {
    val customIncrease = remember {
        mutableStateOf("")
    }

    if (isShow) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(MyColorWhite)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        text = "증가폭 설정",
                        style = textStyle16().copy(fontSize = 18.sp),
                        modifier = Modifier.align(Alignment.Center)
                    )

                    IconBox(
                        boxColor = MyColorRed,
                        boxShape = CircleShape,
                        iconRes = R.drawable.ic_close,
                        iconSize = 21.dp,
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 20.dp)
                    ) {
                        onDismiss()
                    }
                }

                DoubleCard(
                    bottomCardColor = MyColorRed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 17.dp)
                ) {
                    CommonTextField(
                        value = customIncrease.value,
                        onTextChange = {
                            if (isNumeric(it)) {
                                customIncrease.value = it
                            }
                        },
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                DoubleCard(
                    topCardColor = MyColorRed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 17.dp, bottom = 20.dp)
                        .nonRippleClickable {
                            if (selectValue.count != customIncrease.value.toInt()) {
                                onUpdateClick(customIncrease.value.toInt(), selectValue.number)
                            }
                            onDismiss()
                        }
                ) {
                    Text(
                        text = "설정하기",
                        style = textStyle16B().copy(fontSize = 18.sp, textAlign = TextAlign.Center),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    )
                }
            }
        }
    }
}