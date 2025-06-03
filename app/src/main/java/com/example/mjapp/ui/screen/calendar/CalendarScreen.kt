package com.example.mjapp.ui.screen.calendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.CustomCalendar
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.rememberLifecycleEvent
import com.example.network.model.CalendarInfo2
import java.util.Calendar

@Composable
fun CalendarScreen(
    navHostController: NavHostController? = null,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    var isYearMonthDialogShow by remember { mutableStateOf(false) }
    val lifecycleEvent = rememberLifecycleEvent()
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus_2),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp).nonRippleClickable {

                        }
                    )
                }
            )
        },
        bodyContent = {
            CalendarBody(
                yearMonth = viewModel.getYearMonth(),
                selectDate = selectDate,
                list = calendarInfo,
                onPrevClick = viewModel::prevMonth,
                onNextClick = viewModel::nextMonth,
                onDateClick = viewModel::updateSelectDate
            )
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    )

    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
        }
    }
}

@Composable
fun CalendarBody(
    yearMonth: String,
    selectDate: Calendar,
    list: List<CalendarInfo2>,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
    onDateClick: (Calendar) -> Unit
) {
    CustomCalendar(
        yearMonth = yearMonth,
        selectDate = selectDate,
        list = list,
        onPrevClick = onPrevClick,
        onNextClick = onNextClick,
        onDateClick = onDateClick
    )
}