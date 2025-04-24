package com.example.mjapp.ui.screen.game.pokemon.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.isNumeric
import com.example.mjapp.util.textStyle16
import com.example.network.model.PokemonCounter
import kotlin.math.min

@Composable
fun CustomIncreaseSettingDialog(
    isShow: Boolean,
    selectValue: PokemonCounter,
    onDismiss: () -> Unit,
    onUpdateClick: (Int, String) -> Unit
) {
    if (isShow.not()) return
    val customIncrease = remember { mutableStateOf("") }

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
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 20.dp)
                )
            }

            DoubleCardTextField(
                value = customIncrease.value,
                onTextChange = {
                    if (isNumeric(it)) {
                        customIncrease.value = "${min(it.toInt(), 999_999)}"
                    } else if(it.isEmpty()) {
                        customIncrease.value = it
                    }
                },
                keyboardType = KeyboardType.Number,
                hint = "증가폭 입력",
                bottomCardColor = MyColorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 17.dp)
            )

            DoubleCardButton(
                text = "설정하기",
                onClick = {
                    if (isNumeric(customIncrease.value) && selectValue.count != customIncrease.value.toInt()) {
                        onUpdateClick(customIncrease.value.toInt(), selectValue.number)
                    }
                    onDismiss()
                },
                topCardColor = MyColorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 17.dp, bottom = 20.dp)
            )
        }
    }
}