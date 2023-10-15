package com.example.mjapp.ui.screen.other.english_words

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mjapp.R
import com.example.mjapp.ui.custom.CommonLottieAnimation
import com.example.mjapp.ui.custom.DoubleCard
import com.example.mjapp.ui.custom.IconBox
import com.example.mjapp.ui.custom.UnderLineText
import com.example.mjapp.ui.dialog.OneSelectDialog
import com.example.mjapp.ui.screen.navigation.NavScreen
import com.example.mjapp.ui.structure.HeaderBodyContainer
import com.example.mjapp.ui.theme.MyColorBeige
import com.example.mjapp.ui.theme.MyColorBlack
import com.example.mjapp.ui.theme.MyColorGray
import com.example.mjapp.ui.theme.MyColorWhite
import com.example.mjapp.util.nonRippleClickable
import com.example.mjapp.util.textStyle16
import com.example.mjapp.util.textStyle18
import com.example.mjapp.util.textStyle18B

@Composable
fun EnglishWordsScreen(
    onBackClick: () -> Unit,
    goToScreen: (String, String) -> Unit,
    viewModel: EnglishWordsViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            EnglishWordsHeader(
                title = "영단어 암기",
                day = viewModel.day.intValue,
                onBackClick = onBackClick,
                onDaySelect = viewModel::updateDay
            )
        },
        bodyContent = {
            EnglishWordsBody(
                goToScreen = {
                    goToScreen(it, viewModel.day.intValue.toString())
                }
            )
        }
    )
}

@Composable
fun EnglishWordsHeader(
    title: String = "",
    day: Int,
    onBackClick: () -> Unit,
    onDaySelect: (Int) -> Unit
) {
    var isShow by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconBox(
            boxColor = MyColorBeige,
            onClick = onBackClick
        )

        Text(text = title, style = textStyle18(), modifier = Modifier.align(Alignment.Center))

        UnderLineText(
            textValue = "Day $day",
            textStyle = textStyle18B(),
            underLineColor = MyColorBeige,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .nonRippleClickable { isShow = true }
        )
    }

    OneSelectDialog(
        isShow =  isShow,
        title = "Day 선택",
        list = (1..60).map { "Day $it" },
        color = MyColorBeige,
        selectItem = "Day $day",
        onDismiss = { isShow = false },
        onSelect = {
            runCatching {
                onDaySelect(it.replace("Day ", "").toInt())
            }
        }
    )
}

@Composable
fun EnglishWordsBody(
    goToScreen: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(top = 20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            EnglishWordsItem(
                topCardColor = MyColorBeige,
                bottomCardColor = MyColorWhite,
                title = "단어 암기하기",
                iconRes = R.drawable.ic_english_study,
                onClick = {
                    goToScreen(NavScreen.Memorize.item.route)
                }
            )
        }
        item {
            EnglishWordsItem(
                title = "단어 테스트하기",
                iconRes = R.drawable.ic_exam,
                onClick = {
                    goToScreen(NavScreen.Exam.item.route)
                }
            )
        }
        item {
            EnglishWordsItem(
                title = "오답 노트 보기",
                iconRes = R.drawable.ic_wrong_answer_notes,
                onClick = {
                    goToScreen(NavScreen.WrongAnswers.item.route)
                }
            )
        }
    }
}

@Composable
fun EnglishEmpty(
    message: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CommonLottieAnimation(
            resId = R.raw.lottie_error,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Text(
            text = message,
            style = textStyle16().copy(color = MyColorGray),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun EnglishWordsItem(
    topCardColor: Color = MyColorWhite,
    bottomCardColor: Color = MyColorBeige,
    @DrawableRes iconRes: Int,
    title: String,
    onClick: () -> Unit
) {
    DoubleCard(
        topCardColor = topCardColor,
        bottomCardColor = bottomCardColor,
        modifier = Modifier
            .fillMaxWidth()
            .nonRippleClickable(onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 5.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = MyColorBlack,
                modifier = Modifier
                    .size(64.dp)
            )
            Text(text = title, style = textStyle18B(), modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_next),
                contentDescription = null,
                tint = MyColorBlack,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
