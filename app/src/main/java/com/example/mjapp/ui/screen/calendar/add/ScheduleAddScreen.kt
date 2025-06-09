package com.example.mjapp.ui.screen.calendar.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CommonTextField
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.dialog.DateSelectDialog
import com.example.mjapp.ui.dialog.RecurrenceSelectDialog
import com.example.mjapp.ui.dialog.TimeSelectDialog
import com.example.mjapp.ui.screen.calendar.ScheduleAddUiState
import com.example.mjapp.ui.structure.HeaderBodyBottomContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle14B
import com.example.network.model.PlanTasksModify
import com.example.network.model.Recurrence
import com.example.network.model.ScheduleModifier

@Composable
fun ScheduleAddScreen(
    navHostController: NavHostController? = null,
    viewModel: ScheduleAddViewModel = hiltViewModel()
) {
    var uiState by remember { mutableStateOf(ScheduleAddUiState()) }
    val status by viewModel.status.collectAsStateWithLifecycle()
    val state by viewModel.state

    HeaderBodyBottomContainer(
        status = status,
        onBackClick = { navHostController?.popBackStack() },
        heightContent = {
            ScheduleAddHeader(
                onBackClick = { navHostController?.popBackStack() },
                isSchedule = state.isSchedule,
                onScheduleClick = { viewModel.updateIsSchedule(true) },
                onPlanClick = { viewModel.updateIsSchedule(false) }
            )
        },
        bodyContent = {
            if (state.isSchedule) {
                ScheduleAddBody(
                    scheduleInfo = state.scheduleInfo,
                    onUpdate = viewModel::updateSchedule,
                    onDateClick = { uiState = uiState.copy(isDateSelectDialogShow = true) },
                    onStartTimeClick = { uiState = uiState.copy(isStartTimeSelectDialogShow = true) },
                    onEndTimeClick = { uiState = uiState.copy(isEndTimeSelectDialogShow = true) },
                    onRecurrenceClick = { uiState = uiState.copy(isRecurrenceSelectDialogShow = true) },
                )
            } else {
                PlanAddBody(
                    planInfo = state.planInfo,
                    onUpdate = viewModel::updatePlan,
                    onAddTask = viewModel::updateAddTask,
                    onDateClick = { uiState = uiState.copy(isDateSelectDialogShow = true) }
                )
            }
        },
        bottomContent = { ScheduleAddSubmit(viewModel::submit) },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    )

    DateSelectDialog(
        date = state.scheduleInfo.date,
        isShow = uiState.isDateSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isDateSelectDialogShow = false) },
        onSelect = viewModel::updateDate
    )

    TimeSelectDialog(
        time =  state.scheduleInfo.startTime,
        isShow = uiState.isStartTimeSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isStartTimeSelectDialogShow = false) },
        onSelect = viewModel::updateStartTime
    )

    TimeSelectDialog(
        time =  state.scheduleInfo.endTime,
        isShow = uiState.isEndTimeSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isEndTimeSelectDialogShow = false) },
        onSelect = viewModel::updateEndTime
    )

    RecurrenceSelectDialog(
        initValue = state.scheduleInfo.recurrenceEndDate,
        isShow = uiState.isRecurrenceSelectDialogShow,
        onDismiss = { uiState = uiState.copy(isRecurrenceSelectDialogShow = false) },
        onSelect = viewModel::updateRecurrenceType
    )
}

