package com.example.mjapp.ui.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.*
import com.example.mjapp.ui.dialog.YearMonthSelectDialog
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.*
import com.example.mjapp.util.*
import com.example.network.model.CalendarItem

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = hiltViewModel(),
    goToAdd: (String) -> Unit
) {
    var isYearMonthDialogShow by remember { mutableStateOf(false) }
    val lifecycleEvent = rememberLifecycleEvent()
    val status by viewModel.status.collectAsStateWithLifecycle()
    HeaderBodyContainer(
        status = status,
        headerContent = {
            CalendarHeader(
                viewModel = viewModel,
                onYearMonthClick = { isYearMonthDialogShow = true }
            )
        },
        bodyContent = {
            CalendarBody(
                viewModel = viewModel,
                goToAdd = goToAdd
            )
        },
    )

    YearMonthSelectDialog(
        year = viewModel.state.value.getYear(),
        month = viewModel.state.value.getMonth(),
        isShow = isYearMonthDialogShow,
        onDismiss = { isYearMonthDialogShow = false },
        onSelect = viewModel::updateYearMonth
    )

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchCalendarItems()
        }
    }
}

@Composable
fun CalendarHeader(
    viewModel: CalendarViewModel,
    onYearMonthClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        val state = viewModel.state.value

        Text(
            text = state.getYearMonth(),
            style = textStyle24B().copy(color = MyColorPurple),
            modifier = Modifier.nonRippleClickable(onYearMonthClick)
        )
        Spacer(modifier = Modifier.weight(1f))

        IconBox(
            boxColor = if (state.isCalendar) MyColorPurple else MyColorWhite,
            iconSize = 22.dp,
            iconRes = R.drawable.ic_calendar,
            onClick = { viewModel.updateMode(isCalendar = true) }
        )
        Spacer(modifier = Modifier.width(5.dp))

        IconBox(
            boxColor = if (state.isCalendar) MyColorWhite else MyColorPurple,
            iconSize = 22.dp,
            iconRes = R.drawable.ic_lists,
            onClick = { viewModel.updateMode(isCalendar = false) }
        )
    }
}

