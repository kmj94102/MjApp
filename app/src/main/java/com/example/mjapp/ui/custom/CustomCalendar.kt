package com.example.mjapp.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mjapp.R
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.calendarWeek
import com.example.mjapp.util.isSameDay
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle16B
import com.example.network.model.CalendarInfo2
import java.util.Calendar

@Composable
fun CustomCalendar(
    yearMonth: String = "",
    selectDate: Calendar = Calendar.getInstance(),
    list: List<CalendarInfo2> = emptyList(),
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onDateClick: (Calendar) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(MyColorLightBlack, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Spacer(Modifier.width(16.dp))
            Text(yearMonth, style = textStyle16B(MyColorWhite))
            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.ic_prev_small),
                contentDescription = null,
                modifier = Modifier.nonRippleClickable(onPrevClick)
            )
            Spacer(Modifier.width(12.dp))
            Image(
                painter = painterResource(R.drawable.ic_next_small),
                contentDescription = null,
                modifier = Modifier.nonRippleClickable(onNextClick)
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(7)
        ) {
            items(calendarWeek()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(44.dp)
                ) {
                    Text(it, style = textStyle12(Color(0xFF9EA3B2)))
                }
            }
            items(list) {
                CustomCalendarItem(it, selectDate, onDateClick)
            }
        }
    }
}

@Composable
fun CustomCalendarItem(
    item: CalendarInfo2,
    selectDate: Calendar,
    onClick: (Calendar) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .border(
                width = 1.dp,
                color = if (isSameDay(item.date, selectDate)) {
                    MyColorDarkBlue
                } else {
                    Color.Transparent
                },
                shape = CircleShape
            )
    ) {
        item.getDate()?.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .nonRippleClickable { item.date?.let { onClick(it) } }
            ) {
                Text(
                    it.toString(),
                    style = textStyle12B(MyColorWhite),
                )
                if (item.itemSize() != 0) {
                    Spacer(Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        repeat(item.itemSize()) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .background(MyColorDarkBlue, CircleShape)
                            )
                        }
                    }
                }
            }
        }
    }
}