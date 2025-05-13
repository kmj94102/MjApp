package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.theme.MyColorRed

@Composable
fun NumberSelectDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    onUpdateClick: (String) -> Unit
) {
    val number = remember { mutableStateOf("") }

    BaseDialog(
        isShow = isShow,
        title = "이동할 번호",
        onDismiss = onDismiss,
        bodyContents = {
            DoubleCard(
                bottomCardColor = MyColorRed,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 17.dp)
            ) {
                CommonTextField(
                    value = number.value,
                    onTextChange = { number.value = it },
                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        bottomContents = {
            DoubleCardButton(
                text = "설정하기",
                topCardColor = MyColorRed,
                onClick = {
                    onUpdateClick(number.value)
                    onDismiss()
                }
            )
        }
    )
}