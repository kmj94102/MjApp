package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.DoubleCardButton
import com.example.mjapp.ui.custom.DoubleCardText
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.screen.calendar.dialog.DateSelectDialog
import com.example.mjapp.ui.screen.calendar.dialog.RecurrenceSelectDialog
import com.example.mjapp.ui.screen.calendar.dialog.TimeSelectDialog
import com.example.mjapp.ui.structure.HighMediumLowContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.network.model.Recurrence

@Composable
fun ScheduleAddScreen(
    onBackClick: () -> Unit,
    goToPlanAdd: (String) -> Unit,
    viewModel: ScheduleAddViewModel = hiltViewModel()
) {
    var isDateSelectDialogShow by remember { mutableStateOf(false) }
    var isTimeSelectDialogShow by remember { mutableStateOf(false) }
    var isRecurrenceSelectDialogShow by remember { mutableStateOf(false) }

    val status by viewModel.status.collectAsStateWithLifecycle()

    HighMediumLowContainer(
        status = status,
        onBackClick = onBackClick,
        heightContent = {
            ScheduleAddHeight(
                onBackClick = onBackClick,
                onPlanAddClick = { goToPlanAdd(viewModel.initDate) }
            )
        },
        mediumContent = {
            ScheduleAddMedium(
                viewModel = viewModel,
                onRecurrenceSelect = { isRecurrenceSelectDialogShow = true },
                onDateSelect = { isDateSelectDialogShow = true },
                onTimeSelect = { isTimeSelectDialogShow = true }
            )
        },
        lowContent = {
            ScheduleAddLow(viewModel::insertSchedule)
        }
    )

    DateSelectDialog(
        date = viewModel.selectDate.value,
        isShow = isDateSelectDialogShow,
        onDismiss = { isDateSelectDialogShow = false },
        onSelect = viewModel::updateDate
    )

    TimeSelectDialog(
        time = viewModel.selectTime.value,
        isShow = isTimeSelectDialogShow,
        onDismiss = { isTimeSelectDialogShow = false },
        onSelect = viewModel::updateTime
    )

    RecurrenceSelectDialog(
        initValue = viewModel.scheduleModifier.value.recurrenceType,
        isShow = isRecurrenceSelectDialogShow,
        onDismiss = { isRecurrenceSelectDialogShow = false },
        onSelect = viewModel::updateRecurrence
    )
}

@Composable
fun ScheduleAddHeight(
    onBackClick: () -> Unit,
    onPlanAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 3.dp)
    ) {
        IconBox(onClick = onBackClick)
        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MyColorPurple)
                .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
        ) {
            Text(text = "일정", style = textStyle16B(), modifier = Modifier.padding(5.dp))
        }
        Spacer(modifier = Modifier.width(5.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MyColorWhite)
                .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                .nonRippleClickable(onPlanAddClick)
        ) {
            Text(text = "계획", style = textStyle16B(), modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ScheduleAddMedium(
    viewModel: ScheduleAddViewModel,
    onRecurrenceSelect: () -> Unit,
    onDateSelect: () -> Unit,
    onTimeSelect: () -> Unit
) {
    val scheduleModifier = viewModel.scheduleModifier.value

    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            DoubleCardText(
                bottomCardColor = MyColorPurple,
                text = scheduleModifier.date.ifEmpty { "날짜 선택" },
                onClick = {
                    viewModel.updateSelectItem(ScheduleAddViewModel.ScheduleDate)
                    onDateSelect()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                DoubleCardText(
                    bottomCardColor = MyColorPurple,
                    text = scheduleModifier.startTime.ifEmpty { "시작 시간" },
                    onClick = {
                        viewModel.updateSelectItem(ScheduleAddViewModel.StartTime)
                        onTimeSelect()
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))

                DoubleCardText(
                    bottomCardColor = MyColorPurple,
                    text = scheduleModifier.endTime.ifEmpty { "종료 시간" },
                    onClick = {
                        viewModel.updateSelectItem(ScheduleAddViewModel.EndTime)
                        onTimeSelect()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            DoubleCardText(
                bottomCardColor = MyColorPurple,
                text = scheduleModifier.getRecurrenceInfo(),
                onClick = onRecurrenceSelect,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (scheduleModifier.recurrenceType != Recurrence.NoRecurrence.originName) {
            item {
                DoubleCardText(
                    bottomCardColor = MyColorPurple,
                    text = scheduleModifier.recurrenceEndDate.ifEmpty { "반복 종료일" },
                    onClick = {
                        viewModel.updateSelectItem(ScheduleAddViewModel.RecurrenceEndDate)
                        onDateSelect()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item {
            DoubleCardTextField(
                value = scheduleModifier.scheduleTitle,
                onTextChange = viewModel::updateTitle,
                hint = "일정 제목",
                textStyle = textStyle16(),
                imeAction = ImeAction.Next,
                bottomCardColor = MyColorPurple,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            DoubleCardTextField(
                value = scheduleModifier.scheduleContent,
                onTextChange = viewModel::updateContent,
                hint = "일정 내용",
                textStyle = textStyle16(),
                imeAction = ImeAction.None,
                singleLine = false,
                maxLines = 10,
                bottomCardColor = MyColorPurple,
                minHeight = 200.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ScheduleAddLow(onClick: () -> Unit) {
    DoubleCardButton(
        text = "등록하기",
        topCardColor = MyColorPurple,
        onClick = onClick
    )
}