@Composable
fun ScheduleAddHeader(
    onBackClick: () -> Unit = {},
    isSchedule: Boolean = true,
    onScheduleClick: () -> Unit = {},
    onPlanClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)){
        Box(modifier = Modifier.padding(start = 24.dp).align(Alignment.CenterStart)) {
            CommonGnbBackButton(onBackClick)
        }

        Row (
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(MyColorLightBlack, RoundedCornerShape(24.dp))
                .padding(2.dp)
                .align(Alignment.Center)
        ){
            Text("일정",
                style = textStyle14B(
                    color = if(isSchedule) MyColorWhite else MyColorGray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(81.dp)
                    .background(
                        color = if(isSchedule) MyColorDarkBlue else MyColorLightBlack,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 12.dp)
                    .nonRippleClickable(onScheduleClick)
            )

            Text("계획",
                style = textStyle14B(
                    color = if(isSchedule) MyColorGray else MyColorWhite,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(81.dp)
                    .background(
                        color = if(isSchedule) MyColorLightBlack else MyColorDarkBlue,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(vertical = 12.dp)
                    .nonRippleClickable(onPlanClick)
            )
        }
    }
}

@Composable
fun ScheduleAddBody(
    scheduleInfo: ScheduleModifier = ScheduleModifier(),
    onUpdate: (ScheduleModifier) -> Unit = {},
    onDateClick: () -> Unit = {},
    onStartTimeClick: () -> Unit = {},
    onEndTimeClick: () -> Unit = {},
    onRecurrenceClick: () -> Unit = {},
    onRecurrenceEndDateClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 24.dp).padding(top = 24.dp)
    ) {
        SelectBox(
            value = scheduleInfo.date,
            hint = "일정 날짜 선택",
            onClick = onDateClick
        )
        Row {
            SelectBox(
                value = scheduleInfo.startTime,
                hint = "시작 시간",
                modifier = Modifier.weight(1f),
                onClick = onStartTimeClick
            )
            Spacer(modifier = Modifier.width(8.dp))

            SelectBox(
                value = scheduleInfo.endTime,
                hint = "종료 시간",
                modifier = Modifier.weight(1f),
                onClick = onEndTimeClick
            )
        }
        SelectBox(
            value = scheduleInfo.getRecurrenceInfo(),
            hint = "반복 없음",
            onClick = onRecurrenceClick
        )
        if (scheduleInfo.recurrenceType != Recurrence.NoRecurrence.originName) {
            SelectBox(
                value = scheduleInfo.recurrenceEndDate,
                hint = "반복 종료 일",
                onClick = onRecurrenceEndDateClick
            )
        }
        CommonTextField(
            value = scheduleInfo.scheduleTitle,
            onTextChange = {
                onUpdate(scheduleInfo.copy(scheduleTitle = it))
            },
            hint = "일정 제목",
            textStyle = textStyle14(MyColorWhite),
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorLightBlack, RoundedCornerShape(16.dp))
                .padding(20.dp)
        )
        CommonTextField(
            value = scheduleInfo.scheduleContent,
            onTextChange = {
                onUpdate(scheduleInfo.copy(scheduleContent = it))
            },
            textStyle = textStyle14(MyColorWhite),
            singleLine = false,
            hint = "일정 내용",
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorLightBlack, RoundedCornerShape(16.dp))
                .padding(20.dp)
        )
    }
}

@Composable
fun PlanAddBody(
    planInfo: PlanTasksModify = PlanTasksModify(),
    onUpdate: (PlanTasksModify) -> Unit = {},
    onAddTask: () -> Unit = {},
    onRemoveTask: (Int) -> Unit = {},
    onDateClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 24.dp).padding(top = 24.dp)
    ) {
        SelectBox(
            value = planInfo.planDate,
            hint = "계획 날짜 선택",
            onClick = onDateClick
        )
        CommonTextField(
            value = planInfo.title,
            onTextChange = {
                onUpdate(planInfo.copy(title = it))
            },
            hint = "계획 제목",
            textStyle = textStyle14(MyColorWhite),
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorLightBlack, RoundedCornerShape(16.dp))
                .padding(vertical = 20.dp, horizontal = 10.dp)
        )
        planInfo.taskList.forEachIndexed { index, task ->
            CommonTextField(
                value = task.contents,
                onTextChange = {
                    val newList = planInfo.taskList.toMutableList()
                    newList[index] = task.copy(contents = it)
                    onUpdate(planInfo.copy(taskList = newList))
                },
                textStyle = textStyle14(MyColorWhite),
                trailingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        tint = Color(0xFF777C89),
                        modifier = Modifier
                            .size(24.dp)
                            .nonRippleClickable { onRemoveTask(index) }
                    )
                },
                hint = "계획 내용",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorLightBlack, RoundedCornerShape(16.dp))
                    .padding(10.dp)
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .background(MyColorDarkBlue, CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                tint = MyColorWhite,
                modifier = Modifier
                    .size(28.dp)
                    .nonRippleClickable(onAddTask)
            )
        }
    }
}

@Composable
fun SelectBox(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MyColorLightBlack, RoundedCornerShape(16))
            .padding(20.dp)
            .nonRippleClickable(onClick)
    ) {
        if (value.isEmpty()) {
            Text(
                text = hint,
                style = textStyle14(MyColorGray)
            )
        } else {
            Text(
                text = value,
                style = textStyle14(MyColorWhite)
            )
        }
    }
}

@Composable
fun ScheduleAddSubmit(
    onClick: () -> Unit = {}
) {
    TextButton(
        text = "등록하기",
        backgroundColor = MyColorDarkBlue,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp)
    )
}