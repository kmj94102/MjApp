package com.example.mjapp.ui.screen.other.word.note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonChip
import com.example.mjapp.ui.custom.CommonGnb
import com.example.mjapp.ui.custom.CommonGnbBackButton
import com.example.mjapp.ui.custom.TextButton
import com.example.mjapp.ui.dialog.YearMonthSelectDialog
import com.example.mjapp.ui.screen.navigation.NavScreen2
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorDarkBlue
import com.example.mjapp.ui.theme.MyColorLightBlack
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.calendarWeek
import com.example.mjapp.util.isSameDay
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle12B
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18B
import com.example.network.model.Note
import java.util.Calendar

@Composable
fun WordStudyScreen(
    navHostController: NavHostController? = null,
    viewModel: WordStudyViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val list by viewModel.list.collectAsStateWithLifecycle()
    val selectDate by viewModel.selectDate.collectAsStateWithLifecycle()
    var isYearMonthDialogShow by remember { mutableStateOf(false) }

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonGnb(
                title = "단어 암기",
                startButton = {
                    CommonGnbBackButton { navHostController?.popBackStack() }
                }
            )
        },
        bodyContent = {
            WordStudyContent(
                list = list,
                yearMonth = viewModel.getYearMonth(),
                selectDate = selectDate,
                goToExam = { idx, title ->
                    navHostController?.navigate(NavScreen2.WordExam(index = idx, title = title))
                },
                goToWordDetail = { idx, title ->
                    navHostController?.navigate(NavScreen2.WordDetail(id = idx, title = title))
                },
                onPrevClick = viewModel::prevMonth,
                onNextClick = viewModel::nextMonth,
                onDateClick = viewModel::updateSelectDate
            )
        },
        paddingValues = PaddingValues(),
        modifier = Modifier.background(MyColorBlack)
    )

    YearMonthSelectDialog(
        year = "",
        month = "",
        isShow = isYearMonthDialogShow,
        onDismiss = { isYearMonthDialogShow = false },
        onSelect = { _, _ -> }
    )
}

@Composable
fun WordStudyContent(
    list: List<WordStudyCalendar> = emptyList(),
    selectDate: Calendar = Calendar.getInstance(),
    yearMonth: String = "",
    goToExam: (Int, String) -> Unit,
    goToWordDetail: (Int, String) -> Unit,
    onPrevClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onDateClick: (Calendar) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        WordCalendar(
            yearMonth = yearMonth,
            selectDate = selectDate,
            list = list,
            onPrevClick = onPrevClick,
            onNextClick = onNextClick,
            onDateClick = onDateClick
        )
        Spacer(Modifier.height(32.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            list.filter { isSameDay(it.date, selectDate) }
                .map { it.noteList }
                .flatten()
                .forEach {
                    item {
                        NoteItem(it, goToExam, goToWordDetail)
                    }
                }
        }
    }
}

@Composable
fun WordCalendar(
    yearMonth: String = "",
    selectDate: Calendar = Calendar.getInstance(),
    list: List<WordStudyCalendar> = emptyList(),
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
                WordCalendarItem(it, selectDate, onDateClick)
            }
        }
    }
}

@Composable
fun WordCalendarItem(
    item: WordStudyCalendar,
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
                if (item.noteList.isNotEmpty()) {
                    Spacer(Modifier.height(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        item.noteList.forEach {
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

@Composable
fun NoteItem(
    item: Note,
    goToExam: (Int, String) -> Unit = { _, _ -> },
    goToWordDetail: (Int, String) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MyColorLightBlack)
    ) {
        CommonChip(
            text = item.getLanguageKr(),
            textStyle = textStyle12(),
            backgroundColor = MyColorWhite,
            paddingValues = PaddingValues(vertical = 3.dp, horizontal = 8.dp),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
        Spacer(Modifier.height(14.dp))

        Text(
            item.title,
            style = textStyle16B(MyColorWhite),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(20.dp))

        Row {
            TextButton(
                text = "테스트",
                textStyle = textStyle18B(MyColorWhite),
                backgroundColor = MyColorDarkBlue.copy(alpha = 0.1f),
                borderWidth = 0.dp,
                borderColor = Color.Transparent,
                shape = RoundedCornerShape(0.dp),
                innerPadding = PaddingValues(vertical = 14.dp),
                onClick = { goToExam(item.noteId, item.title) },
                modifier = Modifier.weight(1f)
            )

            TextButton(
                text = "단어 암기",
                textStyle = textStyle18B(MyColorWhite),
                backgroundColor = MyColorDarkBlue,
                borderWidth = 0.dp,
                borderColor = Color.Transparent,
                shape = RoundedCornerShape(0.dp),
                innerPadding = PaddingValues(vertical = 14.dp),
                onClick = { goToWordDetail(item.noteId, item.title) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}