@Composable
fun CalendarBody(
    viewModel: CalendarViewModel,
    goToAdd: (String) -> Unit
) {
    when (viewModel.state.value.isCalendar) {
        true -> {
            CalendarContainer(
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth(),
                goToAdd = goToAdd
            )
        }

        false -> {
            ListContainer(
                viewModel = viewModel,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun CalendarContainer(
    viewModel: CalendarViewModel,
    modifier: Modifier,
    goToAdd: (String) -> Unit
) {
    val state = viewModel.state.value
    Column(modifier = modifier) {
        DoubleCard(
            bottomCardColor = MyColorPurple,
        ) {
            MonthCalendar(
                today = state.today,
                calendarInfo = state.calendarItemList,
                selectDate = state.selectDate,
                onSelectChange = viewModel::updateSelectDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 5.dp)
                    .padding(horizontal = 10.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, end = 3.dp)
        ) {
            val item = state.getSelectItem() ?: return@Row
            Text(
                text = buildAnnotatedString {
                    append(item.getDateAndDayOfWeek())
                    if (item.dateInfo.isNotEmpty()) {
                        withStyle(style = SpanStyle(color = MyColorGray, fontSize = 16.sp)) {
                            append(" ${item.dateInfo}")
                        }
                    }
                },
                style = textStyle24B().copy(color = MyColorPurple)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconBox(
                boxColor = MyColorRed,
                iconRes = R.drawable.ic_plus,
                onClick = { goToAdd(state.selectDate) }
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(top = 7.dp, bottom = 50.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 20.dp, top = 3.dp)
        ) {
            val list = state.getSelectItem()?.itemList
            if (list.isNullOrEmpty()) {
                item { CalendarEmptyContainer() }
            } else {
                list.forEach { calendarItem ->
                    when (calendarItem) {
                        is CalendarItem.PlanInfo -> {
                            item {
                                CalendarPlanContainer(
                                    info = calendarItem,
                                    modifier = Modifier.fillMaxWidth(),
                                    onTaskClick = viewModel::updateTask,
                                    deleteListener = viewModel::deletePlanTasks
                                )
                            }
                        }

                        is CalendarItem.ScheduleInfo -> {
                            item {
                                CalendarScheduleContainer(
                                    info = calendarItem,
                                    modifier = Modifier.fillMaxWidth(),
                                    deleteListener = viewModel::deleteSchedule
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarEmptyContainer() {
    DoubleCard(
        bottomCardColor = MyColorPurple,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "등록 된 일정이 없습니다.",
            style = textStyle14().copy(
                color = MyColorGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
        )
    }
}

@Composable
fun CalendarScheduleContainer(
    info: CalendarItem.ScheduleInfo,
    deleteListener: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    DoubleCard(
        bottomCardColor = MyColorPurple,
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorPurple)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = info.scheduleTitle,
                    style = textStyle16B().copy(fontSize = 18.sp),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                )
                IconBox(
                    boxSize = DpSize(24.dp, 24.dp),
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    iconRes = R.drawable.ic_modify,
                    iconSize = 18.dp,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconBox(
                    boxSize = DpSize(24.dp, 24.dp),
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    iconRes = R.drawable.ic_close,
                    iconSize = 18.dp,
                    onClick = { deleteListener(info.id) }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyColorBlack)
            )

            Text(
                text = info.getTime(),
                style = textStyle14().copy(MyColorGray),
                modifier = Modifier.padding(top = 5.dp, start = 10.dp)
            )
            Text(
                text = info.scheduleContent,
                style = textStyle16(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
            )
        }
    }
}

@Composable
fun CalendarPlanContainer(
    info: CalendarItem.PlanInfo,
    deleteListener: (Int) -> Unit,
    onTaskClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    DoubleCard(
        bottomCardColor = MyColorPurple,
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorPurple)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(
                    text = info.title,
                    style = textStyle16B().copy(fontSize = 18.sp),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                )
                IconBox(
                    boxSize = DpSize(24.dp, 24.dp),
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    iconRes = R.drawable.ic_modify,
                    iconSize = 18.dp,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconBox(
                    boxSize = DpSize(24.dp, 24.dp),
                    boxColor = MyColorRed,
                    boxShape = CircleShape,
                    iconRes = R.drawable.ic_close,
                    iconSize = 18.dp,
                    onClick = { deleteListener(info.id) }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyColorBlack)
            )

            info.taskList.forEach { item ->
                CommonRadio(
                    text = item.contents,
                    check = item.isCompleted,
                    onCheckedChange = {
                        onTaskClick(item.id, item.isCompleted.not())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun ListContainer(
    viewModel: CalendarViewModel,
    modifier: Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 50.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = modifier.padding(bottom = 20.dp)
    ) {
        viewModel.state.value.calendarItemList
            .filter { it.itemList.isNotEmpty() }
            .forEach { myCalendar ->
                item {
                    DoubleCard(
                        bottomCardColor = MyColorPurple,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MyColorPurple)
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append(myCalendar.getDateAndDayOfWeek())
                                        if (myCalendar.dateInfo.isNotEmpty()) {
                                            withStyle(
                                                style = SpanStyle(
                                                    color = MyColorGray,
                                                    fontSize = 14.sp
                                                )
                                            ) {
                                                append(" ${myCalendar.dateInfo}")
                                            }
                                        }
                                    },
                                    style = textStyle16B().copy(color = MyColorBlack),
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(vertical = 5.dp, horizontal = 10.dp)
                                )

                                IconBox(
                                    boxShape = CircleShape,
                                    boxColor = MyColorRed,
                                    boxSize = DpSize(24.dp, 24.dp),
                                    iconRes = R.drawable.ic_modify,
                                    iconSize = 16.dp,
                                    onClick = {}
                                )

                                IconBox(
                                    boxShape = CircleShape,
                                    boxColor = MyColorRed,
                                    boxSize = DpSize(24.dp, 24.dp),
                                    iconRes = R.drawable.ic_close,
                                    iconSize = 16.dp,
                                    modifier = Modifier.padding(start = 5.dp, end = 10.dp),
                                    onClick = {}
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(MyColorBlack)
                            )

                            myCalendar.itemList.forEachIndexed { index, item ->
                                when (item) {
                                    is CalendarItem.PlanInfo -> {
                                        ListPlanContainer(
                                            info = item,
                                            onTaskClick = viewModel::updateTask
                                        )
                                    }

                                    is CalendarItem.ScheduleInfo -> {
                                        ListScheduleContainer(info = item)
                                    }
                                }
                                if (myCalendar.itemList.size > index) {
                                    DashLine(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
    }
}

@Composable
fun ListScheduleContainer(
    info: CalendarItem.ScheduleInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = info.getTime(),
            style = textStyle14().copy(MyColorGray),
        )
        Text(
            text = info.scheduleTitle,
            style = textStyle16B().copy(fontSize = 18.sp),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = info.scheduleContent,
            style = textStyle16(),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ListPlanContainer(
    info: CalendarItem.PlanInfo,
    onTaskClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = info.title, style = textStyle16B().copy(fontSize = 18.sp))
        info.taskList.forEach { item ->
            CommonRadio(
                text = item.contents,
                check = item.isCompleted,
                onCheckedChange = {
                    onTaskClick(item.id, item.isCompleted.not())
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
    }
}