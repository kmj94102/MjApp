package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.DoubleCardTextField
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.screen.calendar.PlanTasksModify
import com.example.mjapp.ui.screen.calendar.dialog.DateSelectDialog
import com.example.mjapp.ui.screen.calendar.dialog.RecurrenceSelectDialog
import com.example.mjapp.ui.screen.calendar.dialog.TimeSelectDialog
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.toast
import com.example.network.model.Recurrence
import com.example.network.model.ScheduleModifier

@Composable
fun CalendarAddScreen(
    onBackClick: () -> Unit,
    viewModel: CalendarAddViewModel = hiltViewModel()
) {
    val isDateSelectDialogShow = remember { mutableStateOf(false) }
    val isTimeSelectDialogShow = remember { mutableStateOf(false) }
    val isRecurrenceSelectDialogShow = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 21.dp, bottom = 10.dp, start = 20.dp, end = 17.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 3.dp)
        ) {
            IconBox {
                onBackClick()
            }
            Spacer(modifier = Modifier.weight(1f))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(if (viewModel.isSchedule.value) MyColorPurple else MyColorWhite)
                    .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                    .nonRippleClickable {
                        viewModel.updateIsSchedule(true)
                    }
            ) {
                Text(text = "일정", style = textStyle16B(), modifier = Modifier.padding(5.dp))
            }
            Spacer(modifier = Modifier.width(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(if (viewModel.isSchedule.value) MyColorWhite else MyColorPurple)
                    .border(1.dp, MyColorBlack, RoundedCornerShape(5.dp))
                    .nonRippleClickable {
                        viewModel.updateIsSchedule(false)
                    }
            ) {
                Text(text = "계획", style = textStyle16B(), modifier = Modifier.padding(5.dp))
            }
        }

        when (viewModel.isSchedule.value) {
            true -> {
                ScheduleAddContainer(
                    scheduleModifier = viewModel.scheduleModifier.value,
                    onDateSelect = { isDate ->
                        viewModel.updateItem(
                            type = if (isDate) {
                                CalendarAddViewModel.ScheduleDate
                            } else {
                                CalendarAddViewModel.RecurrenceEndDate
                            }
                        )
                        isDateSelectDialogShow.value = true
                    },
                    onTimeSelect = { isStart ->
                        viewModel.updateItem(
                            type = if (isStart) {
                                CalendarAddViewModel.StartTime
                            } else {
                                CalendarAddViewModel.EndTime
                            }
                        )
                        isTimeSelectDialogShow.value = true
                    },
                    onRecurrenceSelect = {
                        isRecurrenceSelectDialogShow.value = true
                    },
                    updateTitle = {
                        viewModel.updateItem(value = it, type = CalendarAddViewModel.ScheduleTitle)
                    },
                    updateContent = {
                        viewModel.updateItem(value = it, type = CalendarAddViewModel.ScheduleContent)
                    }
                )
            }
            false -> {
                PlanAddContainer(
                    planTasks = viewModel.planTasks.value,
                    addPlanListener = {
                        viewModel.addPlanItem()
                    },
                    removePlanListener = {
                        viewModel.removePlanItem(it)
                    },
                    onPlanContentsChange = { index, value ->
                        viewModel.updatePlanContents(index, value)
                    },
                    onDateSelect = {
                        isDateSelectDialogShow.value = true
                    },
                    updateTitle = {
                        viewModel.updateItem(value = it, type = CalendarAddViewModel.PlanTitle)
                    }
                )
            }
        }

        DoubleCard(
            topCardColor = MyColorPurple,
            modifier = Modifier
                .fillMaxWidth()
                .nonRippleClickable {
                    if (viewModel.isSchedule.value) {
                        viewModel.insertSchedule()
                    } else {
                        viewModel.insertPlan()
                    }
                }
        ) {
            Text(
                text = "등록하기",
                style = textStyle16B().copy(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }
    }

    when (val status =
        viewModel.status.collectAsState(initial = CalendarAddViewModel.Status.Init).value) {
        is CalendarAddViewModel.Status.Init -> {}
        is CalendarAddViewModel.Status.Success -> {
            context.toast(status.msg)
            viewModel.updateStatusInit()
            onBackClick()
        }
        is CalendarAddViewModel.Status.Failure -> {
            context.toast(status.msg)
            viewModel.updateStatusInit()
        }
    }

    DateSelectDialog(
        date = viewModel.selectDate.value,
        isShow = isDateSelectDialogShow.value,
        onDismiss = {
            isDateSelectDialogShow.value = false
        },
        onSelect = {
            viewModel.updateDate(it)
        }
    )

    TimeSelectDialog(
        time = viewModel.selectTime.value,
        isShow = isTimeSelectDialogShow.value,
        onDismiss = {
            isTimeSelectDialogShow.value = false
        },
        onSelect = {
            viewModel.updateTime(it)
        }
    )

    RecurrenceSelectDialog(
        initValue = viewModel.scheduleModifier.value.recurrenceType,
        isShow = isRecurrenceSelectDialogShow.value,
        onDismiss = {
            isRecurrenceSelectDialogShow.value = false
        },
        onSelect = { value ->
            viewModel.updateItem(value = value, type = CalendarAddViewModel.RecurrenceType)
        }
    )
}

