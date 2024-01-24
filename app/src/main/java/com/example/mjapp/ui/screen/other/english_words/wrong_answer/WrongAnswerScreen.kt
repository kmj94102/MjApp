package com.example.mjapp.ui.screen.other.english_words.wrong_answer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.screen.other.english_words.EnglishEmpty
import com.example.mjapp.ui.screen.other.english_words.EnglishWordsHeader
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorPurple
import com.example.mjapp.util.textStyle12
import com.example.mjapp.util.textStyle16B
import com.example.mjapp.util.textStyle18B
import com.example.network.model.WrongAnswer

@Composable
fun WrongAnswerScreen(
    onBackClick: () -> Unit,
    viewModel: WrongAnswerViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            EnglishWordsHeader(
                day = viewModel.day.intValue,
                onBackClick = onBackClick,
                onDaySelect = viewModel::updateDay
            )
        },
        bodyContent = {
            if (viewModel.list.isNotEmpty()) {
                WrongAnswerBody(viewModel = viewModel)
            } else {
                EnglishEmpty(message = "오답이 없습니다.")
            }
        }
    )
}

@Composable
fun WrongAnswerBody(
    viewModel: WrongAnswerViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 20.dp, bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.list.forEach {
            item { WrongAnswerItem(item = it) }
        }
    }
}

@Composable
fun WrongAnswerItem(
    item: WrongAnswer
) {
    DoubleCard(
        bottomCardColor = MyColorBeige,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyColorBeige)
                    .padding(horizontal = 15.dp, vertical = 5.dp)
            ) {
                Text(
                    text = item.word,
                    style = textStyle18B(),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${item.count}회",
                    style = textStyle18B(color = MyColorGray),
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyColorBlack)
            )
            Text(
                text = item.meaning, style = textStyle16B(color = MyColorPurple),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 10.dp)
            )
            Text(
                text = item.hint.replace("\\\\\\", "\n"),
                style = textStyle12().copy(fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            )
        }
    }
}
