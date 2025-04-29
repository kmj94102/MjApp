package com.example.mjapp.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mjapp.ui.screen.accountbook.detail.AccountBookCalendar
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorLightGray
import com.example.mjapp.ui.theme.MyColorRed
import com.example.mjapp.ui.theme.MyColorSkyBlue
import com.example.network.model.MyCalendar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@Preview
@Composable
fun ComposeTest(
    viewModel: Test = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        DoubleCard {
            AccountBookMonthCalendar(
                today = "2023.07.28",
                calendarInfo = viewModel.list,
                selectDate = "2023.07.28",
                onSelectChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        DoubleCard {
            MonthCalendar(
                today = "2023.07.28",
                calendarInfo = viewModel.list2,
                selectDate = "2023.07.28",
                onSelectChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MyColorLightGray)
        ) {
//            CommonProgressBar(
//                percent = 90,
//                progressColor = MyColorBeige,
//                backgroundColor = Color.Transparent,
//                isTextVisible = false,
//                modifier = Modifier.fillMaxWidth()
//            )
//            CommonProgressBar(
//                percent = 85,
//                progressColor = MyColorSkyBlue,
//                backgroundColor = Color.Transparent,
//                isTextVisible = false,
//                modifier = Modifier.fillMaxWidth()
//            )
//            CommonProgressBar(
//                percent = 70,
//                progressColor = MyColorRed,
//                backgroundColor = Color.Transparent,
//                isTextVisible = false,
//                modifier = Modifier.fillMaxWidth()
//            )
        }
    }
}

@HiltViewModel
class Test @Inject constructor(

) : ViewModel() {
    val list = mutableStateListOf<AccountBookCalendar>(
        AccountBookCalendar(date = "10", detailDate = "2023.07.10"),
        AccountBookCalendar(date = "11", detailDate = "2023.07.11"),
        AccountBookCalendar(date = "12", detailDate = "2023.07.12"),
        AccountBookCalendar(date = "13", detailDate = "2023.07.13"),
        AccountBookCalendar(date = "14", detailDate = "2023.07.14"),
        AccountBookCalendar(date = "15", detailDate = "2023.07.15"),
        AccountBookCalendar(date = "16", detailDate = "2023.07.16"),
        AccountBookCalendar(date = "17", detailDate = "2023.07.17"),
        AccountBookCalendar(date = "18", detailDate = "2023.07.18"),
    )
    val list2 = mutableStateListOf<MyCalendar>(
        MyCalendar(date = "10", detailDate = "2023.07.10"),
        MyCalendar(date = "11", detailDate = "2023.07.11"),
        MyCalendar(date = "12", detailDate = "2023.07.12"),
        MyCalendar(date = "13", detailDate = "2023.07.13"),
        MyCalendar(date = "14", detailDate = "2023.07.14"),
        MyCalendar(date = "15", detailDate = "2023.07.15"),
        MyCalendar(date = "16", detailDate = "2023.07.16"),
        MyCalendar(date = "17", detailDate = "2023.07.17"),
        MyCalendar(date = "18", detailDate = "2023.07.18"),
    )

    init {
        fetch()
    }

    private fun fetch() = viewModelScope.launch {
        delay(2000)
        list.clear()
        list.addAll(
            listOf(
                AccountBookCalendar(date = "20", detailDate = "2023.07.20"),
                AccountBookCalendar(date = "21", detailDate = "2023.07.21"),
                AccountBookCalendar(date = "22", detailDate = "2023.07.22"),
                AccountBookCalendar(date = "23", detailDate = "2023.07.23"),
                AccountBookCalendar(date = "24", detailDate = "2023.07.24"),
                AccountBookCalendar(date = "25", detailDate = "2023.07.25"),
                AccountBookCalendar(date = "26", detailDate = "2023.07.26"),
                AccountBookCalendar(date = "27", detailDate = "2023.07.27"),
                AccountBookCalendar(date = "28", detailDate = "2023.07.28"),
                AccountBookCalendar(date = "20", detailDate = "2023.07.20"),
                AccountBookCalendar(date = "21", detailDate = "2023.07.21"),
                AccountBookCalendar(date = "22", detailDate = "2023.07.22"),
                AccountBookCalendar(date = "23", detailDate = "2023.07.23"),
                AccountBookCalendar(date = "24", detailDate = "2023.07.24"),
                AccountBookCalendar(date = "25", detailDate = "2023.07.25"),
                AccountBookCalendar(date = "26", detailDate = "2023.07.26"),
                AccountBookCalendar(date = "27", detailDate = "2023.07.27"),
                AccountBookCalendar(date = "28", detailDate = "2023.07.28"),
            )
        )
        list2.clear()
        list2.addAll(
            listOf(
                MyCalendar(date = "20", detailDate = "2023.07.20"),
                MyCalendar(date = "21", detailDate = "2023.07.21"),
                MyCalendar(date = "22", detailDate = "2023.07.22"),
                MyCalendar(date = "23", detailDate = "2023.07.23"),
                MyCalendar(date = "24", detailDate = "2023.07.24"),
                MyCalendar(date = "25", detailDate = "2023.07.25"),
                MyCalendar(date = "26", detailDate = "2023.07.26"),
                MyCalendar(date = "27", detailDate = "2023.07.27"),
                MyCalendar(date = "28", detailDate = "2023.07.28"),
                MyCalendar(date = "20", detailDate = "2023.07.20"),
                MyCalendar(date = "21", detailDate = "2023.07.21"),
                MyCalendar(date = "22", detailDate = "2023.07.22"),
                MyCalendar(date = "23", detailDate = "2023.07.23"),
                MyCalendar(date = "24", detailDate = "2023.07.24"),
                MyCalendar(date = "25", detailDate = "2023.07.25"),
                MyCalendar(date = "26", detailDate = "2023.07.26"),
                MyCalendar(date = "27", detailDate = "2023.07.27"),
                MyCalendar(date = "28", detailDate = "2023.07.28"),
            )
        )
    }
}