@Composable
fun ColumnScope.ScheduleAddContainer(
    scheduleModifier: ScheduleModifier,
    onDateSelect: (Boolean) -> Unit,
    onTimeSelect: (Boolean) -> Unit,
    onRecurrenceSelect: () -> Unit,
    updateTitle: (String) -> Unit,
    updateContent: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        item {
            DoubleCard(
                bottomCardColor = MyColorPurple,
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable {
                        onDateSelect(true)
                    }
            ) {
                Text(
                    text = scheduleModifier.date.ifEmpty { "날짜 선택" },
                    style = textStyle16().copy(
                        color = isEmptyColor(scheduleModifier.date.isEmpty())
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                )
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                DoubleCard(
                    bottomCardColor = MyColorPurple,
                    modifier = Modifier
                        .weight(1f)
                        .nonRippleClickable {
                            onTimeSelect(true)
                        }
                ) {
                    Text(
                        text = scheduleModifier.startTime.ifEmpty { "시작 시간" },
                        style = textStyle16().copy(
                            color = isEmptyColor(scheduleModifier.startTime.isEmpty())
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                DoubleCard(
                    bottomCardColor = MyColorPurple,
                    modifier = Modifier
                        .weight(1f)
                        .nonRippleClickable {
                            onTimeSelect(false)
                        }
                ) {
                    Text(
                        text = scheduleModifier.endTime.ifEmpty { "종료 시간" },
                        style = textStyle16().copy(
                            color = isEmptyColor(scheduleModifier.endTime.isEmpty())
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    )
                }
            }
        }

        item {
            DoubleCard(
                bottomCardColor = MyColorPurple,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = scheduleModifier.getRecurrenceInfo(),
                    style = textStyle16(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                        .nonRippleClickable {
                            onRecurrenceSelect()
                        }
                )
            }
        }

        if (scheduleModifier.recurrenceType != Recurrence.NoRecurrence.originName) {
            item {
                DoubleCard(
                    bottomCardColor = MyColorPurple,
                    modifier = Modifier
                        .fillMaxWidth()
                        .nonRippleClickable {
                            onDateSelect(false)
                        }
                ) {
                    Text(
                        text = scheduleModifier.recurrenceEndDate.ifEmpty { "반복 종료 시간" },
                        style = textStyle16().copy(
                            color = isEmptyColor(scheduleModifier.recurrenceEndDate.isEmpty())
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp, horizontal = 15.dp)
                    )
                }
            }
        }

        item {
            DoubleCardTextField(
                value = scheduleModifier.scheduleTitle,
                onTextChange = {
                    updateTitle(it)
                },
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
                onTextChange = {
                    updateContent(it)
                },
                hint = "일정 내용",
                textStyle = textStyle16(),
                imeAction = ImeAction.None,
                singleLine = false,
                maxLines = 10,
                bottomCardColor = MyColorPurple,
                minHeight = 200.dp,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ColumnScope.PlanAddContainer(
    planTasks: PlanTasksModify,
    addPlanListener: () -> Unit,
    removePlanListener: (Int) -> Unit,
    onPlanContentsChange: (Int, String) -> Unit,
    onDateSelect: () -> Unit,
    updateTitle: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 10.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        item {
            DoubleCard(
                bottomCardColor = MyColorPurple,
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable {
                        onDateSelect()
                    }
            ) {
                Text(
                    text = planTasks.planDate.ifEmpty { "날짜 선택" },
                    style = textStyle16().copy(
                        color = isEmptyColor(planTasks.planDate.isEmpty())
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 15.dp)
                )
            }
        }

        item {
            DoubleCardTextField(
                value = planTasks.title,
                onTextChange = {
                    updateTitle(it)
                },
                hint = "계획 제목",
                textStyle = textStyle16(),
                bottomCardColor = MyColorPurple,
                modifier = Modifier.fillMaxWidth()
            )
        }

        planTasks.taskList.forEachIndexed { index, task ->
            item {
                DoubleCardTextField(
                    value = task.contents,
                    onTextChange = {
                        onPlanContentsChange(index, it)
                    },
                    hint = "계획 내용",
                    textStyle = textStyle16(),
                    modifier = Modifier.fillMaxWidth(),
                    bottomCardColor = MyColorPurple,
                    tailIcon = {
                        IconBox(
                            boxColor = MyColorRed,
                            boxShape = CircleShape,
                            iconRes = R.drawable.ic_close,
                            iconSize = 16.dp,
                            modifier = Modifier.padding(end = 10.dp)
                        ) {
                            removePlanListener(index)
                        }
                    }
                )
            }
        }

        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                IconBox(
                    boxShape = CircleShape,
                    boxColor = MyColorRed,
                    iconRes = R.drawable.ic_plus,
                ) {
                    addPlanListener()
                }
            }
        }
    }
}

private fun isEmptyColor(value: Boolean) = if (value) MyColorGray else MyColorBlack