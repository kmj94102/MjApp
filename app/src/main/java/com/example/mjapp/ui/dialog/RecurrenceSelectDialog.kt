package com.example.mjapp.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.theme.MyColorPurple
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

    ConfirmCancelDialog(
        isShow = isShow,
        title = "반복 설정",
        bodyContents = {
            FlowRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                maxItemsInEachRow = 2,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp)
            ) {
                Recurrence.values().forEach { recurrence ->
                    CommonRadio(
                        text = recurrence.koreanName,
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
        color = MyColorPurple
    )
}