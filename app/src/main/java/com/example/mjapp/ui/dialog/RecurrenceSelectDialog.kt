package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.textStyle16
import com.example.network.model.Recurrence

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecurrenceSelectDialog(
    initValue: String,
    isShow: Boolean,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    val checkValue = remember { mutableStateOf(Recurrence.getRecurrenceState(initValue)) }

    ConfirmCancelDialog2(
        isShow = isShow,
        bodyContents = {
            Text(
                "반복 설정",
                style = textStyle16(MyColorWhite),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 2,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                Recurrence.entries.forEach { recurrence ->
                    CommonRadio(
                        text = recurrence.koreanName,
                        color = MyColorDarkBlue,
                        check = checkValue.value == recurrence,
                        onCheckedChange = { checkValue.value = recurrence },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        onCancelClick = onDismiss,
        onConfirmClick = {
            onSelect(checkValue.value.originName)
            onDismiss()
        },
        onDismiss = onDismiss,
    )
}