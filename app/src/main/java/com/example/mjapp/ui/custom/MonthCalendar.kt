package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.R
import com.example.network.model.MyCalendar

@Composable
fun MonthCalendar(
    today: String,
    calendarInfo: List<MyCalendar>,
    selectDate: String,
    onSelectChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        WeekLabel()
        Spacer(modifier = Modifier.height(5.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            calendarInfo.forEach { info ->
                if (info.date.isEmpty()) {
                    item { Box(modifier = Modifier) }
                } else {
                    item {
                        DateCard(
                            calendarItem = info,
                            selectDate = selectDate,
                            today = today,
                            onClick = onSelectChange
                        )
                    }
                }
            }
        }
    }
}

/** 요일 라벨 **/
@Composable
fun WeekLabel() {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        listOf("일", "월", "화", "수", "목", "금", "토").forEach {
            val color = when (it) {
                "일" -> MyColorRed
                "토" -> Color(0xFF8AC3F9)
                else -> MyColorBlack
            }

            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = it,
                    style = textStyle16B().copy(color = color, textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DateCard(
    calendarItem: MyCalendar,
    selectDate: String,
    today: String,
    onClick: (String) -> Unit
) {
    val color = when {
        calendarItem.dayOfWeek == "일" || calendarItem.isHoliday -> MyColorRed
        calendarItem.dayOfWeek == "토" -> Color(0xFF8AC3F9)
        else -> MyColorBlack
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(
                color = if (calendarItem.detailDate == selectDate) MyColorPurple else MyColorWhite
            )
            .border(
                width = 1.dp,
                color = if (today == calendarItem.detailDate) MyColorPurple else MyColorWhite,
                shape = RoundedCornerShape(5.dp)
            )
            .nonRippleClickable {
                onClick(calendarItem.detailDate)
            }
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = calendarItem.date,
            style = textStyle16B().copy(
                color = color,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(top = 2.dp)
        ) {
            val maxItemCount = minOf(calendarItem.itemList.size, 3)
            for (i in 0 until maxItemCount) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(MyColorRed)
                )
            }
            if (calendarItem.itemList.size > 3) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_small),
                    contentDescription = null,
                    tint = MyColorBlack,
                    modifier = Modifier.size(5.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}