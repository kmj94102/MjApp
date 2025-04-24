package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle14
import com.example.mjapp.util.textStyle16B
import com.example.network.model.MyCalendar
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeekCalendar(
    modifier: Modifier = Modifier,
    today: String,
    list: List<MyCalendar>,
    selectDate: String,
    onDateSelect: (String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        DoubleCard(
            bottomCardColor = MyColorPurple,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
            ) {
                list.forEach {
                    WeekCalendarItem(
                        myCalendar = it,
                        isSelectDate = it.detailDate == selectDate,
                        today = today,
                        onClick = onDateSelect,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

        }
    }
}

@Composable
fun WeekCalendarItem(
    myCalendar: MyCalendar,
    isSelectDate: Boolean,
    today: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val color = when {
        myCalendar.dayOfWeek == "일" || myCalendar.isHoliday -> MyColorRed
        myCalendar.dayOfWeek == "토" -> Color(0xFF8AC3F9)
        else -> MyColorBlack
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelectDate) MyColorPurple else MyColorWhite)
            .border(
                width = 1.dp,
                color = if (today == myCalendar.detailDate) MyColorPurple else MyColorWhite,
                shape = RoundedCornerShape(10.dp)
            )
            .nonRippleClickable {
                onClick(myCalendar.detailDate)
            }
    ) {
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = myCalendar.dayOfWeek, style = textStyle14().copy(color = color))
        Spacer(modifier = Modifier.height(3.dp))
        Text(text = myCalendar.date, style = textStyle16B().copy(color = color))
        Spacer(modifier = Modifier.height(3.dp))
    }
}

fun getWeeklyDateRange(dateString: String): List<MyCalendar> {
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
    val startDayOfWeek = getStartDayOfWeek(dateString)?: return emptyList()
    val lastDayOfWeek = getLastDayOfWeek(dateString)?: return emptyList()

    val list = mutableListOf<MyCalendar>()

    var currentDate = startDayOfWeek.time
    while (!currentDate.after(lastDayOfWeek.time)) {
        list.add(
            MyCalendar(
                detailDate = format.format(currentDate),
                date = dateFormat.format(currentDate),
                dayOfWeek = getDayOfWeek(currentDate)
            )
        )
        startDayOfWeek.add(Calendar.DAY_OF_YEAR, 1)
        currentDate = startDayOfWeek.time
    }

    return list
}

fun getStartDayOfWeek(dateString: String): Calendar? {
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val date = format.parse(dateString) ?: return null

    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    return calendar
}

fun getLastDayOfWeek(dateString: String): Calendar? {
    val format = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val date = format.parse(dateString) ?: return null

    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
    return calendar
}

private fun getDayOfWeek(date: Date): String {
    val calendar = Calendar.getInstance()
    calendar.time = date

    // 요일 정보 반환
    return when (calendar.get(Calendar.DAY_OF_WEEK)) {
        Calendar.SUNDAY -> "일"
        Calendar.MONDAY -> "월"
        Calendar.TUESDAY -> "화"
        Calendar.WEDNESDAY -> "수"
        Calendar.THURSDAY -> "목"
        Calendar.FRIDAY -> "금"
        Calendar.SATURDAY -> "토"
        else -> ""
    }
}






