package com.example.mjapp.ui.screen.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mjapp.R
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.MonthCalendar
import com.example.mjapp.ui.screen.calendar.dialog.YearMonthSelectDialog
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle24B

@Composable
fun CalendarScreen(
    viewModel: CalendarViewHolder = hiltViewModel(),
    goToAdd: (String) -> Unit
) {
    val isYearMonthDialogShow = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 21.dp, bottom = 10.dp)
        ) {
            Text(
                text = "${viewModel.year.value}.${viewModel.month.value}",
                style = textStyle24B().copy(color = MyColorPurple),
                modifier = Modifier.nonRippleClickable {
                    isYearMonthDialogShow.value = true
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            IconBox(
                boxColor = MyColorPurple,
                iconSize = 22.dp,
                iconRes = R.drawable.ic_calendar
            ) {

            }
            Spacer(modifier = Modifier.width(5.dp))
            IconBox(
                boxColor = MyColorWhite,
                iconSize = 22.dp,
                iconRes = R.drawable.ic_lists
            ) {

            }
        }
        DoubleCard(
            bottomCardColor = MyColorPurple,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 17.dp)
        ) {
            MonthCalendar(
                today = viewModel.getToday(),
                calendarInfo = viewModel.calendarItemList,
                selectDate = viewModel.selectDate.value,
                onSelectChange = {
                    viewModel.updateSelectDate(it)
                },
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
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp)
        ) {
            val item = viewModel.selectItem ?: return@Row
            Text(
                text = buildAnnotatedString {
                    append("${item.date}(${item.dayOfWeek})")
                    if (item.dateInfo.isNotEmpty()) {
                        withStyle(style = SpanStyle(color = MyColorGray, fontSize = 16.sp)) {
                            append(" ${item.dateInfo}")
                        }
                    }
                },
                style = textStyle24B().copy(color = MyColorPurple)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconBox(boxColor = MyColorRed, iconRes = R.drawable.ic_plus) {
                goToAdd(viewModel.selectDate.value)
            }
        }
    }

    YearMonthSelectDialog(
        year = viewModel.year.value,
        month = viewModel.month.value,
        isShow = isYearMonthDialogShow.value,
        onDismiss = {
            isYearMonthDialogShow.value = false
        },
        onSelect = { year, month ->
            viewModel.updateYearMonth(year, month)
        }
    )
}