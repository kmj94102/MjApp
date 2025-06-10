package com.example.mjapp.ui.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CommonRadio
import com.example.mjapp.ui.custom.CustomCalendar
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle20B
import com.example.network.model.CalendarItem.PlanInfo
import com.example.network.model.CalendarItem.ScheduleInfo
import com.example.network.model.ScheduleCalendarInfo
import com.example.network.model.isSameDay

@Composable
fun ScheduleScreen(
    navHostController: NavHostController? = null,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val selectDate by viewModel.selectDate.collectAsStateWithLifecycle()
    val calendarInfo by viewModel.calendarInfo.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonGnb(
                title = "일정/계획",
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                },
                endButton = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = null,
                        tint = MyColorWhite,
                        modifier = Modifier
                            .size(28.dp)
                            .nonRippleClickable {
                                navHostController?.navigate(
                                    NavScreen2.ScheduleAdd(viewModel.getSelectDate())
                                )
                            }
                    )
                }
            )
        },
        bodyContent = {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                CustomCalendar(
                    yearMonth = viewModel.getYearMonth(),
                    selectDate = selectDate,
                    list = calendarInfo,
                    onPrevClick = viewModel::prevMonth,
                    onNextClick = viewModel::nextMonth,
                    onDateClick = viewModel::updateSelectDate
                )
                Spacer(Modifier.height(32.dp))

                CalendarBody(
                    selectInfo = calendarInfo.firstOrNull {
                        isSameDay(selectDate, it.date)
                    }
                )
            }
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    )
}

@Composable
fun CalendarBody(
    selectInfo: ScheduleCalendarInfo?
) {
    Text(
        selectInfo?.getDayInfo() ?: "",
        style = textStyle20B(color = MyColorWhite),
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
    Text(
        selectInfo?.detailDate ?: "",
        style = textStyle14(color = MyColorGray),
        modifier = Modifier.padding(start = 8.dp)
    )

    selectInfo?.itemList?.let {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
            items(it) {
                if (it is ScheduleInfo) {
                    ScheduleInfoItem(item = it)
                } else if (it is PlanInfo) {
                    PlanInfo(item = it)
                }
            }
        }
    }
}

@Composable
fun ScheduleInfoItem(item: ScheduleInfo) {
    Column(
        modifier = Modifier
            .background(MyColorLightBlack, RoundedCornerShape(32.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                item.getTime(),
                style = textStyle12B(MyColorWhite),
                modifier = Modifier
                    .background(MyColorDarkBlue, RoundedCornerShape(12.dp))
                    .padding(vertical = 7.5.dp, horizontal = 12.5.dp)
            )
            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.nonRippleClickable {}
            ) {
                Text("일정 수정", style = textStyle14(Color(0xFF9EA3B2)))
                Spacer(Modifier.width(5.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_next_small),
                    contentDescription = null,
                    tint = Color(0xFF9EA3B2),
                    modifier = Modifier.size(12.dp)
                )
            }
        }
        Spacer(Modifier.height(12.dp))

        Text(item.scheduleTitle, style = textStyle16B(MyColorWhite))
        Spacer(Modifier.height(12.dp))

        Text(item.scheduleContent, style = textStyle14(MyColorWhite))
    }
}

@Composable
fun PlanInfo(item: PlanInfo) {
    Column(
        modifier = Modifier
            .background(MyColorLightBlack, RoundedCornerShape(32.dp))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                item.title,
                style = textStyle12B(MyColorWhite),
                modifier = Modifier
                    .background(MyColorDarkBlue, RoundedCornerShape(12.dp))
                    .padding(vertical = 7.5.dp, horizontal = 12.5.dp)
            )
            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.nonRippleClickable {}
            ) {
                Text("일정 수정", style = textStyle14(Color(0xFF9EA3B2)))
                Spacer(Modifier.width(5.dp))

                Icon(
                    painter = painterResource(R.drawable.ic_next_small),
                    contentDescription = null,
                    tint = Color(0xFF9EA3B2),
                    modifier = Modifier.size(12.dp)
                )
            }
        }
        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyColorDarkBlue.copy(0.1f), RoundedCornerShape(24.dp))
                .border(1.dp, MyColorDarkBlue, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            item.taskList.forEach {
                CommonRadio(
                    text = it.contents,
                    check = it.isCompleted,
                    color = Color(0xFF9EA3B2),
                    isLineThrough = it.isCompleted,
                    onCheckedChange = { }
                )
            }
        }
    }
}