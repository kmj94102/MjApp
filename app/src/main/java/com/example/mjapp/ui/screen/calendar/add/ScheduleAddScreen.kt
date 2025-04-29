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
import com.example.mjapp.ui.dialog.DateSelectDialog
import com.example.mjapp.ui.dialog.RecurrenceSelectDialog
import com.example.mjapp.ui.dialog.TimeSelectDialog
import com.example.mjapp.ui.screen.calendar.ScheduleAddUiState
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
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
    var uiState by remember { mutableStateOf(ScheduleAddUiState()) }
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        heightContent = {
            ScheduleAddHeight(
                onBackClick = onBackClick,
                onPlanAddClick = { goToPlanAdd(viewModel.initDate) }
            )
        },
        bodyContent = {
            ScheduleAddMedium(
                viewModel = viewModel,
                onRecurrenceSelect = {
                    uiState = uiState.copy(isRecurrenceSelectDialogShow = true)
                },
                onDateSelect = {
                    uiState = uiState.copy(isDateSelectDialogShow = true)
                },
                onTimeSelect = {
                    uiState = uiState.copy(isTimeSelectDialogShow = true)
                }
            )
        },
        bottomContent = {
            ScheduleAddLow(viewModel::insertSchedule)
        }
    )

    DateSelectDialog(
        date = viewModel.scheduleModifier.value.selectDate,
        isShow = uiState.isDateSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isDateSelectDialogShow = false) },
        onSelect = viewModel::updateDate
    )

    TimeSelectDialog(
        time = viewModel.scheduleModifier.value.selectTime,
        isShow = uiState.isTimeSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isTimeSelectDialogShow = false) },
        onSelect = viewModel::updateTime
    )

    RecurrenceSelectDialog(
        initValue = viewModel.scheduleModifier.value.recurrenceType,
        isShow = uiState.isRecurrenceSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isRecurrenceSelectDialogShow = false) },
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
                textStyle = textStyle16B(),
                onClick = {
                    viewModel.updateSelectItem(ScheduleAddViewModel.SCHEDULE_DATE)
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
                    textStyle = textStyle16B(),
                    onClick = {
                        viewModel.updateSelectItem(ScheduleAddViewModel.START_TIME)
                        onTimeSelect()
                    },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(10.dp))

                DoubleCardText(
                    bottomCardColor = MyColorPurple,
                    text = scheduleModifier.endTime.ifEmpty { "종료 시간" },
                    textStyle = textStyle16B(),
                    onClick = {
                        viewModel.updateSelectItem(ScheduleAddViewModel.END_TIME)
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
                textStyle = textStyle16B(),
                onClick = onRecurrenceSelect,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (scheduleModifier.recurrenceType != Recurrence.NoRecurrence.originName) {
            item {
                DoubleCardText(
                    bottomCardColor = MyColorPurple,
                    text = scheduleModifier.recurrenceEndDate.ifEmpty { "반복 종료일" },
                    textStyle = textStyle16B(),
                    onClick = {
                        viewModel.updateSelectItem(ScheduleAddViewModel.RECURRENCE_END_DATE)
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